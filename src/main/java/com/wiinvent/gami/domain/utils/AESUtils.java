package com.wiinvent.gami.domain.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@Log4j2
public class AESUtils {

  public static final String TRANSFORMATION = "AES/ECB/PKCS5PADDING";

  @Value("${aes-secret}")
  public String aesSecretKey;

  public String encrypt(String text) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
      byte[] byteEncrypted = cipher.doFinal(text.getBytes());
      return Base64.getEncoder().encodeToString(byteEncrypted);
    } catch (Exception e) {
      log.error("Error while encrypting the text", e);
    }
    return null;
  }

  public String decrypt(String encryptedText) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      byte[] byteEncrypted = Base64.getDecoder().decode(encryptedText);
      cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
      return new String(byteDecrypted);
    } catch (Exception e) {
      log.error("Error while decrypting the text", e);
    }
    return null;
  }

}