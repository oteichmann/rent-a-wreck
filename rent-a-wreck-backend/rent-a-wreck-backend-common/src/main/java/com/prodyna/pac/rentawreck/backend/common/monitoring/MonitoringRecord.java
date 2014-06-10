/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common.monitoring;

/**
 * MonitoringRecord
 *
 * @author Oliver Teichmann
 *
 */
public class MonitoringRecord {
	
	private String key;
	private long invocationCount = 0;
	private long totalDuration = 0;
	private long averageDuration = 0;
	private long maxDuration = 0;
	private long minDuration = 0;

	/**
	 * @param key
	 */
	public MonitoringRecord(String key) {
		this.key = key;
	}

	/**
	 * @param duration
	 */
	public void update(final long duration) {
		this.invocationCount++;
		this.totalDuration += maxDuration;
		this.averageDuration = totalDuration / invocationCount;
		
		if(duration > this.maxDuration) this.maxDuration = duration;
		if(duration < this.minDuration) this.minDuration = duration;
	}

	public String getKey() {
		return key;
	}

	public long getInvocationCount() {
		return invocationCount;
	}

	public long getTotalDuration() {
		return totalDuration;
	}

	public long getAverageDuration() {
		return averageDuration;
	}

	public long getMaxDuration() {
		return maxDuration;
	}

	public long getMinDuration() {
		return minDuration;
	}
	
	
}
