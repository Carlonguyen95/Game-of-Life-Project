package golTest;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {
	private Test_Class testBoard = new Test_Class();
	 
	@Test
	public void test() {
		testBoard.testNextGeneration();
		 
	     Assert.assertEquals(testBoard.toString(),"0000011001100000");

	}

}
