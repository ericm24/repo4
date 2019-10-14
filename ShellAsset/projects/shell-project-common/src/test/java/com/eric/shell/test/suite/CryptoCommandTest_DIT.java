/**
* ---------------------------------------------------------------------------
* COPYRIGHT NOTICE  Copyright (c) 2011 by Citigroup, Inc.  All rights reserved.
* These materials are confidential and proprietary to  Citigroup, Inc.
* No part of this code may be reproduced, published in
* any form by any means (electronic or mechanical, including photocopy or
* any information storage or retrieval system), nor may the materials be
* disclosed to third parties, or used in derivative works without the
* express written authorization of Citigroup, Inc.
* ---------------------------------------------------------------------------
*/
package com.eric.shell.test.suite;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.shell.command.Command;
import com.eric.shell.command.crypto.AbstractBaseCryptoTestCommand;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.TargetFileOption;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})

/**
 * This is the actual set of DIT  test cases.  This class is wrappered by the JUnitRunner Suite
 * class.
 * 
 * @author em32459
 * @see CryptoCommandTestDIT
 * 
 */
public class CryptoCommandTest_DIT extends AbstractBaseCryptoTestCommand
{

	@Autowired
	@Qualifier("encryptCommand") 
	private Command encryptCommand;

	@Autowired
	@Qualifier("decryptCommand")
	private Command decryptCommand;

	private static Log methIDrunTestEncryptCommandEncryptAndDecryptFileSUCCESS;
	private static Log methIDrunTestEncryptCommandDecryptAndEncryptFileSUCCESS;
	
	private static Log methIDSetUpDefaultTargetFile;
	private static Log methIDCheckOutputFileMatch;
	
	static
	{
	
		methIDrunTestEncryptCommandEncryptAndDecryptFileSUCCESS = LogFactory
				.getLog(CryptoCommandTest_DIT.class.getName()
						+ ".runTestEncryptCommandEncryptAndDecryptFileSUCCESS()");		

		methIDrunTestEncryptCommandDecryptAndEncryptFileSUCCESS = LogFactory
				.getLog(CryptoCommandTest_DIT.class.getName()
						+ ".runTestEncryptCommandDecryptAndEncryptFileSUCCESS()");		
		
		methIDSetUpDefaultTargetFile = LogFactory
				.getLog(CryptoCommandTest_DIT.class.getName()
						+ ".setUpDefaultTargetFile()");
		
		methIDCheckOutputFileMatch = LogFactory
				.getLog(CryptoCommandTest_DIT.class.getName()
						+ ".checkOutputFileMatch()");
	}

	
	public  void CryptoCommand_DIT()
	{
		this.CryptoCommand_DIT( true );
	}
	
	public  void CryptoCommand_DIT(boolean newValue)
	{
		this.setAssertON( newValue );
	}	
	
	public void runAllTests()
	{	
		
		this.runTestEncryptCommandDecryptAndEncryptFileSUCCESS();
		this.checkOutputFileMatch();		
		
		this.runTestEncryptCommandEncryptAndDecryptFileSUCCESS();		
		this.checkOutputFileMatch();
		
		return;
	}	
	//@Before
	public void setUpDefaultTargetFile(TargetFileOption targetFileOption)
	{

		Log logger = methIDSetUpDefaultTargetFile;

		logger.debug(BaseConstants.BEGINS);

		this.writeTargetFile( targetFileOption.toString() );

		logger.debug(BaseConstants.ENDS);

		return;
	}

	@Test
	public void runTestEncryptCommandEncryptAndDecryptFileSUCCESS()
	{
		Log logger = methIDrunTestEncryptCommandEncryptAndDecryptFileSUCCESS;
		boolean returnValue = false;


		HashMap<String, Object> params = null;
		ShellContext context = null;
		
		// Start with Regular Text File.
		this.setUpDefaultTargetFile( TargetFileOption.TEST_FILE_CONTENTS_TEXT );
		
		// Result should be plain text.
		this.setTargetFileResult( TargetFileOption.TEST_FILE_CONTENTS_TEXT );
		
		// Set this to true, as the files should match!
		this.setTargetFileMatches( true );

		logger.info("CryptoCommandTest En->De Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap();

		params.put(CommandConstants.COMMAND_OPTION,
				   CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		encryptCommand.setContext(context);

		logger.debug("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue(returnValue);
		}


		context = null;
		context = new ShellContext();

		params = new HashMap();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);
		
		decryptCommand.setContext(context);

		logger.debug("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertTrue(returnValue);
		}

		logger.info("CryptoCommandTest En->De Ends...");

		return;
		
	}	
	
	@Test
	public void runTestEncryptCommandDecryptAndEncryptFileSUCCESS()
	{
		Log logger = methIDrunTestEncryptCommandDecryptAndEncryptFileSUCCESS;
		boolean returnValue = false;

		HashMap params = null;
		ShellContext context = null;
		
		// Start with Regular Text File.
		this.setUpDefaultTargetFile( TargetFileOption.TEST_FILE_CONTENTS_DES_ENCRYPTED );
		
		// Result should be Encrypted...but has a different Salt so we cannot eval.
		this.setTargetFileResult( TargetFileOption.TEST_FILE_CONTENTS_EMPTY );
		
		// Set this to true, as the files should match!
		this.setTargetFileMatches( false );

		logger.info("CryptoCommandTest De->En Begins...");

		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		decryptCommand.setContext(context);

		logger.debug("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue(returnValue);
		}

		context = null;
		context = new ShellContext();

		params = new HashMap();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);
		
		encryptCommand.setContext( context );

		logger.debug("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertTrue(returnValue);
		}

		logger.info("CryptoCommandTest De->En Ends...");

		return;
		
	}		
	
	@After
	public void checkOutputFileMatch()
	{

		Log logger = methIDCheckOutputFileMatch;

		boolean returnValue = false;
		String targetFileResultString = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( !this.getTargetFileResult().equals( TargetFileOption.TEST_FILE_CONTENTS_EMPTY ))
		{
			
			returnValue = this.checkTargetFile( this.getTargetFileResult().toString() );

			if ( isAssertON() )
			{
				Assert.assertEquals(this.isTargetFileMatches(), returnValue);			
			}
	
			logger.debug(BaseConstants.ENDS);

		}
		
		return;

	}

}
