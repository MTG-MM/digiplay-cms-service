package com.wiinvent.gami.domain.utils;

import com.wiinvent.gami.domain.exception.BadRequestException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dhatim.fastexcel.reader.ExcelReaderException;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
}
