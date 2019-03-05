package support_lib;

public class EffTracker {
	long[] times;
	long sum = 0;
	int index = 0;
	boolean full = false;
	String name = "";
	EffTrackerList list = null;
	
	long currentStart;
	
	public EffTracker(String name, int maxSampleSize) {
		times = new long[maxSampleSize];
		this.name = name;
	}
	
	public EffTracker(String name, int maxSampleSize, EffTrackerList list) {
		times = new long[maxSampleSize];
		this.name = name;
		this.list = list;
	}
	
	public void start() {
		currentStart = System.nanoTime();
	}
	
	public void end() {
		addTime(currentStart, System.nanoTime());
	}
	
	public void end(double multVal) {
		long endTime = System.nanoTime();
		long total = endTime - currentStart;
		total *= multVal;
		long newEndTime = currentStart + total;
		addTime(currentStart, newEndTime);
	}
	
	private void addTime(long startTime, long endTime) {
		long duration = (endTime - startTime);
		
		// remove the old sample
		sum -= times[index];
		// add new sample to sum
		sum += duration;
		// set new sample to old sample location
		times[index] = duration;
		
		index = (index + 1) % times.length;
		
		if(index == 0) {
			full = true;
		}
	}
	
	public int getAverage() {
		int samples = getSamples();
		return (int)(sum / samples);
		//return (int)((sum / samples) / (double)Math.pow(10, 9));
	}
	
	public int getSamples() {
		if(full) {
			return times.length;
		}else {
			return index;
		}
	}
	
	public void printAverage() {
		int avg = getAverage();
		System.out.println("'" + name + "' averaged " + avg + "ms over " + getSamples() + " samples");
	}
	
	public void printAverageSpecial() {
		int avg = getAverage();
		double ms = (double)(avg / 1000000000.);
		double percentageOfFrame = (double)avg / (double)list.trackers.get("total").getAverage() * 100;
		System.out.printf("'" + name + "' averaged " + avg + "ns (" + ms + "), " + "%2f" + "%% of frame\n", percentageOfFrame);
		System.out.printf("--------------------------------------------------\n");
	}
}
