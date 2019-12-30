package com.movie.util.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MobileNumberValidator {

  // 前缀3表示“用户是一个运营账户”
  private static String[] mobileNumberPrefix = {"13", "14", "15", "166", "17", "18", "198", "199", "3"};
  private static String[] mobileNumberBlockPrefix = {"170", "171"};
  // 去掉运营账户的情况
  private static String[] mobileNumberPrefixWithoutOperator = {"13", "14", "15", "166", "17", "18", "198", "199"};

  public static String validate(String mobileNumber) {
    String regexp = "[\\s\\-\\(\\)]";
    Pattern p = Pattern.compile(regexp);
    mobileNumber = p.matcher(mobileNumber).replaceAll("");
    if (mobileNumber.startsWith("+86")) mobileNumber = mobileNumber.substring(3);
    for (char ch : mobileNumber.toCharArray()) {
      if (!Character.isDigit(ch)) return null;
    }
    if (mobileNumber.length() == 11 && isValidPrefix(mobileNumber)) {
      return mobileNumber;
    } else {
      return null;
    }
  }

  public static String validateWithoutOperator(String mobileNumber, boolean blockingEnabled) {
    String regexp = "[\\s\\-\\(\\)]";
    Pattern p = Pattern.compile(regexp);
    mobileNumber = p.matcher(mobileNumber).replaceAll("");
    if (mobileNumber.startsWith("+86")) mobileNumber = mobileNumber.substring(3);
    for (char ch : mobileNumber.toCharArray()) {
      if (!Character.isDigit(ch)) return null;
    }
    if (mobileNumber.length() == 11 && isValidPrefixWithoutOperator(mobileNumber, blockingEnabled)) {
      return mobileNumber;
    } else {
      return null;
    }
  }

  public static boolean isValidPrefix(String mobileNumber) {
    return isValidPrefix(mobileNumber, true);
  }

  // 某些情况下skip 黑名单前缀的验证。例如已经成功注册的170，171用户进行充值提现等操作时，需要向其发送验证码
  public static boolean isValidPrefix(String mobileNumber, boolean blockingEnabled) {
    if (blockingEnabled) {
      for (String blockPrefix : mobileNumberBlockPrefix) {
        if (mobileNumber.startsWith(blockPrefix)) return false;
      }
    }
    for (String prefix : mobileNumberPrefix) {
      if (mobileNumber.startsWith(prefix)) return true;
    }
    return false;
  }

  public static boolean isValidPrefixWithoutOperator(String mobileNumber, boolean blockingEnabled) {
    if (blockingEnabled) {
      for (String blockPrefix : mobileNumberBlockPrefix) {
        if (mobileNumber.startsWith(blockPrefix)) return false;
      }
    }
    for (String prefix : mobileNumberPrefixWithoutOperator) {
      if (mobileNumber.startsWith(prefix)) return true;
    }
    return false;
  }

  public boolean isValidLen(String mobileNumber) {
    return StringUtils.length(mobileNumber) == 11;
  }

  public boolean isValidPrefixNum(String mobileNumber) {
    return isValidPrefix(mobileNumber);
  }

  public boolean isValidPrefixNum(String mobileNumber, boolean blockingEnabled) {
    return isValidPrefix(mobileNumber, blockingEnabled);
  }

  public boolean isValidNumber(String mobileNumber) {
    return validate(mobileNumber) != null;
  }
}
