public class Plane implements Runnable {
	private static long nextId = 0;

	private long id;
	private long startTime;
	private int scheduledTime;
	private boolean willTakeoff;

	private ATC controlTower;
	
	public Plane(int time, boolean direction, ATC myController) {
		this.controlTower = myController;
		this.willTakeoff = direction;
		this.scheduledTime = time;
		this.id = nextId;
		nextId ++;
	}

	private void logAction() {
		String action = this.willTakeoff ? "decolou" : "pousou";
		Long waitTime = System.currentTimeMillis() - this.startTime;
		Long lateTime = waitTime - this.scheduledTime;
		System.out.printf("Avião de ID %d %s em %dms ao invés de %dms com atraso de %dms\n",
						  id, action, waitTime, scheduledTime, lateTime);
	}

	@Override
	public void run() {
		try {
			this.startTime = System.currentTimeMillis();
			Thread.sleep(this.scheduledTime);
			controlTower.requestRunway();
		
			this.logAction();
			Thread.sleep(500);
			
			controlTower.freeRunway();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}