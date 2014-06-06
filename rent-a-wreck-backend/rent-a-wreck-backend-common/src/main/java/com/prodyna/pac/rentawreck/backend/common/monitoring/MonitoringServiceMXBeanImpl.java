package com.prodyna.pac.rentawreck.backend.common.monitoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	private final Map<String, ArrayList<Long>> methodExecutionDurationMap = new HashMap<String, ArrayList<Long>>();

	/**
	 * Registers MBean to JMX.
	 */
	@PostConstruct
	public void registerInJMX() {

		try {
			this.objectName = new ObjectName("com.prodyna.pac.rentawreck.backend.common.monitoring:type=" + this.getClass().getName());

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

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#addMethodExecutionDuration(java.lang.String, long)
	 */
	@Override
	public void addMethodExecutionDuration(final String className, final String methodName, final long durationMs) {
		String key = createKey(className, methodName);
		
		ArrayList<Long> durations = null;
		
		if(this.methodExecutionDurationMap.containsKey(key)) {
			durations = this.methodExecutionDurationMap.get(key);
			if (durations == null) {
				durations = new ArrayList<Long>();
				this.methodExecutionDurationMap.put(key, durations);
			}
		} else {
			durations = new ArrayList<Long>();
			this.methodExecutionDurationMap.put(key, durations);
		}
		durations.add(durationMs);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getMaxDuration(java.lang.String)
	 */
	@Override
	public long getMaxDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);
		
		return getMaxDuration(key);
	}

	private long getMaxDuration(String key) {
		long result = -1;

		ArrayList<Long> durations = this.methodExecutionDurationMap.get(key);
		
		final Long duration = Collections.max(durations);
		if (duration != null) {
			result = duration;
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getMinDuration(java.lang.String)
	 */
	@Override
	public long getMinDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);
		
		return getMinDuration(key);
	}

	private long getMinDuration(final String key) {
		long result = -1;

		ArrayList<Long> durations = this.methodExecutionDurationMap.get(key);
		
		final Long duration = Collections.min(durations);
		if (duration != null) {
			result = duration;
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#getAverageDuration(java.lang.String)
	 */
	@Override
	public long getAverageDuration(final String className, final String methodName) {
		String key = createKey(className, methodName);
		
		return getAverageDuration(key);
	}

	private long getAverageDuration(String key) {
		ArrayList<Long> durations = this.methodExecutionDurationMap.get(key);
		
		long durationSum = 0;
		
		for (Long duration : durations) {
			durationSum += duration;
		}
		
		long averageDuration = durationSum / durations.size();

		return averageDuration;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#clearMonitoringData(java.lang.String)
	 */
	@Override
	public void clearMonitoringData(final String className, final String methodName) {
		this.methodExecutionDurationMap.remove(methodName);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#clearMonitoringData()
	 */
	@Override
	public void clearMonitoringData() {
		this.methodExecutionDurationMap.clear();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.monitoring.PerformanceMonitoringService#createReport()
	 */
	@Override
	public String createReport() {
		final StringBuilder diagnosticsStringBuilder = new StringBuilder();

		final Set<Entry<String, ArrayList<Long>>> entrySet = this.methodExecutionDurationMap.entrySet();
		for (final Entry<String, ArrayList<Long>> entry : entrySet) {
			String key = entry.getKey();
			
			String methodDiagnostic = String.format("%s: invocations=%s, average=%sms, min=%sms, max=%sms", key, entry.getValue().size(), getAverageDuration(key), getMinDuration(key), getMaxDuration(key));
			diagnosticsStringBuilder.append(methodDiagnostic);
			diagnosticsStringBuilder.append("\n");
		}

		return diagnosticsStringBuilder.toString();
	}
	
	private String createKey(final String className, final String methodName) {
		return className + "#" + methodName;
	}
}
