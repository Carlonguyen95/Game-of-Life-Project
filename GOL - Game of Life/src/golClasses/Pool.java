package golClasses;

import java.util.ArrayList;
import java.util.List;

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

	public void runThreads() throws InterruptedException {

		for(Thread t: wrkPool) {
			t.start();
		}

		try {
			for(Thread t : wrkPool){
				t.join();
			}
		}
		catch(InterruptedException i) {
			Error e = new Error();
			e.interruptedError();
		}
	}
}