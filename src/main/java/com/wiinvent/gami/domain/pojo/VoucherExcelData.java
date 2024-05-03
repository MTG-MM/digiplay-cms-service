package com.wiinvent.gami.domain.pojo;

import lombok.Data;

@Data
public class VoucherExcelData {
  private Integer id;
  private String code;
  private String name;
  private String startAt;
  private String expireAt;

  public static String[] getHeader(){
    return new String[]{"Stt","Mã voucher","Tên voucher", "Ngày bắt đầu", "Ngày hết hạn"};
  }
}
