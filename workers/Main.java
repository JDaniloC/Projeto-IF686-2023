import java.util.concurrent.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    FileReader fileReader = new FileReader("./input.txt");
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    // Scanner scanner = new Scanner(System.in);

    String[] numberWorkersAndTasks = bufferedReader.readLine().split(" ");
    int numberWorkers = Integer.parseInt(numberWorkersAndTasks[0]);
    int taskAmount = Integer.parseInt(numberWorkersAndTasks[1]);

  	LinkedBlockingQueue<Task> tasks = new LinkedBlockingQueue<Task>();
    for (int i = 0; i < taskAmount; i++) {
      	// String[] line = scanner.nextLine().split(" ");
		String[] line = bufferedReader.readLine().split(" ");
		
		int taskID = Integer.parseInt(line[0]);
		int taskTime = Integer.parseInt(line[1]);
		int[] dependencies = new int[line.length - 2];
		for (int j = 2; j < line.length; j++) {
			dependencies[j - 2] = Integer.parseInt(line[j]);
		}
		tasks.add(new Task(taskID, taskTime, dependencies));
    }

	Queen queen = new Queen(taskAmount, tasks);
    ExecutorService es = Executors.newFixedThreadPool(numberWorkers);
  	for (int j = 0; j < taskAmount; j++) {
		Operaria operaria = new Operaria(queen);
		es.execute(operaria);
	}
  	es.shutdown();

	while (!es.isTerminated()) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
	}
  }
}