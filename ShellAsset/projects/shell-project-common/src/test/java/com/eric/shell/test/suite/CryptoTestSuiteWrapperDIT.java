/**
*/
package com.eric.shell.test.suite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith( Suite.class )
@Suite.SuiteClasses({ 
						CryptoCommandTest_DIT.class
					})

/**
 * The purpose of this class is to provide a JUnitRunner-Level Suite-Wrapper Class.  This adds the
 * before() & after() methods at the suite level.
 * 
 * @author em32459
 * 
 */		
public class CryptoTestSuiteWrapperDIT
{

	private static final Log methIDfirstMethod;
	private static final Log methIDlastMethod;
	
	static 
	{
		methIDfirstMethod = LogFactory
			.getLog(CryptoTestSuiteWrapperDIT.class.getName() + ".firstMethod()");
		
		methIDlastMethod = LogFactory
			.getLog(CryptoTestSuiteWrapperDIT.class.getName() + ".lastMethod()");
		
	}	
	
	@BeforeClass
	public static void firstMethod()
	{		
		Log logger = methIDfirstMethod;		
		logger.info("Begin DIT Test Suite!");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Log logger = methIDlastMethod;		
		logger.info("End DIT Test Suite!");		
		return;
	}
	
}
