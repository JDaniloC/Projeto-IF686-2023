public class Task {
	private int id;
	private long timeToComplete;
	private int[] dependenciesID;
	
	public Task(int id, long timeToComplete, int[] dependencies) {
		this.id = id;
		this.dependenciesID = dependencies;
		this.timeToComplete = timeToComplete;
	}

	public int getId() {
		return id;
	}

	public long getTimeToComplete() {
		return timeToComplete;
	}

	public int[] getDependenciesID() {
		return dependenciesID;
	}
}