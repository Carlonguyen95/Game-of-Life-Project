package golClasses;

import java.util.ArrayList;
import java.util.List;

public class Pool {
	
	private int numWorkers = Runtime.getRuntime().availableProcessors();
	private List<Runnable> wrkPool = new ArrayList<Runnable>();
	
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
