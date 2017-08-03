package Server;

/**
 * 
 * @author L.Weber
 *
 */
public class Waiter extends Thread{
	private long hash1;
	private long hash2;
	private boolean waiter1 = true;
	private boolean waiter2 = true;
	private volatile boolean runner = true;
	
	public Waiter(long hash1, long hash2) {
		this.hash1 = hash1;
		this.hash2 = hash2;
	}

	@Override
	public void run() {
		while(runner) {
		}
	}
	
	public void setWaiter1(boolean waiter1) {
		this.waiter1 = waiter1;
	}
	
	public void setWaiter2(boolean waiter2) {
		this.waiter2 = waiter2;
	}
	
	public long getHash1() {
		return this.hash1;
	}
	
	public long getHash2() {
		return this.hash2;
	}
	
	public boolean getWaiter1() {
		return this.waiter1;
	}
	
	public boolean getWaiter2() {
		return this.waiter2;
	}
	
	public void setRunner(boolean runner) {
		this.runner = runner;
	}

}
