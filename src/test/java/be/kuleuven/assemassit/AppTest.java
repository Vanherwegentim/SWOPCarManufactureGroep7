package be.kuleuven.assemassit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAppConstructor() {
        try {
            new be.kuleuven.assemassit.App();
        } catch (Exception e) {
            fail("Construction failed.");
        }
    }

    @Test
    public void testAppMain() {
        be.kuleuven.assemassit.App.main(null);
        try {
            assertEquals("Hello World!" + System.getProperty("line.separator"), outContent.toString());
        } catch (AssertionError e) {
            fail("\"message\" is not \"Hello World!\"");
        }
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }

}
