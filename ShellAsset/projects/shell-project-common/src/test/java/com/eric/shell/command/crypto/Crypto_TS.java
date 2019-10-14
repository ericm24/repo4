/**
*/
package com.eric.shell.command.crypto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.test.AbstractBaseTestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()


// Primary function of this class is to use DI for deps, and enable AOP.

public class Crypto_TS extends AbstractBaseTestCase
{
	
	@Autowired
	@Qualifier("decryptCommandTest_UT")
	private DecryptCommand_UT decryptCommandTest_UT;
	
	@Autowired
	@Qualifier("encryptCommandTest_UT")
	private EncryptCommand_UT encryptCommandTest_UT;

	private boolean masterFlag = true;	

	
	public void runAllTests()
	{

		this.runDecryptTests();
		
		this.runEncryptTests();
		
		return;
	}
	
	
	
	@Test
	public void runDecryptTests()
	{		
		
		//masterFlag = false;
		decryptCommandTest_UT.setAssertON( masterFlag );		
		
		// Notice the difference in the # of methods you get AOP book-end
		// logging for by calling the .runAllTests() [ which wraps  the same 
		// methods ] instead of calling them individually.
		
		// Called individually, you get AOP book-end logging on each
		decryptCommandTest_UT.runTestDecryptCommandNullContextFAIL();		
		
		decryptCommandTest_UT.setUpDefaultTargetFile();
		decryptCommandTest_UT.runTestDecryptCommandDESFileMissingFileFAIL();
		decryptCommandTest_UT.checkOutputFileMatch();
		
		decryptCommandTest_UT.setUpDefaultTargetFile();
		decryptCommandTest_UT.runTestDecryptCommandIllegalBlockSizeFAIL();
		decryptCommandTest_UT.checkOutputFileMatch();
	
		decryptCommandTest_UT.setUpDefaultTargetFile();
		decryptCommandTest_UT.runTestDecryptCommandPasswordTooShortFAIL();
		decryptCommandTest_UT.checkOutputFileMatch();

		decryptCommandTest_UT.setUpDefaultTargetFile();
		decryptCommandTest_UT.runTestDecryptCommandDESStringSUCCESS();
		decryptCommandTest_UT.checkOutputFileMatch();
		
		return;
	}


	@Test
	public void runEncryptTests()
	{
		//masterFlag = false;		

		encryptCommandTest_UT.setAssertON( masterFlag );

		// Notice the difference in the # of methods you get AOP book-end
		// logging for by calling the .runAllTests() [ which wraps  the same 
		// methods ] instead of calling them individually.

		// Called individually, you get AOP book-end logging on each

		encryptCommandTest_UT.runTestEncryptCommandNullContextFAIL();		
		
		encryptCommandTest_UT.setUpDefaultTargetFile();
		encryptCommandTest_UT.runTestEncryptCommandPasswordTooShortFAIL();
		encryptCommandTest_UT.checkOutputFileMatch();
		
		encryptCommandTest_UT.setUpDefaultTargetFile();
		encryptCommandTest_UT.runTestEncryptCommandDESFileMissingFileFAIL();
		encryptCommandTest_UT.checkOutputFileMatch();
		
		encryptCommandTest_UT.setUpDefaultTargetFile();
		encryptCommandTest_UT.runTestEncryptCommandDESFileSUCCESS();
		encryptCommandTest_UT.checkOutputFileMatch();
		
		encryptCommandTest_UT.setUpDefaultTargetFile();
		encryptCommandTest_UT.runTestEncryptCommandDESEncryptAndDecryptFileSUCCESS();
		encryptCommandTest_UT.checkOutputFileMatch();

		encryptCommandTest_UT.setUpDefaultTargetFile();
		encryptCommandTest_UT.runTestEncryptCommandDESStringSUCCESS();
		encryptCommandTest_UT.checkOutputFileMatch();
		
		
		return;
	}
	
	

}
