package com.prodyna.pac.rentawreck.backend.common.monitoring;

/**
 * Iterface for MBean to collect monitoring information.
 * 
 * @author Oliver Teichmann
 * 
 */
public interface MonitoringServiceMXBean {

	/**
	 * Adds the execution duration for given method to monitoring data.
	 * 
	 * @param className Class name of the method class.
	 * @param methodName
	 *            Method to save the execution duration for.
	 * @param durationMs
	 *            Execution duration in milliseconds.
	 */
	void addMethodExecutionDuration(String className, String methodName, long durationMs);

	/**
	 * Returns the max execution duration for the given method.
	 * 
	 * @param className Class name of the methods class.
	 * @param methodName Name of the method.
	 * @return Execution duration for given method.
	 */
	
	long getMaxDuration(String className, String methodName);
	/**
	 * Returns the min execution duration for the given method.
	 * 
	 * @param className Class name of the methods class.
	 * @param methodName Name of the method.
	 * @return Execution duration for given method.
	 */
	
	long getMinDuration(String className, String methodName);
	
	/**
	 * Returns the average execution duration for the given method.
	 * 
	 * @param className Class name of the methods class.
	 * @param methodName Name of the method.
	 * @return Execution duration for given method.
	 */
	long getAverageDuration(String className, String methodName);

	/**
	 * Create Diagnostics Report.
	 * 
	 * @param className Class name of the methods class.
	 * @param methodName Name of the method.
	 * @return Monitoring data report.
	 */
	String createReport(String className, String methodName);

	/**
	 * Create Diagnostics Report.
	 * 
	 * @return Monitoring data report.
	 */
	String createReport();

	/**
	 * Clears monitoring data for given method.
	 * 
	 * @param className Class name of the methods class.
	 * @param methodName
	 *            Method to clear the monitoring data for.
	 */
	void clearMonitoringData(String className, String methodName);

	/**
	 * Clears all monitoring data.
	 */
	void clearMonitoringData();


}
