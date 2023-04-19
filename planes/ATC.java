public class ATC {
    private int totalRunway = 0;
    private int avaliableRunway = 0;
	
    public ATC() {}

	public void setRunways(int totalRunway) {
		this.totalRunway = totalRunway;
	    this.avaliableRunway = totalRunway;
	}
	
    public synchronized void requestRunway() throws InterruptedException {
        while (this.avaliableRunway == 0) {
            wait();
        }
        this.avaliableRunway--;
        notifyAll();
    }

    public synchronized void freeRunway() throws InterruptedException {
        while (this.totalRunway == this.avaliableRunway) {
            wait();
        }
        this.avaliableRunway++;
        notifyAll();
    }
}