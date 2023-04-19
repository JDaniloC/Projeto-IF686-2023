public class Operaria implements Runnable {
	private Queen controller;
	public Operaria(Queen controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		try {
			Task task = this.controller.getTask();
			Thread.sleep(task.getTimeToComplete());
			this.controller.finishTask(task.getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}