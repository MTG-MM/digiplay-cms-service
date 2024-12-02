package com.wiinvent.gami.domain.service;

import com.google.zxing.WriterException;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.exception.AuthenticationException;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.factory.RecaptchaRequestFactory;
import com.wiinvent.gami.domain.pojo.GoogleCaptchaResponse;
import com.wiinvent.gami.domain.utils.Google2FAUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class GoogleAuthService extends BaseService{
  private final RecaptchaRequestFactory recaptchaRequestFactory;

  @Lazy
  public GoogleAuthService(@Lazy RecaptchaRequestFactory recaptchaRequestFactory) {
    this.recaptchaRequestFactory = recaptchaRequestFactory;
  }

  @Value("${spring.application.name}")
  private String applicationName;
  @Value("${spring.security.recaptcha.enabled}")
  private Boolean recaptchaEnabled;
  @Value("${spring.security.recaptcha.secret-key}")
  private String recaptchaSecretKey;
  @Value("${spring.security.2fa.secret-key}")
  private String twoFASecretKey;

  public byte[] generateQrCode(String username) throws IOException, WriterException {
    Account account = accountStorage.findByUsername(username);
    if (account == null) {
      throw new AuthenticationException("Account not found");
    }
    String secretKey = Google2FAUtils.generateSecretKey(twoFASecretKey, username, account.getSalt());
    String barCodeUrl = Google2FAUtils.getGoogleAuthenticatorBarCode(secretKey, username,  applicationName);
    return Google2FAUtils.generateQRCode(barCodeUrl, 400,400);
  }

  public void checkGoogleCaptcha(String sign) {
    if (Boolean.FALSE.equals(recaptchaEnabled)) return;
    try {
      GoogleCaptchaResponse googleCaptchaResponse = recaptchaRequestFactory.processCheckRecaptcha(recaptchaSecretKey, sign);
      if (Boolean.FALSE.equals(googleCaptchaResponse.getSuccess())) {
        throw new BadRequestException("Invalid Captcha");
      }
    } catch (Exception e) {
      log.debug("Failed to process: ", e);
      throw new BadRequestException("Invalid Captcha");
    }
  }
}
