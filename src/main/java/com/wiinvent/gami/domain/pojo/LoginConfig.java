package com.wiinvent.gami.domain.pojo;

import lombok.Data;

@Data
public class LoginConfig {
  private Integer maxLoginAttempts = 5;
  private Integer lockTime = 300;
}
