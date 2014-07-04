package com.prodyna.pac.rentawreck.backend.common.monitoring;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * MBean implementation of {@link MonitoringService}.
 * 
 * @author Oliver Teichmann
 * 
 */
@Singleton
@Startup
public class MonitoringServiceMXBeanImpl implements MonitoringServiceMXBean {

	/** {@link MBeanServer} instance */
	@Inject
	private MBeanServer platformMBeanServer;

	/** MBean name to register at MBeanServer. */
	private ObjectName objectName = null;

	/** Map containing the last duration for each method ever has been added. */
	private final Map<String, MonitoringRecord> methodExecutionDurationMap = new HashMap<String, MonitoringRecord>();

	/**
	 * Registers MBean to JMX.
	 */
	@PostConstruct
	public void registerInJMX() {

		try {
			this.objectName = new ObjectName("com.prodyna.pac.rentawreck.backend.common.monitoring:type="
					+ this.getClass().getName());

			this.platformMBeanServer.registerMBean(this, this.objectName);
		} catch (final JMException e) {
			throw new IllegalStateException("JMX registration failed.", e);
		}
	}

	/**
	 * Unregisters this MBean from JMX.
	 */
	@PreDestroy
	public void unregisterFromJMX() {
		try {
			this.platformMBeanServer.unregisterMBean(this.objectName);
		} catch (final JMException e) {
			throw new IllegalStateException("Problem during unregistration of performance monitoring from JMX", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#addMethodExecutionDuration(
	 * java.lang.String, long)
	 */
	@Override
	public void addMethodExecutionDuration(final String className, final String methodName, final long durationMs) {
		String key = createKey(className, methodName);

		MonitoringRecord monitoringRecord = this.methodExecutionDurationMap.get(key);

		if (monitoringRecord == null) {
			monitoringRecord = new MonitoringRecord(key);
			this.methodExecutionDurationMap.put(key, monitoringRecord);
		}

		monitoringRecord.update(durationMs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getMaxDuration(java.lang.String
	 * )
	 */
	@Override
	public long getMaxDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);

		return getMaxDuration(key);
	}

	private long getMaxDuration(String key) {
		MonitoringRecord monitoringRecord = this.methodExecutionDurationMap.get(key);

		return monitoringRecord.getMaxDuration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getMinDuration(java.lang.String
	 * )
	 */
	@Override
	public long getMinDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);

		return getMinDuration(key);
	}

	private long getMinDuration(final String key) {
		MonitoringRecord monitoringRecord = this.methodExecutionDurationMap.get(key);

		return monitoringRecord.getMinDuration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getAverageDuration(java.lang
	 * .String)
	 */
	@Override
	public long getAverageDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);

		return getAverageDuration(key);
	}

	private long getAverageDuration(String key) {
		MonitoringRecord monitoringRecord = this.methodExecutionDurationMap.get(key);

		return monitoringRecord.getAverageDuration();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.MonitoringServiceMXBean#createReport(java.lang.String, java.lang.String)
	 */
	@Override
	public String createReport(String className, String methodName) {
		String key = createKey(className, methodName);
		MonitoringRecord monitoringRecord = this.methodExecutionDurationMap.get(key);
		
		if (monitoringRecord != null) {
			return createMethodDiagnostic(monitoringRecord);
		} else {
			return "";
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#createReport()
	 */
	@Override
	public String createReport() {
		final StringBuilder diagnosticsStringBuilder = new StringBuilder();

		for (MonitoringRecord monitoringRecord : this.methodExecutionDurationMap.values()) {

			String methodDiagnostic = createMethodDiagnostic(monitoringRecord);
			diagnosticsStringBuilder.append(methodDiagnostic);
			diagnosticsStringBuilder.append("\n");
		}

		return diagnosticsStringBuilder.toString();
	}

	/**
	 * Creates a String representation of a monitoring record for printing.
	 * @param monitoringRecord The record to print.
	 * @return The diagnostics string.
	 */
	private String createMethodDiagnostic(final MonitoringRecord monitoringRecord) {
		String methodDiagnostic = String.format("%s: invocations=%s, total=%sms, average=%sms, min=%sms, max=%sms",
				monitoringRecord.getKey(), monitoringRecord.getInvocationCount(), 
				monitoringRecord.getTotalDuration(), monitoringRecord.getAverageDuration(),
				monitoringRecord.getMinDuration(), monitoringRecord.getMaxDuration());
		return methodDiagnostic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#clearMonitoringData(java.lang
	 * .String)
	 */
	@Override
	public void clearMonitoringData(final String className, final String methodName) {
		this.methodExecutionDurationMap.remove(methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#clearMonitoringData()
	 */
	@Override
	public void clearMonitoringData() {
		this.methodExecutionDurationMap.clear();
	}
	
	/**
	 * Create key for monitoring records.
	 * @param className The name of the service class.
	 * @param methodName The name of the service method.
	 * @return The key of the monitoring record.
	 */
	private String createKey(final String className, final String methodName) {
		return className + "#" + methodName;
	}

}
