package be.kuleuven.assemassit.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import textuitester.TextUITestScriptRunner;
import textuitester.TextUITester;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PerformAssemblyTasksActionUITest {
  private String classpath;

  @BeforeEach
  public void beforeEach(){
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
   * Deze methode haalt het OrderNewCarActionUITest.txt script op uit resources en voert het uit.
   * De classpath staat wel hardcoded en lijn 185-193 bevat dynamische content waardoor deze test
   * daar meestal faalt.
   */
  @Test
  public void Test() throws IOException {

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("PerformAssemblyTasksActionUITest.txt");
    TextUITestScriptRunner.runTestScript(is);
  }
}
