package golTest;

import static org.junit.Assert.*;

/**
 * This class tests the static board by ...
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

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
