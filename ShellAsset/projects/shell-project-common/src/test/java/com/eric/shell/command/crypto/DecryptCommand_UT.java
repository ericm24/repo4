/**
 * 
 * @author Eric 
 * 
 * 
 */
package com.eric.shell.command.crypto;

import java.util.ArrayList;
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
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.EricTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration
	({
		"classpath:test-context/shell-project-common-context.xml"
	})

public class DecryptCommand_UT extends AbstractBaseCryptoTestCommand
{
	@Autowired
	@Qualifier("decryptCommand")
	private DecryptCommand decryptCommand;

	private static Log methIDrunTestDecryptCommandNullContextFAIL;
	private static Log methIDrunTestDecryptCommandPasswordTooShortFAIL;	
	private static Log methIDrunTestDecryptCommandIllegalBlockSizeFAIL;
	private static Log methIDrunTestDecryptCommandDESFileMissingFileFAIL;
	private static Log methIDrunTestDecryptCommandInvalidOptionFAIL;
	private static Log methIDrunTestEncryptCommandDESStringMissingStringFAIL;
	
	private static Log methIDrunTestDecryptCommandDESFileSUCCESS;	
	private static Log methIDrunTestDecryptCommandDESStringSUCCESS;	
	private static Log methIDrunTestDecryptCommandAESFileSUCCESS;	

	private static Log methIDSetUpDefaultTargetFile;
	private static Log methIDSetUpBadTargetFile;

	private static Log methIDCheckOutputFileMatch;

	static
	{

		methIDrunTestDecryptCommandNullContextFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runDecryptTestFAILContext()");		
		
		methIDrunTestDecryptCommandPasswordTooShortFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandPasswordTooShortFAIL()");

		methIDrunTestDecryptCommandIllegalBlockSizeFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandIllegalBlockSizeFAIL()");

		methIDrunTestDecryptCommandDESFileMissingFileFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandDESFileMissingFileFAIL()");
		
		methIDrunTestDecryptCommandInvalidOptionFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandInvalidOptionFAIL()");		
		
		methIDrunTestEncryptCommandDESStringMissingStringFAIL = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestEncryptCommandDESStringMissingStringFAIL()");		
		
		methIDrunTestDecryptCommandDESStringSUCCESS = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandDESStringSUCCESS()");

		methIDrunTestDecryptCommandDESFileSUCCESS = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandDESFileSUCCESS()");
		
		methIDrunTestDecryptCommandAESFileSUCCESS = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".runTestDecryptCommandAESFileSUCCESS()");
		
		methIDSetUpDefaultTargetFile = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".setUpDefaultTargetFile()");

		methIDSetUpBadTargetFile = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".setUpBadTargetFile()");

		methIDCheckOutputFileMatch = LogFactory
				.getLog(DecryptCommand_UT.class.getName()
						+ ".checkOutputFileMatch()");

	}

	public void DecryptCommandTest_UT()
	{
		this.DecryptCommandTest_UT(true);
	}

	public void DecryptCommandTest_UT(boolean newValue)
	{
		this.setAssertON(newValue);
	}

	public void runAllTests()
	{

		this.runTestDecryptCommandNullContextFAIL();

		this.setUpDefaultTargetFile();
		this.runTestDecryptCommandDESStringSUCCESS();
		this.checkOutputFileMatch();

		return;
	}

	@Before
	public void setUpDefaultTargetFile()
	{
		Log logger = methIDSetUpDefaultTargetFile;

		logger.debug(BaseConstants.BEGINS);

		this.writeTargetFile( TargetFileOption.TEST_FILE_CONTENTS_DES_ENCRYPTED2 );		
		
		logger.debug(BaseConstants.ENDS);

		return;
	}

	@Test
	public void runTestDecryptCommandNullContextFAIL()
	{
		Log logger = methIDrunTestDecryptCommandNullContextFAIL;
		boolean returnValue = false;

		// Set this to false, as the files should NOT match!
		this.setTargetFileMatches( false );

		// Set this as Empty...no file should have been generated.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);

		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		// Here's the Important Part!!
		decryptCommand.setContext( null );

		returnValue = decryptCommand.execute();

		if (isAssertON())
		{
			Assert.assertFalse( returnValue );
		}

		return;
	}
	
	@Test
	public void runTestDecryptCommandPasswordTooShortFAIL()
	{
		Log logger 						= methIDrunTestDecryptCommandPasswordTooShortFAIL;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;

		// Set this to true, as the files should match!
		this.setTargetFileMatches( true );

		// Set this as Empty to skip it..
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");		

		context = new ShellContext();
		params  = new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION,
				   CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.BAD_PASSWORD_TOO_SHORT);

		context.setParams(params);

		decryptCommand.setContext(context);

		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertFalse( returnValue );
		
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					BaseTestConstants.PASSWORD_IS_TOO_SHORT, MessageLevel.SEVERE) == 1);
		}
		
		logger.info("De-CryptCommand Tests Ends...");

		return;
		
	}	
	
	@Test
	public void runTestDecryptCommandDESFileMissingFileFAIL()
	{
		Log logger 						= methIDrunTestDecryptCommandDESFileMissingFileFAIL;
		boolean returnValue 			= false;		
		Map<String, Object> params 		= null;
		ShellContext context 			= null;

		// Result should be NO File.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		// Set this to true, as the files should NOT match!   
		this.setTargetFileMatches( false );

		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_DECRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_MISSING);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		decryptCommand.setContext(context);

		logger.debug("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertFalse(returnValue);
		}

		Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
				BaseTestConstants.EXCEPTION_FILE_NOT_FOUND, MessageLevel.SEVERE) == 1);
		
		logger.info("De-CryptCommand Tests Ends...");

		return;
	}	
	
	@Test
	public void runTestDecryptCommandIllegalBlockSizeFAIL()
	{
		Log logger 						= methIDrunTestDecryptCommandIllegalBlockSizeFAIL;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
		MessageCollection mc 			= null;
		Message msg 					= null;
		ArrayList<Message> msgZ 		= null;

		// Set this to false, as the files should NOT match!
		this.setTargetFileMatches( false );

		// Set this as Empty to skip it..
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_DES_ENCRYPTED_BLOCKSIZE_ERROR);

		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		this.setUpBadTargetFile();

		context = new ShellContext();
		params = new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_FILE_DECRYPT);

		params.put(CommandConstants.FILE_IN, BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		decryptCommand.setContext(context);

		returnValue = decryptCommand.execute();

		if (isAssertON())
		{
			Assert.assertEquals(false, returnValue);
		}

		Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
								BaseTestConstants.EXCEPTION_ILLEGAL_BLOCK_SIZE, MessageLevel.SEVERE) == 1);

		logger.info("De-CryptCommand Tests Ends...");
		
		return;
	}

	
	@Test
	public void runTestDecryptCommandInvalidOptionFAIL()
	{
		Log logger 						= methIDrunTestDecryptCommandInvalidOptionFAIL;
		boolean returnValue 			= false;		
		Map<String, Object> params 		= null;
		ShellContext context 			= null;

		// Result should be NO File.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);
		
		// Set this to true, as the files should NOT match!   
		this.setTargetFileMatches( false );

		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();

		params = new HashMap<String, Object>();

		// Set this to ENCRYPT!!!!
		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_FILE_ENCRYPT );
		
		params.put(CommandConstants.FILE_IN,
				   BaseTestConstants.TEST_FILE_NAME_MISSING);

		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		decryptCommand.setContext(context);

		logger.debug("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertFalse(returnValue);
		}

		Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
				BaseTestConstants.ERROR_OPTION + CryptoCommandOption.DES_FILE_ENCRYPT.toString(), 
				MessageLevel.SEVERE) == 1);
		
		logger.info("De-CryptCommand Tests Ends...");

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
		
		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		params = new HashMap<String, Object>();
	
		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_STRING_DECRYPT );
		
		// Here's the important part!!!
		params.put(CommandConstants.STRING_IN,
				   null);
	
		params.put(CommandConstants.PASSWORD, 
				   BaseTestConstants.GOOD_PASSWORD);
	
		context.setParams( params );
	
		decryptCommand.setContext(context);
	
		logger.debug("Running Decryption.....");
	
		returnValue = decryptCommand.execute();
	
		if ( isAssertON() )
		{			
			Assert.assertFalse( returnValue );
			Assert.assertNotNull( context );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							  String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
							  DESStringRequiredKey.STRING_IN.toString()),
				              MessageLevel.SEVERE) == 1);		
		}
	
		logger.info("DecryptCommand Tests Ends...");
		
		return;
	}

	@Test
	public void runTestDecryptCommandDESFileSUCCESS()
	{
		Log logger 						= methIDrunTestDecryptCommandDESFileSUCCESS;
		boolean returnValue 			= false;
		HashMap<String, Object> params 	= null;
		ShellContext context 			= null;

		logger.info("Decrypt Command Tests Begins...");

		// TODO: FIX ME!
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_TEXT);

		// Set this to true, as the files should match!		
		this.setTargetFileMatches( true );
		
		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context				= EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		decryptCommand.setContext(context);

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertTrue( returnValue );			
		}

		logger.info("De-CryptCommand Tests Ends...");

		return;
	}
	

	
	//@Test
	public void runTestDecryptCommandAESFileSUCCESS()
	{
		Log logger 						= methIDrunTestDecryptCommandAESFileSUCCESS;
		boolean returnValue 			= false;
		HashMap<String, Object> params 	= null;
		ShellContext context 			= null;

		logger.info("Decrypt Command Tests Begins...");

		// TODO: FIX ME!
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_TEXT);

		// Set this to true, as the files should match!		
		this.setTargetFileMatches( true );
		
		logger.info("Decrypt Command Tests Begins...");
		logger.debug("Initializing.....");

		context = new ShellContext();
		params = new HashMap<String, Object>();

		// TODO: Here dude!!!
		
		params.put(CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.AES_FILE_DECRYPT);

		params.put(CommandConstants.FILE_IN,
					BaseTestConstants.TEST_FILE_NAME_GOOD);

		params.put(CommandConstants.PASSWORD, 
					BaseTestConstants.GOOD_PASSWORD);

		context.setParams(params);

		decryptCommand.setContext(context);

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{
			Assert.assertTrue( returnValue );			
		}

		logger.info("De-CryptCommand Tests Ends...");

		return;
	}
	
	@Test
	public void runTestDecryptCommandDESStringSUCCESS()
	{
		Log logger 						= methIDrunTestDecryptCommandDESStringSUCCESS;
		boolean returnValue 			= false;
		Map<String, Object> params 		= null;
		ShellContext context 			= null;
		String outputValue				= null;
		String returnString				= null;

		// Set this to false.
		this.setTargetFileMatches( false );
		
		// Result should be plain text.
		this.setTargetFileResult(TargetFileOption.TEST_FILE_CONTENTS_EMPTY);	
		
		logger.info("Decrypt Command Tests Begins...");

		logger.debug("Initializing.....");
		
		context = new ShellContext();

		params = new HashMap<String, Object>();

		params.put( CommandConstants.COMMAND_OPTION,
					CryptoCommandOption.DES_STRING_DECRYPT );
		
		// Add String-Key Here...
		params.put( CommandConstants.STRING_IN,
					TargetFileOption.TEST_FILE_CONTENTS_DES_ENCRYPTED2.toBytes());

		params.put( CommandConstants.PASSWORD, 
				    BaseTestConstants.GOOD_PASSWORD);

		context.setParams( params );

		EricTestHelper.dumpContext( context );		
		
		decryptCommand.setContext(context);

		logger.debug("Running Decryption.....");

		returnValue = decryptCommand.execute();

		if ( isAssertON() )
		{			
			Assert.assertTrue( returnValue );
			Assert.assertNotNull( context );			
			Assert.assertTrue( context.getMessages().getMessages().size() == 0 );			
			Assert.assertNotNull( params );			
		}

		outputValue = (String)params.get( CryptoCommandOptionalKey.STRING_OUT.toString() );
		
		if ( isAssertON() )
		{
			Assert.assertNotNull( outputValue );			
			
			logger.info("Decrypted String Size: " + outputValue.length() );
			logger.info("Decrypted String: " + outputValue.toString() );
			
			Assert.assertTrue(outputValue.equals( TargetFileOption.TEST_FILE_CONTENTS_TEXT.toString() ));			
		}
		
		logger.info("De-CryptCommand Tests Ends...");
		
		return;
	}	

	@After
	public void checkOutputFileMatch()
	{

		Log logger = methIDCheckOutputFileMatch;

		boolean returnValue = false;

		logger.debug(BaseConstants.BEGINS);

		if (!this.getTargetFileResult().equals(
				TargetFileOption.TEST_FILE_CONTENTS_EMPTY))
		{
			returnValue = this.checkTargetFile(this.getTargetFileResult()
					.toString());

			if ( isAssertON() )
			{
				Assert.assertEquals(this.isTargetFileMatches(), returnValue);
			}

		}

		logger.debug(BaseConstants.ENDS);

		return;

	}

	private void setUpBadTargetFile()
	{

		Log logger = methIDSetUpBadTargetFile;

		logger.debug(BaseConstants.BEGINS);

		this.writeTargetFile(this.getTargetFileResult().toString());

		logger.debug(BaseConstants.ENDS);

		return;
	}

}
