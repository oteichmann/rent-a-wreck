package com.prodyna.pac.rentawreck.backend.common.monitoring;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Monitoring interceptor.
 * 
 * @author Oliver Teichmann
 * 
 */
@Interceptor
@Monitored
@Priority(Interceptor.Priority.APPLICATION)
public class MonitoringInterceptor {

	/** MBean to collect monitoring data. */
	@Inject
	private MonitoringServiceMXBean monitoringService;

	/**
	 * This method is invoked around all service operations marked with the @Monitored annotation. It reports the
	 * duration of the called method to the monitoring MBean.
	 * 
	 * @param ctx
	 *            The {@link InvocationContext}.
	 * @return The result of the invocation of ctx.proceed().
	 * @throws Exception
	 *             If an exception is thrown by subsequent processing.
	 */
	@AroundInvoke
	public Object monitorPerformance(final InvocationContext ctx) throws Exception {
		final long startTimeMillis = System.currentTimeMillis();
		Method method = ctx.getMethod();
		Object result = null;
		result = ctx.proceed();
		final long endTimeMillis = System.currentTimeMillis();

		this.monitoringService.addMethodExecutionDuration(method.getDeclaringClass().getName(), method.getName(),
				endTimeMillis - startTimeMillis);

		return result;
	}
}
