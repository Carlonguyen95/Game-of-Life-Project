package golClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a pool of threads. due to a lack of time we couldn't
 *  finish implementing threads.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

public class Pool {
	
	private int numWorkers = Runtime.getRuntime().availableProcessors();
	private List<Thread> wrkPool = new ArrayList<Thread>();
	
	public Pool() {}
	
	public void setTask(Runnable task) {
		for(int i = 0; i < numWorkers; i++){
            wrkPool.add(new Thread(task));
        }
	}
	
	  public void clearWorkers(){
	        wrkPool.clear();
	    }
	
	  public int getWorkers() {
		  return numWorkers;
	  }
}