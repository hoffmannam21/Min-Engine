package support_lib;

import java.util.HashMap;

public class EffTrackerList {
	HashMap<String, EffTracker> trackers;
	boolean show = false;

	public EffTrackerList() {
		trackers = new HashMap<String, EffTracker>();
	}

	public void start(String trackerName) {
		EffTracker et = trackers.get(trackerName);
		if(null == et) {
			et = new EffTracker(trackerName, 100000, this);
			trackers.put(trackerName, et);
		}
		et.start();
	}

	public void end(String trackerName) {
		EffTracker et = trackers.get(trackerName);
		if(null == et) {
			System.err.println("Error in EffTrackerList:end, tracker doesn't exist");
		}else {
			et.end();
		}
	}
	
	public void end(String trackerName, double multVal) {
		EffTracker et = trackers.get(trackerName);
		if(null == et) {
			System.err.println("Error in EffTrackerList:end, tracker doesn't exist");
		}else {
			et.end(multVal);
		}
	}

	public void print() {
		if(show) {
			for(EffTracker e : trackers.values()) {
				e.printAverageSpecial();
			}
		}
	}
}
