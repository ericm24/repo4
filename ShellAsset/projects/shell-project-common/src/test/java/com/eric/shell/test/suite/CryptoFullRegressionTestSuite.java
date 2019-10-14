package com.eric.shell.test.suite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eric.shell.command.crypto.DecryptCommand_UT;
import com.eric.shell.command.crypto.EncryptCommand_UT;
import com.eric.shell.domain.adapter.CryptoAdapter_UT;
import com.eric.shell.validator.CryptoCommandValidator_UT;
import com.eric.utils.crypto.CryptoHelper_UT;
import com.eric.utils.file.FileHelper_UT;
import com.eric.utils.string.StringHelper_UT;


@RunWith( Suite.class )
@Suite.SuiteClasses({
						StringHelper_UT.class,
						FileHelper_UT.class,
						CryptoHelper_UT.class,
						CryptoCommandValidator_UT.class,
						CryptoAdapter_UT.class,						
						EncryptCommand_UT.class,
						DecryptCommand_UT.class
					})
/**
 * This Suite is the FULL-REGRESSION test Suite (ie Non-DIT).
 *  
 * @author eric
 * 
 */					

public class CryptoFullRegressionTestSuite
{

	private static final Log methIDfirstMethod;
	private static final Log methIDlastMethod;
	
	static 
	{
		methIDfirstMethod = LogFactory
			.getLog(CryptoFullRegressionTestSuite.class.getName() + ".firstMethod()");
		
		methIDlastMethod = LogFactory
			.getLog(CryptoFullRegressionTestSuite.class.getName() + ".lastMethod()");
		
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
