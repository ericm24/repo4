/**
 */
package com.eric.shell.validator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.AESFileRequiredKey;
import com.eric.shell.domain.common.enumeration.AESStringRequiredKey;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.enumeration.DESStringRequiredKey;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.EricTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
	({
		"classpath:/test-context/shell-project-common-context.xml"
	})

public class CryptoCommandValidator_UT extends AbstractBaseTestCase
{

	private final static Log methIDrunTestValidateNULLContextFAIL;
	private final static Log methIDrunTestValidateContextPasswordTooShortFAIL;
	
	private final static Log methIDrunTestValidateContextDESFileDecryptFileNameMissingFAIL;
	private final static Log methIDrunTestValidateContextDESFileEncryptFileNameMissingFAIL;
	private final static Log methIDrunTestValidateContextDESStringDecryptInputStringMissingFAIL;	
	private final static Log methIDrunTestValidateContextDESStringEncryptInputStringMissingFAIL;
	
	private final static Log methIDrunTestValidateContextAESFileDecryptFileNameMissingFAIL;
	private final static Log methIDrunTestValidateContextAESFileEncryptFileNameMissingFAIL;
	private final static Log methIDrunTestValidateContextAESStringDecryptInputStringMissingFAIL;
	private final static Log methIDrunTestValidateContextAESStringEncryptInputStringMissingFAIL;
	
	private final static Log methIDrunTestValidateContextAESStringEncryptKey1MissingFAIL;
	private final static Log methIDrunTestValidateContextAESFileDecryptKey1MissingFAIL;	
	private final static Log methIDrunTestValidateContextAESStringDecryptKey1MissingFAIL;	
	private final static Log methIDrunTestValidateContextAESFileEncryptKey1MissingFAIL;
	
	private final static Log methIDrunTestValidateContextDESFileDecryptSUCCESS;
	private final static Log methIDrunTestValidateContextDESFileEncryptSUCCESS;
	private final static Log methIDrunTestValidateContextDESStringDecryptSUCCESS;
	private final static Log methIDrunTestValidateContextDESStringEncryptSUCCESS;	
	
	private final static Log methIDrunTestValidateContextAESFileDecryptSUCCESS;
	private final static Log methIDrunTestValidateContextAESFileEncryptSUCCESS;
	private final static Log methIDrunTestValidateContextAESStringDecryptSUCCESS;
	private final static Log methIDrunTestValidateContextAESStringEncryptSUCCESS;
	
	private String xProp;
	
	static
	{
		methIDrunTestValidateNULLContextFAIL								= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateNULLContextFAIL()");
		methIDrunTestValidateContextPasswordTooShortFAIL					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextPasswordTooShortFAIL()");		
		
		methIDrunTestValidateContextDESFileDecryptFileNameMissingFAIL		= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESFileDecryptFileNameMissingFAIL()");		
		methIDrunTestValidateContextDESFileEncryptFileNameMissingFAIL		= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESFileEncryptFileNameMissingFAIL()");		
		methIDrunTestValidateContextDESStringDecryptInputStringMissingFAIL	= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESStringDecryptInputStringMissingFAIL()");		
		methIDrunTestValidateContextDESStringEncryptInputStringMissingFAIL	= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESStringEncryptInputStringMissingFAIL()");

		methIDrunTestValidateContextAESFileDecryptFileNameMissingFAIL		= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileDecryptFileNameMissingFAIL()");		
		methIDrunTestValidateContextAESFileEncryptFileNameMissingFAIL		= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileEncryptFileNameMissingFAIL()");		
		methIDrunTestValidateContextAESStringDecryptInputStringMissingFAIL	= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringDecryptInputStringMissingFAIL()");		
		methIDrunTestValidateContextAESStringEncryptInputStringMissingFAIL	= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringEncryptInputStringMissingFAIL()");
		
		methIDrunTestValidateContextAESStringEncryptKey1MissingFAIL			= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringEncryptKey1MissingFAIL()");		
		methIDrunTestValidateContextAESStringDecryptKey1MissingFAIL			= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringDecryptKey1MissingFAIL()");
		methIDrunTestValidateContextAESFileDecryptKey1MissingFAIL			= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileDecryptKey1MissingFAIL()");		
		methIDrunTestValidateContextAESFileEncryptKey1MissingFAIL			= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileEncryptKey1MissingFAIL()");		
		
		methIDrunTestValidateContextDESFileDecryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESFileDecryptSUCCESS()");		
		methIDrunTestValidateContextDESFileEncryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESFileEncryptSUCCESS()");		
		methIDrunTestValidateContextDESStringDecryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESStringDecryptSUCCESS()");		
		methIDrunTestValidateContextDESStringEncryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextDESStringEncryptSUCCESS()");	
		
		methIDrunTestValidateContextAESFileDecryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileDecryptSUCCESS()");		
		methIDrunTestValidateContextAESFileEncryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESFileEncryptSUCCESS()");		
		methIDrunTestValidateContextAESStringDecryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringDecryptSUCCESS()");		
		methIDrunTestValidateContextAESStringEncryptSUCCESS					= LogFactory.getLog(CryptoCommandValidator_UT.class.getName() + ".runTestValidateContextAESStringEncryptSUCCESS()");		
	}


	private String s1 = "This is JobContextValidator_UT";
	private String targetConfigFile = null; 
	
	@Before
	public void setUp()
	{		
		return;
	}
	
	
	@Test
	public void runTestValidateNULLContextFAIL()
	{		
		Log logger 							= methIDrunTestValidateNULLContextFAIL;		
		MessageCollection mc				= null;
		ShellContext context				= null;
		
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								ErrorMessageConstants.CONTEXT_IS_NULL, MessageLevel.SEVERE) == 1);
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextPasswordTooShortFAIL()
	{		
		Log logger 						= methIDrunTestValidateContextPasswordTooShortFAIL;
		
		MessageCollection mc			= null;
		ShellContext context			= null;
		Map<String, Object> params		= null;
		
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 				= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		Assume.assumeNotNull( context );
		
		params = context.getParams();

		
		Assume.assumeNotNull( params );
	
		// Here's the important part!!
		params.put(CommandConstants.PASSWORD, BaseTestConstants.BAD_PASSWORD_TOO_SHORT);
		
		EricTestHelper.dumpContext( context );
		
		mc = cryptoValidator.validate( context );
		
		Assume.assumeNotNull( mc );
			
			
		Assume.assumeTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
							BaseTestConstants.PASSWORD_IS_TOO_SHORT, MessageLevel.SEVERE) == 1);
	
		
		logger.debug(BaseConstants.ENDS);  
	
		return;
	}		
	
	@Test
	public void runTestValidateContextDESFileDecryptFileNameMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextDESFileDecryptFileNameMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);

		// Here's the Important part!!		
		context				= EricTestHelper.loadContextWithBaseParamsDESStringDecrypt( context );		
		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_FILE_DECRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
										DESFileRequiredKey.FILE_NAME.toString()),
							  MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextDESFileEncryptFileNameMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextDESFileEncryptFileNameMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESStringEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_FILE_ENCRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
										DESFileRequiredKey.FILE_NAME.toString()),
							  MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	
	@Test
	public void runTestValidateContextDESStringDecryptInputStringMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextDESStringDecryptInputStringMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<DESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_STRING_DECRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
										DESStringRequiredKey.STRING_IN.toString()),
							  MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}			

	@Test
	public void runTestValidateContextDESStringEncryptInputStringMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextDESStringEncryptInputStringMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<DESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_STRING_ENCRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
										DESStringRequiredKey.STRING_IN.toString()),
							  MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}			
	
	
	@Test
	public void runTestValidateContextAESFileDecryptFileNameMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileDecryptFileNameMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		
		CryptoCommandValidator<AESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESFileRequiredKey>(AESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);

		// Here's the Important part!!		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringDecrypt( context );		
		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_FILE_DECRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
								AESFileRequiredKey.FILE_NAME.toString()),
								MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextAESFileEncryptFileNameMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileEncryptFileNameMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		
		CryptoCommandValidator<AESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESFileRequiredKey>(AESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_FILE_ENCRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
								AESFileRequiredKey.FILE_NAME.toString()),
								MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	
	@Test
	public void runTestValidateContextAESStringDecryptInputStringMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringDecryptInputStringMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileDecrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_STRING_DECRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
								AESStringRequiredKey.STRING_IN.toString()),
								MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}			

	@Test
	public void runTestValidateContextAESStringEncryptInputStringMissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringEncryptInputStringMissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_STRING_ENCRYPT);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
								AESStringRequiredKey.STRING_IN.toString()),
								MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	
	
	@Test
	public void runTestValidateContextAESStringEncryptKey1MissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringEncryptKey1MissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 												= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().remove(CommandConstants.KEY1);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
												AESStringRequiredKey.KEY1.toString()),
												MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		
	

	@Test
	public void runTestValidateContextAESStringDecryptKey1MissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringDecryptKey1MissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 												= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringDecrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().remove(CommandConstants.KEY1);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
												AESStringRequiredKey.KEY1.toString()),
												MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		

	
	
	@Test
	public void runTestValidateContextAESFileDecryptKey1MissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileDecryptKey1MissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 												= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileDecrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().remove(CommandConstants.KEY1);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
												AESStringRequiredKey.KEY1.toString()),
												MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		
	
	@Test
	public void runTestValidateContextAESFileEncryptKey1MissingFAIL()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileEncryptKey1MissingFAIL;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 												= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileEncrypt( context );		
		
		// Here's the Important part!!		
		context.getParams().remove(CommandConstants.KEY1);		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
								String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
												AESStringRequiredKey.KEY1.toString()),
												MessageLevel.SEVERE) == 1);		
			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		
	
	
	
	@Test
	public void runTestValidateContextDESFileDecryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextDESFileDecryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextDESFileEncryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextDESFileEncryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<DESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESFileEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		
	
	@Test
	public void runTestValidateContextDESStringDecryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextDESStringDecryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<DESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESStringDecrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextDESStringEncryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextDESStringEncryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<DESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsDESStringEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	
	@Test
	public void runTestValidateContextAESFileDecryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileDecryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		CryptoCommandValidator<AESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESFileRequiredKey>(AESFileRequiredKey.COMMAND_OPTION);			
		
		// Here's the IMPORTANT PART		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileDecrypt( context );		
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	@Test
	public void runTestValidateContextAESFileEncryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextAESFileEncryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<AESFileRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESFileRequiredKey>(AESFileRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsAESFileEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}		
	
	@Test
	public void runTestValidateContextAESStringDecryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringDecryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringDecrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	

	@Test
	public void runTestValidateContextAESStringEncryptSUCCESS()
	{		
		Log logger 				= methIDrunTestValidateContextAESStringEncryptSUCCESS;
		
		MessageCollection mc	= null;
		ShellContext context	= null;
		
		CryptoCommandValidator<AESStringRequiredKey> cryptoValidator 	= null;		
		cryptoValidator 						= new CryptoCommandValidator<AESStringRequiredKey>(AESStringRequiredKey.COMMAND_OPTION);			
		
		logger.debug(BaseConstants.BEGINS);
		
		context				= EricTestHelper.loadContextWithBaseParamsAESStringEncrypt( context );
		
		EricTestHelper.dumpContext( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}
		
		mc = cryptoValidator.validate( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );			
			Assert.assertNotNull( context );			
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return;
	}	
	
	
	
	public void runAllTests()
	{
		return;
	}
	
	@After
	public void tearDown()
	{		
		return;
	}

}