package be.kuleuven.assemassit.Domain.Helper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class CustomTimeTest {

  LocalDateTime localDateTime =  LocalDateTime.of(1999, 8, 29, 12, 22);

  @Test
  void customTimeTesT() {
    assertTrue(CustomTime.isJUnitTest());

    CustomTime customTime = CustomTime.getInstance();
    customTime.setSimulateTime(true);
    assertEquals(customTime.customLocalDateTimeNow(), localDateTime);
    assertEquals(customTime.customLocalTimeNow(), localDateTime.toLocalTime());
    assertEquals(customTime.customLocalDateNow(), localDateTime.toLocalDate());
  }

}
