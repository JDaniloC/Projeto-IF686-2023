import java.util.concurrent.*;

public class Queen {
    boolean[] completedTasks;
    LinkedBlockingQueue<Task> tasks;
    
    public Queen(int taskAmount, LinkedBlockingQueue<Task> tasks) {
        this.completedTasks = new boolean[taskAmount];
		this.tasks = tasks;
    }
    
	public void finishTask(int taskID) {
		System.out.printf("tarefa %d feita\n", taskID);
		this.completedTasks[taskID - 1] = true;
	}

  	public Task getTask() {
		boolean canTake = false;
		Task task = tasks.poll();
		while (!this.canTakeTask(task.getDependenciesID())) {
			this.tasks.add(task);
			task = tasks.poll();
		}
		return task;            
	}
  
    private boolean canTakeTask(int[] dependencies) {
      for (int id : dependencies) {
        if (!completedTasks[id - 1]) {
          return false;
        }
      }
      return true;
    }
}