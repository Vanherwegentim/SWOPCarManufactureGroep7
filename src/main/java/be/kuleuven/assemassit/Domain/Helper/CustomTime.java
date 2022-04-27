package be.kuleuven.assemassit.Domain.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomTime {
  public static boolean isJUnitTest() {
    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
      if (element.getClassName().startsWith("org.junit.")) {
        return true;
      }
    }
    return false;
  }

  public LocalTime customLocalTimeNow() {
    return customLocalDateTimeNow().toLocalTime();
  }

  public LocalDate customLocalDateNow() {
    return customLocalDateTimeNow().toLocalDate();
  }

  public LocalDateTime customLocalDateTimeNow() {
    if (CustomTime.isJUnitTest()) {
      return LocalDateTime.of(1999, 8, 29, 12, 22);
    }
    return LocalDateTime.now();
  }
}
