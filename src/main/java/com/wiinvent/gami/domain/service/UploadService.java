package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.UploadResponse;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class UploadService {
  private List<String> contentTypeAllowList = List.of("image/jpeg", "image/png");

  @Value("${file.dir}")
  private String fileDir;

  @Value("${file.url}")
  private String fileUrl;

  public UploadResponse uploadStatic(MultipartFile file){
    if(file == null){
      throw new BadRequestException("Missing file input");
    }
    if(file.getSize() > 1000000){
      throw new BadRequestException("File size not valid");
    }
    if(!contentTypeAllowList.contains(file.getContentType())){
      throw new BadRequestException("File not match input");
    }

    try {
      LocalDate now = LocalDate.now();

      String fileName = getFileName(file);
      fileName = Helper.boDauTiengViet(fileName);
      String subPath = "/" + now.getYear() + "/" + Helper.stringFormatDay(now.getMonthValue()) + "/" + Helper.stringFormatDay(now.getDayOfMonth());

      File rootFolder = new File(fileDir + subPath);
      if(!rootFolder.exists()){
        rootFolder.mkdirs();
      }

      File newFile = new File(fileDir + subPath + "/" + fileName);
      if (!newFile.exists()) {
        rootFolder.createNewFile();
      }

      FileCopyUtils.copy(file.getBytes(), newFile);

      return UploadResponse.builder().imgUrl(fileUrl + subPath + "/" + fileName).build();

    }catch(Exception e){
      log.error(e.getMessage());
      throw new BadRequestException("Co loi xay ra");
    }
  }

  protected String getFileName(MultipartFile file){
    String fileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0].trim();
    fileName = Helper.boDauTiengViet(fileName).replaceAll(" +", "_").toLowerCase() + "_" + System.currentTimeMillis();

    if (Objects.equals(file.getContentType(), "image/png")){
      fileName = fileName + ".png";
    }else if (Objects.equals(file.getContentType(), "image/jpg")){
      fileName = fileName + ".jpg";
    }else if (Objects.equals(file.getContentType(), "image/jpeg")){
      fileName = fileName + ".jpeg";
    }else if (Objects.equals(file.getContentType(), "application/vnd.android.package-archive")){
      fileName = fileName + ".apk";
    }
    return fileName;
  }

}
