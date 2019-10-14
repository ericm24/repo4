/**
*/
package com.eric.shell.command.crypto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoCommandOptionalKey;
import com.eric.shell.domain.common.enumeration.DESStringRequiredKey;
import com.eric.shell.domain.common.enumeration.TargetFileOption;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.EricTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})

public class EncryptCommand_UT extends AbstractBaseCryptoTestCommand
{

	@Autowired
	@Qualifier("encryptCommand") 
	private EncryptCommand encryptCommand;

	@Autowired
	@Qualifier("decryptCommand")
	private DecryptCommand decryptCommand;

	private static Log methIDrunTestEncryptCommandNullContextFAIL;	
	private static Log methIDrunTestEncryptCommandPasswordTooShortFAIL;
	private static Log methIDrunTestEncryptCommandInvalidOptionFAIL;
	private static Log methIDrunTestEncryptCommandDESFileMissingFileFAIL;

	private static Log methIDrunTestEncryptCommandDESStringMissingStringFAIL;	
	private static Log methIDrunTestEncryptCommandDESFileSUCCESS;
	private static Log methIDrunTestEncryptCommandDESStringSUCCESS;
	private static Log methIDrunTestEncryptCommandDESEncryptAndDecryptFileSUCCESS;
	private static Log methIDrunTestEncryptCommandDESEncryptAndDecryptStringSUCCESS;
	
	private static Log methIDSetUpDefaultTargetFile;
	private static Log methIDCheckOutputFileMatch;
	
	private static Log methIDrunTestEncryptCommandAESFileSUCCESS;
	
	static
	{
		methIDrunTestEncryptCommandNullContextFAIL = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandNullContextFAIL()");
		
		methIDrunTestEncryptCommandPasswordTooShortFAIL = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandPasswordTooShortFAIL()");
		
		methIDrunTestEncryptCommandDESFileMissingFileFAIL = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESFileMissingFileFAIL()"); 

		methIDrunTestEncryptCommandInvalidOptionFAIL = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandInvalidOptionFAIL()");		

		methIDrunTestEncryptCommandDESStringMissingStringFAIL = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESStringMissingStringFAIL()");		
		
		methIDrunTestEncryptCommandDESFileSUCCESS = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESFileSUCCESS()");		

		methIDrunTestEncryptCommandAESFileSUCCESS = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandAESFileSUCCESS()");
		
		methIDrunTestEncryptCommandDESEncryptAndDecryptFileSUCCESS = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESEncryptAndDecryptFileSUCCESS()");		
		
		methIDrunTestEncryptCommandDESEncryptAndDecryptStringSUCCESS = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESEncryptAndDecryptStringSUCCESS()");		

		methIDrunTestEncryptCommandDESStringSUCCESS = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESStringSUCCESS()");		
		
		methIDSetUpDefaultTargetFile = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".setUpDefaultTargetFile()");
		
		methIDCheckOutputFileMatch = LogFactory
				.getLog(EncryptCommand_UT.class.getName()
						+ ".checkOutputFileMatch()");
	}

	
	public  void EncryptCommandTest_UT()
	{
		this.EncryptCommandTest_UT( true );
	}
	
	public  void EncryptCommandTest_UT(boolean newValue)
	{
		this.setAssertON( newValue );
	}	
	
	public void runAllTests()
	{	
		
		this.runTestEncryptCommandNullContextFAIL();
		
		this.setUpDefaultTargetFile();		
		
		this.runTestEncryptCommandDESFileSUCCESS();		
		this.runTestEncryptCommandDESStringSUCCESS();
		
		this.checkOutputFileMatch();
		
		return;
	}	
	@Before
	public void setUpDefaultTargetFile()
	{

		Log logger = methIDSetUpDefaultTargetFile;

		logger.debug(BaseConstants.BEGINS);

		this.writeTargetFile(BaseTestConstants.TEST_FILE_CONTENTS_TEXT);

		logger.debug(BaseConstants.ENDS);

		return;
	}

	@Test
	public void runTestEncryptCommandNullContextFAIL()
	{
		Log logger 						= methIDrunTestEncryptCommandNullContextFAIL;
		boolean returnValue 			= false;

		Map<String, Object> params 		= null;
		ShellContext context 			= null;
		
		logger.debug( BaseConstants.BEGINS );
		
		logger.debug("Initializing.....");		
		
		// Set this to false, as the files should not match!
		this.setTargetFileMatches( false );
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		
		// Here's the important part!!

		encryptCommand.setContext( null );
		
		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertFalse( returnValue );			
		}

		logger.info("EncryptCommand Tests Ends...");
		
		logger.debug( BaseConstants.ENDS );		

		return;
	}	
	
	@Test
	public void runTestEncryptCommandPasswordTooShortFAIL()
	{
		Log logger = methIDrunTestEncryptCommandPasswordTooShortFAIL;
		boolean returnValue = false;

		HashMap<String, Object> params = null;
		ShellContext context = null;

		// Set this to true, as the files should match!
		this.setTargetFileMatches( true );

		// Set this as Empty to skip it..
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		

		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.BAD_PASSWORD_TOO_SHORT);

		context.setParams(params);

		encryptCommand.setContext( context );

		logger.debug("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
								BaseTestConstants.PASSWORD_IS_TOO_SHORT, MessageLevel.SEVERE) == 1);
			
			Assert.assertFalse( returnValue );
		}

		logger.info("EncryptCommand Tests Ends...");

		return;
		
	}	

	@Test
	public void runTestEncryptCommandInvalidOptionFAIL()
	{
		Log logger 						= methIDrunTestEncryptCommandInvalidOptionFAIL;
		boolean returnValue 			= false;		
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
	
		// Result should be NO File.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		// Set this to true, as the files should NOT match!   
		this.setTargetFileMatches( false );
	
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
	
		context = new ShellContext();
	
		params = new HashMap<String, Object>();
	
		// Set this to DECRYPT!!!!
		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_MISSING);
	
		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);
	
		context.setParams(params);
	
		encryptCommand.setContext(context);
	
		logger.debug("Running Encryption.....");
	
		returnValue = encryptCommand.execute();
	
		if ( isAssertON() )
		{			
			Assert.assertFalse(returnValue);
	
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
				BaseTestConstants.ERROR_OPTION + CryptoCommandOption.DES_FILE_DECRYPT.toString(), 
				MessageLevel.SEVERE) == 1);
		}
		
		logger.info("EncryptCommand Tests Ends...");
	
		return;
		
	}

	@Test
	public void runTestEncryptCommandDESFileMissingFileFAIL()
	{
		Log logger 						= methIDrunTestEncryptCommandDESFileMissingFileFAIL;
		boolean returnValue 			= false;
		
		Map<String, Object> params 		= null;
		ShellContext context 			= null;

		// Result should be NO File.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		// Set this to true, as the files should NOT match!   
		this.setTargetFileMatches( false );

		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_MISSING);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		encryptCommand.setContext(context);

		logger.debug("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertFalse(returnValue);

			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					BaseTestConstants.EXCEPTION_FILE_NOT_FOUND, MessageLevel.SEVERE) == 1);
		}
		
		logger.info("EncryptCommand Tests Ends...");

		return;
	}	
	
	@Test
	public void runTestEncryptCommandDESStringMissingStringFAIL()
	{
		Log logger 						= methIDrunTestEncryptCommandDESStringMissingStringFAIL;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
	
		// Set this to false.
		this.setTargetFileMatches( false );
		
		// Result should be plain text.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);	
		
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		params = new HashMap<String, Object>();
	
		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_STRING_ENCRYPT );
		
		// Here's the important part!!!
		params.put(CommandConstants.STRING_IN,
				   null);
	
		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);
	
		context.setParams( params );
	
		encryptCommand.setContext(context);
	
		logger.debug("Running Encryption.....");
	
		returnValue = encryptCommand.execute();
	
		if ( isAssertON() )
		{			
			Assert.assertFalse( returnValue );
			Assert.assertNotNull( context );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							  String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
							  DESStringRequiredKey.STRING_IN.toString()),
				              MessageLevel.SEVERE) == 1);		
		}
	
		logger.info("EncryptCommand Tests Ends...");
		
		return;
	}
	
	
	@Test
	public void runTestEncryptCommandDESFileSUCCESS()
	{
		Log logger 						= methIDrunTestEncryptCommandDESFileSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;

		// Result should be encrypted file.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_DES_ENCRYPTED);
		
		// Set this to False, as the files should NOT match!   
		// This shows the random salt is working.
		
		this.setTargetFileMatches( false );

		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		encryptCommand.setContext(context);

		logger.debug("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{	
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );
			Assert.assertTrue( returnValue );
		}

		logger.info("EncryptCommand Tests Ends...");

		return;

	}	
	
	@Test
	public void runTestEncryptCommandDESStringSUCCESS()
	{
		Log logger 						= methIDrunTestEncryptCommandDESStringSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
		byte[] outputValue				= null;
	
		// Set this to false.
		this.setTargetFileMatches( false );
		
		// Result should be plain text.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);	
		
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		params = new HashMap<String, Object>();
	
		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_STRING_ENCRYPT );
		
		// Add String-Key Here...
		params.put(CommandConstants.STRING_IN,
				   BaseTestConstants.TEST_FILE_CONTENTS_TEXT);
	
		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);
	
		context.setParams( params );
	
		encryptCommand.setContext(context);
	
		logger.debug("Running Encryption.....");
	
		returnValue = encryptCommand.execute();
	
		if ( isAssertON() )
		{			
			Assert.assertTrue( returnValue );
			Assert.assertNotNull( context );
			
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );
			
			Assert.assertNotNull( params );			
		}
	
		outputValue = (byte[])params.get( CryptoCommandOptionalKey.STRING_OUT.toString() );
		
		if ( isAssertON() )
		{
			Assert.assertNotNull( outputValue );			
			logger.info("Encrypted String Size: " + outputValue.length );			
		}
		
		logger.info("EncryptCommand Tests Ends...");
		
		return;
	}

	@Test
	public void runTestEncryptCommandDESEncryptAndDecryptFileSUCCESS()
	{
		Log logger 						= methIDrunTestEncryptCommandDESEncryptAndDecryptFileSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params		= null;
		ShellContext context 			= null;

		// Result should be plain text.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_TEXT);
		
		// Set this to true, as the files should match!
		this.setTargetFileMatches( true );

		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		encryptCommand.setContext(context);

		logger.info("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );			
			Assert.assertTrue( returnValue );
		}

		context = null;
		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);		
		
		decryptCommand.setContext(context);

		logger.info("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );			
			Assert.assertTrue( returnValue );
		}

		logger.info("EncryptCommand Tests Ends...");

		return;
		
	}	

	
	@Test
	public void runTestEncryptCommandDESEncryptAndDecryptStringSUCCESS()
	{
		Log logger 						= methIDrunTestEncryptCommandDESEncryptAndDecryptStringSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params		= null;
		ShellContext context 			= null;
		byte[] outputValueB				= null;		
		String outputValueS				= null;

		// Result should be plain text.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_TEXT);
		
		// Set this to true, as the files should match!
		this.setTargetFileMatches( true );

		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
				CryptoCommandOption.DES_STRING_ENCRYPT );
	
		// Add String-Key Here...
		params.put(CommandConstants.STRING_IN,
			   BaseTestConstants.TEST_FILE_CONTENTS_TEXT);

		params.put(CommandConstants.PASSWORD, 
			   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		encryptCommand.setContext(context);

		logger.info("Running Encryption.....");

		returnValue = encryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );			
			Assert.assertTrue( returnValue );
		}
		
		outputValueB = (byte[])params.get( CryptoCommandOptionalKey.STRING_OUT.toString() );
		
		if ( isAssertON() )
		{
			Assert.assertNotNull( outputValueB );			
			logger.info("Encrypted String Size: " + outputValueB.length );			
		}
		
		//
		// Part II
		//
		
		context = null;
		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_STRING_DECRYPT );
	
		// Add String-Key Here...
		params.put( CommandConstants.STRING_IN,
					outputValueB);

		params.put( CommandConstants.PASSWORD, 
			    	BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);		
		
		EricTestHelper.dumpContext( context );		
		
		decryptCommand.setContext(context);

		logger.info("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue( returnValue );
			Assert.assertNotNull( context );			
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );			
			Assert.assertNotNull( params );			
		}

		outputValueS = (String)params.get( CryptoCommandOptionalKey.STRING_OUT.toString() );
		
		if ( isAssertON() )
		{
			Assert.assertNotNull( outputValueS );			
			
			logger.info("Decrypted String Size: " + outputValueS.length() );
			logger.info("Decrypted String: " + outputValueS.toString() );
			
			Assert.assertTrue(outputValueS.equals( TargetFileOption.TEST_FILE_CONTENTS_TEXT.toString() ));			
		}

		logger.info("EncryptCommand Tests Ends...");

		return;
		
	}	
	
	
	@Test
	public void runTestEncryptCommandAESFileSUCCESS()
	{
		Log logger 						= methIDrunTestEncryptCommandAESFileSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
	
		// Result should be encrypted file.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_AES_ENCRYPTED);
		
		// Set this to False, as the files should NOT match!   
		// This shows the random salt is working.
		
		this.setTargetFileMatches( false );
	
		logger.info("Encrypt Command Tests Begins...");
		logger.debug("Initializing.....");
	
		context				= EricTestHelper.loadContextWithBaseParamsAESFileEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		encryptCommand.setContext(context);
	
		logger.debug("Running Encryption.....");
	
		returnValue = encryptCommand.execute();
	
		if ( isAssertON() )
		{	
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );
			Assert.assertTrue( returnValue );
		}
	
		logger.info("EncryptCommand Tests Ends...");
	
		return;
	
	}

	@After
	public void checkOutputFileMatch()
	{

		Log logger 			= methIDCheckOutputFileMatch;
		boolean returnValue = false;
		
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
