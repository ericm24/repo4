/*
 * Created by Eric Manley on Dec 12, 2008 
 */

package com.eric.shell.aop.interceptor;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Interceptor that provides trace logging for all non-domain, non-aspect
 * classes, if enabled.
 * 
 * @author eric.manley
 */
@Aspect
public class LoggingInterceptor
{
	
	//private final Logger log = Logger.getLogger( getClass() );
	
	private final Logger log = Logger.getLogger( getClass().getName() );	
	
	@Around("(( execution(* com.eric.shell..*.*(..)) || execution(* com.eric.shell..*.*(..)) ) "
			+ " && !(execution(* com.eric.shell.domain.*.*(..)))"
			+ " && !(execution(* com.eric.shell.aop.*.*(..))) )")
			
	public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable
	{		
		
		if ( log.isDebugEnabled() )
		{
			Logger log2 		= null;			
			String loggerName 	= null;			
			String methodName 	= joinPoint.toShortString();
		
			loggerName 			= joinPoint.getTarget().getClass().toString()
									.substring( 6 ) + "."; 			

			methodName = methodName.substring(methodName.indexOf(".") + 1 );

			// Re-scan methodName to remove extraneous parens.  This happens when hooked from parent, abstract
			// classes, etc.
			
			methodName = methodName.substring(0, methodName.indexOf("(")) + "()";
			
			loggerName += methodName;
			
			// Watch this to make sure you get the correct logger....
			log2 = log.getLogger( loggerName );	
			
			log2.debug("BeginsAOP.....");
		}

		final Object returnValue = joinPoint.proceed( joinPoint.getArgs() );

		if ( log.isDebugEnabled() )
		{
			Logger log2 		= null;			
			String loggerName 	= null;			
			String methodName 	= joinPoint.toShortString();

			loggerName 			= joinPoint.getTarget().getClass().toString()
									.substring( 6 ) + "."; 			
			
			methodName 			= methodName.substring(methodName.indexOf(".") + 1 );

			// Re-scan methodName to remove extraneous parens.  This happens when hooked from parent, abstract
			// classes, etc.
			
			methodName = methodName.substring(0, methodName.indexOf("(")) + "()";
			
			loggerName += methodName;
			
			log2= Logger.getLogger( loggerName );			
			
			log2.debug("EndsAOP.....");			
		}

		return returnValue;
	}

}
