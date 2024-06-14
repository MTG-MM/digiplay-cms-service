package com.wiinvent.gami.domain.utils;

import com.wiinvent.gami.domain.exception.BadRequestException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dhatim.fastexcel.reader.ExcelReaderException;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelUtils {
  public static <T> List<T> readExcel(MultipartFile file, String[] headers, Class<T> clazz) throws IOException {
    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
      Sheet sheet = workbook.getSheetAt(0);
      int[] headerIndexes = getHeaderIndexes(sheet, headers);

      List<T> list = new ArrayList<>();
      try (ReadableWorkbook wb = new ReadableWorkbook(file.getInputStream())) {
        List<org.dhatim.fastexcel.reader.Row> rows = wb.getFirstSheet().read().stream()
            .filter(row -> hasNonEmptyCells(row, headers))
            .collect(Collectors.toList());
        rows.removeFirst();
        for (org.dhatim.fastexcel.reader.Row row : rows) {
          T obj;
          try {
            obj = clazz.newInstance();
          } catch (InstantiationException | IllegalAccessException e) {
            throw new BadRequestException("Không thể khởi tạo đối tượng: " + clazz.getSimpleName());
          }

          Field[] fields = clazz.getDeclaredFields();
          for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            if (row.hasCell(headerIndexes[j])) {
              try {
                setFieldValue(fields[j], obj, row, headerIndexes[j]);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            }
          }
          list.add(obj);
        }
      }
      return list;
    }
  }

  private static boolean hasNonEmptyCells(org.dhatim.fastexcel.reader.Row row, String[] headers) {
    for (int i = 0; i < headers.length; i++) {
      if (row.hasCell(i) && !row.getCell(i).toString().equals("[EMPTY null]")) {
        return true;
      }
    }
    return false;
  }

  private static void setFieldValue(Field field, Object obj, org.dhatim.fastexcel.reader.Row row, int index) throws IllegalAccessException {
    Class<?> fieldType = field.getType();
    if (fieldType == String.class) {
      try {
        field.set(obj, row.getCellAsString(index).orElse(null));
      } catch (ExcelReaderException e) {
        field.set(obj, row.getCellAsNumber(index).orElse(new BigDecimal(-1)).toString());
      }
    } else if (fieldType == int.class || fieldType == Integer.class) {
      field.set(obj, row.getCellAsNumber(index).orElse(new BigDecimal(-1)).intValue());
    } else if (fieldType == double.class || fieldType == Double.class) {
      field.set(obj, row.getCellAsNumber(index).orElse(new BigDecimal(-1)).doubleValue());
    } else if (fieldType == Date.class) {
      field.set(obj, row.getCellAsDate(index).orElse(null));
    }
  }



  private static int[] getHeaderIndexes(Sheet sheet, String[] headers) {
    int[] indexes = new int[headers.length];
    Row headerRow = sheet.getRow(0);
    for (int i = 0; i < headers.length; i++) {
      String header = headers[i];
      for (int j = 0; j < headerRow.getLastCellNum(); j++) {
        Cell cell = headerRow.getCell(j);
        if (cell != null && cell.getStringCellValue().equals(header)) {
          indexes[i] = j;
          break;
        }
      }
    }
    return indexes;
  }

  public static byte[] createExcelFile(List<?> data, String[] headers) throws IOException {
    SXSSFWorkbook workbook = new SXSSFWorkbook();
    SXSSFSheet sheet = workbook.createSheet("Data");

    Row headerRow = sheet.createRow(0);
    CellStyle headerStyle = createHeaderCellStyle(workbook);
    for (int i = 0; i < headers.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(headers[i]);
      cell.setCellStyle(headerStyle);
    }

    CellStyle dataStyle = createDataCellStyle(workbook);
    for (int i = 0; i < data.size(); i++) {
      Row row = sheet.createRow(i + 1);
      Object obj = data.get(i);

      Class<?> clazz = obj.getClass();
      Field[] fields = clazz.getDeclaredFields();
      for (int j = 0; j < fields.length; j++) {
        if(j >= headers.length){
          break;
        }
        Cell cell = row.createCell(j);
        fields[j].setAccessible(true);
        if(fields[j].getName().equals("createdAt") || fields[j].getName().equals("invitedAt") ||
            fields[j].getName().equals("departureAt")) continue;
        try {
          Object value = fields[j].get(obj);
          if (value != null) {
            if (value instanceof Number) {
              cell.setCellValue(((Number) value).doubleValue());
            }else if(value instanceof LocalDateTime){
              cell.setCellValue((DateUtils.convertLocalDateTimeToString((LocalDateTime) value)));
            }else {
              cell.setCellValue(value.toString());
            }
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
        cell.setCellStyle(dataStyle);
      }
    }

    for (int i = 0; i < headers.length; i++) {
      sheet.trackAllColumnsForAutoSizing();
      sheet.autoSizeColumn(i);
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    workbook.write(outputStream);
    workbook.close();
    return outputStream.toByteArray();
  }

  private static CellStyle createHeaderCellStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER); // Căn giữa tiêu đề
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return style;
  }

  private static CellStyle createDataCellStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setAlignment(HorizontalAlignment.LEFT);
    return style;
  }
}
