package be.kuleuven.assemassit.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import textuitester.TextUITestScriptRunner;
import textuitester.TextUITester;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderNewCarActionUITest {

  private String classpath;

  @BeforeEach
  public void beforeEach() {

    classpath = System.getProperty("java.class.path").split(File.pathSeparator)[4];
  }

  /**
   * Deze methode start de app op en sluit die meteen af door -1 in te voeren.
   * Die zou ook moeten werken op andere pc's doordat het de classpath ophaalt, best wel eens testen of het
   * effectief zo is.
   */
  @Test
  public void StartAppAndQuitImmediatelyTest() {
    TextUITester tester = new TextUITester("java -cp " + classpath + " be.kuleuven.assemassit.App");
    tester.expectLine("------- ASSEMASSIST ------");
    tester.expectLine(" 1: Authenticate");
    tester.expectLine("-1: Quit");
    tester.sendLine("-1");
    tester.expectExit(0);
  }

  /**
   * The withHour has to be change every hour to work because of the error in the estimatedTime algorithm
   */
  @Test
  public void OrderNewCarUseCaseTest() throws IOException {

    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime actualDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      actualDate = actualDate.withHour(9);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      actualDate = actualDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      //The withHour has to be change every hour to work because of the error in the estimatedTime algorithm
      actualDate = actualDate.plusDays(1).withHour(14).withMinute(0);
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String str = Files.readString(Path.of("src/test/resources/OrderNewCarActionUITest.txt"));
    Pattern p = Pattern.compile("%date%", Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(str);
    String result = m.replaceAll(actualDate.format(formatter));

    InputStream is = new ByteArrayInputStream(result.getBytes());

    TextUITestScriptRunner.runTestScript(is);
  }


}
