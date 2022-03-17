package textuitesterexample;

import java.io.IOException;

import org.junit.Test;

import textuitester.TextUITestScriptRunner;
import textuitester.TextUITester;
import textuitester.TextUITesterException;

public class Test1 {

	@Test
	public void test() {
		TextUITester tester = new TextUITester("java -cp examplebin textuitesterexample.Echo");
		tester.sendLine("Hello World!");
		tester.expectLine("Hello World!");
		tester.expectExit(0);
	}
	
	@Test(expected=TextUITesterException.class)
	public void lineMismatchFailureTest() {
		TextUITester tester = new TextUITester("java -cp examplebin textuitesterexample.Echo");
		tester.sendLine("Hello World!");
		tester.expectLine("Bye World!");
	}
	
	@Test(expected=TextUITesterException.class)
	public void timeoutFailureTest() {
		TextUITester tester = new TextUITester("java -cp examplebin textuitesterexample.Echo");
		tester.expectLine("Hello World!");
	}
	
	@Test
	public void scriptTest() throws IOException {
		TextUITestScriptRunner.runTestScript(Test1.class, "testScript1.txt");
	}
	
	@Test
	public void scriptRunnerTestScriptTest() throws IOException {
		TextUITestScriptRunner.runTestScript(Test1.class, "scriptRunnerTestScript.txt");
	}
	
}
