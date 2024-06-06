package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
  @NotBlank
  @Size(min = 6, max = 120)
  public String oldPassword;

  @NotBlank
  @Size(min = 6, max = 120)
  public String newPassword;
}
