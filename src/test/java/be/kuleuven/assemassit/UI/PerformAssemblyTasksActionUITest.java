package be.kuleuven.assemassit.UI;

import org.junit.jupiter.api.Test;
import textuitester.TextUITestScriptRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformAssemblyTasksActionUITest {

  /**
   * The withHour has to be change every hour to work because of the error in the estimatedTime algorithm
   */

  @Test
  public void PerformAssemblyTasksUseCaseTest() throws IOException {
    String str = Files.readString(Path.of("src/test/resources/PerformAssemblyTasksActionUITest.txt"));
    InputStream is = new ByteArrayInputStream(str.getBytes());
    TextUITestScriptRunner.runTestScript(is);
  }

}
