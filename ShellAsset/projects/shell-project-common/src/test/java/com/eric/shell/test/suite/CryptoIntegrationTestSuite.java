package com.eric.shell.test.suite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.shell.command.crypto.DecryptCommand_UT;
import com.eric.shell.command.crypto.EncryptCommand_UT;


@RunWith( Suite.class )
@Suite.SuiteClasses({
						EncryptCommand_UT.class,
						DecryptCommand_UT.class
					})
/**
 * This Suite is the Integration test Suite.
 *  
 * @author eric
 * 
 */							
public class CryptoIntegrationTestSuite
{

	private static final Log methIDfirstMethod;
	private static final Log methIDlastMethod;
	
	static 
	{
		methIDfirstMethod = LogFactory
			.getLog(CryptoIntegrationTestSuite.class.getName() + ".firstMethod()");
		
		methIDlastMethod = LogFactory
			.getLog(CryptoIntegrationTestSuite.class.getName() + ".lastMethod()");
		
	}	
	
	@BeforeClass
	public static void firstMethod()
	{		
		Log logger = methIDfirstMethod;		
		logger.info("Start of Test Suite....");		
		return;
	}	
	
	@AfterClass
	public static void lastMethod()
	{
		Log logger = methIDlastMethod;		
		logger.info("End of Test Suite....");		
		return;
	}
	
}
