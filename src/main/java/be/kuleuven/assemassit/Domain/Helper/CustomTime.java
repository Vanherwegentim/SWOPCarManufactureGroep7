package be.kuleuven.assemassit.Domain.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @immutable
 */
public class CustomTime {
  private static CustomTime customTimeInstance = null;

  private Boolean simulateTime = false;

  private CustomTime() {
  }

  /**
   * Get the instance of the custom time
   *
   * @return the instance of the custom time
   */
  public static CustomTime getInstance() {
    if (customTimeInstance == null) customTimeInstance = new CustomTime();
    return customTimeInstance;
  }

  /**
   * Check if the current environment is a testing environment
   *
   * @return if the program is in a testing environment
   */
  public static boolean isJUnitTest() {
    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
      if (element.getClassName().startsWith("org.junit.")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set that the time should be simulated
   *
   * @param simulateTime a boolean value that indicates if simulation of time is needed
   */
  public void setSimulateTime(Boolean simulateTime) {
    this.simulateTime = simulateTime;
  }

  /**
   * Get the custom local time of this moment in time
   *
   * @return the custom local time of this moment in time
   */
  public LocalTime customLocalTimeNow() {
    return customLocalDateTimeNow().toLocalTime();
  }

  /**
   * Get the custom local date of this moment in time
   *
   * @return the custom local date of this moment in time
   */
  public LocalDate customLocalDateNow() {
    return customLocalDateTimeNow().toLocalDate();
  }

  /**
   * Get the custom local date and time of this moment in time
   *
   * @return the custom local date and time of this moment in time
   */
  public LocalDateTime customLocalDateTimeNow() {
    if (simulateTime || CustomTime.isJUnitTest()) {
      return LocalDateTime.of(1999, 8, 29, 12, 22);
    }
    return LocalDateTime.now();
  }
}
