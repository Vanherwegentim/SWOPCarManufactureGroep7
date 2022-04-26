package be.kuleuven.assemassit.Domain.Enums;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkPostTypeTest {
  List<WorkPostType> workPostTypes = List.of(WorkPostType.CAR_BODY_POST, WorkPostType.DRIVETRAIN_POST, WorkPostType.ACCESSORIES_POST);
  List<String> workPostStrings = List.of("Car Body Post", "Drivetrain Post", "Accessories Post");

  @Test
  void values() {
    List<WorkPostType> values = List.of(WorkPostType.values());
    assertEquals(values, workPostTypes);
  }

  @Test
  void valueOf() {
    List<String> values = Stream.of(WorkPostType.values()).map(Enum::toString).toList();
    assertEquals(values, workPostStrings);

  }
}
