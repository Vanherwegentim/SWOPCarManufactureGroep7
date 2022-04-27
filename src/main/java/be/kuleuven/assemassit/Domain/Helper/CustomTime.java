package be.kuleuven.assemassit.Domain.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomTime {
  public LocalDateTime customLocalDateTimeNow() {
    return LocalDateTime.now();
  }

  public LocalTime customLocalTimeNow() {
    return customLocalDateTimeNow().toLocalTime();
  }

  public LocalDate customLocalDateNow() {
    return customLocalDateTimeNow().toLocalDate();
  }
}
