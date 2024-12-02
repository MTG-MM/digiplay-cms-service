package com.wiinvent.gami.domain.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Google2FAUtils {
  public static String generateSecretKey() {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[20];
    random.nextBytes(bytes);
    Base32 base32 = new Base32();
    return base32.encodeToString(bytes).replace("=", "");
  }

  public static String generateSecretKey(String key, String username, String password) {
    String combinedString = key + username + password;
    byte[] bytes = combinedString.getBytes(StandardCharsets.UTF_8);
    Base32 base32 = new Base32();
    return base32.encodeToString(bytes).replace("=", "");
  }


  public static String getTOTPCode(String secretKey) {
    Base32 base32 = new Base32();
    byte[] bytes = base32.decode(secretKey);
    String hexKey = Hex.encodeHexString(bytes);
    return TOTP.getOTP(hexKey);
  }

  public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
    return "otpauth://totp/"
        + URLEncoder.encode(issuer + ":" + account, StandardCharsets.UTF_8).replace("+", "%20")
        + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
        + "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20")
        + "&digits=6"
        + "&period=30";
  }

  public static void createQRCode(String barCodeData, String filePath, int height, int width)
      throws WriterException, IOException {
    BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
    try (FileOutputStream out = new FileOutputStream(filePath)) {
      MatrixToImageWriter.writeToStream(matrix, "png", out);
    }
  }

  public static byte[] generateQRCode(String barCodeData, int width, int height) throws WriterException, IOException {
    BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      MatrixToImageWriter.writeToStream(matrix, "PNG", out);
      return out.toByteArray();
    }
  }

  public static void main(String[] args) throws IOException, WriterException {
    String secretKey = "ABCDE25AFDMNXYZ";
    String newKey = generateSecretKey(secretKey, "AdminManager","Admin");
    String email = "mos-service";
    String companyName = "AdminManager";
    String barCodeUrl = getGoogleAuthenticatorBarCode(newKey, email, companyName);
    createQRCode(barCodeUrl, "QRCode.png", 400, 400);

    System.out.print("Please enter 2fA code here -> ");
  }
}
