package be.kuleuven.assemassit.UI;

import org.junit.jupiter.api.Test;
import textuitester.TextUITestScriptRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CheckAssemblyLineStatusActionUITest {

  @Test
  public void CheckAssemblyLineStatusActionUITest() throws IOException {
    String str = Files.readString(Path.of("src/test/resources/CheckAssemblyLineStatusActionUITest.txt"));
    InputStream is = new ByteArrayInputStream(str.getBytes());
    TextUITestScriptRunner.runTestScript(is);
  }
}
