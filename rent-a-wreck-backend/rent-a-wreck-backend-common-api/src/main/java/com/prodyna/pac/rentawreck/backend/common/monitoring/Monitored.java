package com.prodyna.pac.rentawreck.backend.common.monitoring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Annotation to mark monitored services.
 * 
 * @author Oliver Teichmann
 * 
 */
@Inherited
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitored {

}
