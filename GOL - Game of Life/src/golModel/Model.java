package golModel;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Model extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 6321458303783789824L;
	
	// Spillets interne objekter
	private int cellSize = 10;
	private byte[][] board = new byte[100][100];
	
    private GraphicsContext gc;
    private ColorPicker colorChanger;
    private Canvas graphics;

    // Game loop
    public BufferedImage image;
	private Thread thread;
	private boolean running = false;
	
	
	public synchronized void start() {		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
    public void run() {    	
    	while(running) {
    		System.out.print("Running");
    		
    		nextGeneration();
    		render();   		
    	}
    }


	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.dispose();
		bs.show();
		bs.dispose();
	}

	public void nextGeneration() {
		
	}
	
	/**
	 * Draw metoder
	 */

	
}
