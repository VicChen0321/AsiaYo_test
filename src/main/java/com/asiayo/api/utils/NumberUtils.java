package com.asiayo.api.utils;

public class NumberUtils {

  public static boolean isNumeric(String value) {
    if (value == null) {
      return false;
    }
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

}
