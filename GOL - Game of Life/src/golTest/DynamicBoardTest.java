package golTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import golClasses.DynamicBoard;

/**
 * This class is used to testing the NextGeneration method
 * in the dynamic board
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

public class DynamicBoardTest {

	@Test
	public void test() throws Exception {
		DynamicBoard board = new DynamicBoard(null, null, null, null);
		board.setCellState(5, 5);
		org.junit.Assert.assertEquals(board.getCellStateTest(5, 5), 1);
		board.setCellState(50, 50);
		org.junit.Assert.assertEquals(board.getCellStateTest(50, 50), 1);
		org.junit.Assert.assertEquals(board.getCellStateTest(49, 49), 0);
		org.junit.Assert.assertEquals(board.getCellStateTest(51, 51), 0);
	}

}