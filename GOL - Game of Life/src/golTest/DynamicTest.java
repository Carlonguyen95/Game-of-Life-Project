package golTest;

import golController;

public class DynamicTest {
	DynamicBoard board = new GameBoardDynamic(10,10);
	board.setCellState(5, 5, true);
	org.junit.Assert.assertEquals(board.getCellState(5, 5), true);
	board.setCellState(50, 50, true);
	org.junit.Assert.assertEquals(board.getCellState(50, 50), true);
	org.junit.Assert.assertEquals(board.getCellState(49, 49), false);
	org.junit.Assert.assertEquals(board.getCellState(51, 51), false);
}
