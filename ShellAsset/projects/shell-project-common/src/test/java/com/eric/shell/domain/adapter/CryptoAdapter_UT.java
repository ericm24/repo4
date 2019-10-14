/**
*/
package com.eric.shell.domain.adapter;

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
import com.eric.shell.domain.common.constant.AdapterMessageConstants;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CryptoConstants;
import com.eric.shell.domain.common.enumeration.CryptoConfigFileRequiredKey;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.EricTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})

public class CryptoAdapter_UT extends AbstractBaseTestCase
{

	private final static Log methIDrunTestToContextNULLArgsFAIL;
	private final static Log methIDrunTestToContextEmptyArgsFAIL;
	private final static Log methIDrunTestToShellContextConfigFileMissingFAIL;
	private final static Log methIDrunTestToShellContextConfigFileCorruptFAIL;
	private final static Log methIDrunTestToShellContextConfigFileMissingKEY1FAIL;
	private final static Log methIDrunTestToShellContextConfigFileMissingStringEnviroVarFAIL;
	private final static Log methIDrunTestToShellContextConfigFileSUCCESS;
	private final static Log methIDrunTestToShellContextSUCCESS;	
	
	static
	{
		methIDrunTestToContextNULLArgsFAIL	 							= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToContextNULLArgsFAIL()");		
		methIDrunTestToContextEmptyArgsFAIL	 							= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToContextEmptyArgsFAIL()");
		methIDrunTestToShellContextConfigFileMissingFAIL				= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextConfigFileMissingFAIL()");
		methIDrunTestToShellContextConfigFileCorruptFAIL				= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextConfigFileCorruptFAIL()");
		methIDrunTestToShellContextConfigFileMissingKEY1FAIL			= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextConfigFileMissingKEY1FAIL()");		
		methIDrunTestToShellContextConfigFileMissingStringEnviroVarFAIL	= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextConfigFileMissingStringEnviroVarFAIL()");		
		methIDrunTestToShellContextConfigFileSUCCESS					= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextConfigFileSUCCESS()");
		methIDrunTestToShellContextSUCCESS								= LogFactory.getLog(CryptoAdapter_UT.class.getName() + ".runTestToShellContextSUCCESS()");
	}

	private String s1 = "This is CryptoAdapter_UT";	
	
	@Before
	public void setUp()
	{
		
	}
	
	
	@Test
	public void runTestToContextNULLArgsFAIL()
	{		
		Log logger 						= methIDrunTestToContextNULLArgsFAIL;
		CryptoAdapter cryptoAdapter 	= new CryptoAdapter(CryptoConstants.CONFIG_FILENAME);
		ShellContext context 			= null;		
		String[] myArgs					= null;

		logger.debug(BaseConstants.BEGINS);
		
		// Here's the IMPORTANT PART!!!
		// myArgs					= EricTestHelper.loadArgsWithBaseParams( myArgs );
		
		if ( isAssertON() )
		{			
			Assert.assertNull( myArgs );
		}		
		
		context = cryptoAdapter.toContext( myArgs );		
		
		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );

			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
								AdapterMessageConstants.ARGS_ARRAY_IS_NULL, MessageLevel.SEVERE) == 1);
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	@Test
	public void runTestToContextEmptyArgsFAIL()
	{		
		Log logger = methIDrunTestToContextEmptyArgsFAIL;
		
		CryptoAdapter cryptoAdapter 	= new CryptoAdapter(CryptoConstants.CONFIG_FILENAME);
		ShellContext context 			= null;		
		
		logger.debug(BaseConstants.BEGINS);
		
		// Here's the IMPORTANT PART!!!		
		String[] myArgs			= { null, null, null };

		if ( isAssertON() )
		{			
			Assert.assertNotNull( myArgs );
		}		
		
		context = cryptoAdapter.toContext( myArgs );		
		
		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );

			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
								AdapterMessageConstants.ARGS_ARRAY_IS_NULL, MessageLevel.SEVERE) == 1);
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestToShellContextConfigFileMissingFAIL()
	{		
		Log logger 								= methIDrunTestToShellContextConfigFileMissingFAIL;
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		

		logger.debug(BaseConstants.BEGINS);

		// Here's the IMPORTANT PART!!
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(BaseTestConstants.TEST_FILE_NAME_MISSING);
		
		myArgs				= EricTestHelper.loadArgsWithBaseParams();		

		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		

		context = cryptoAdapter.toContext( myArgs );		

		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		

		Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							BaseTestConstants.MISSING_FILE_BY_NAME, MessageLevel.SEVERE) == 1);

		logger.debug(BaseConstants.ENDS);
		
		return;
	}	

	@Test
	public void runTestToShellContextConfigFileCorruptFAIL()
	{		
		Log logger 								= methIDrunTestToShellContextConfigFileCorruptFAIL;
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		

		logger.debug(BaseConstants.BEGINS);

		// Here's the IMPORTANT PART!!
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(BaseTestConstants.TEST_CONFIG_FILE_CORRUPT);
		
		myArgs				= EricTestHelper.loadArgsWithBaseParams();		

		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		

		context = cryptoAdapter.toContext( myArgs );		

		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		

		Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							BaseTestConstants.EXCEPTION_PARSE, MessageLevel.SEVERE) == 1);

		logger.debug(BaseConstants.ENDS);
		
		return;
	}
		
	@Test
	public void runTestToShellContextConfigFileMissingKEY1FAIL()
	{		
		Log logger 								= methIDrunTestToShellContextConfigFileMissingKEY1FAIL;
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		

		logger.debug(BaseConstants.BEGINS);

		// Here's the IMPORTANT PART!!
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(BaseTestConstants.TEST_CONFIG_FILE_MISSING_KEY1);
		
		myArgs									= EricTestHelper.loadArgsWithBaseParams();		

		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		

		context = cryptoAdapter.toContext( myArgs );		

		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		

		Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							BaseTestConstants.MISSING_KEY_KEY1, MessageLevel.SEVERE) == 1);

		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestToShellContextConfigFileMissingStringEnviroVarFAIL()
	{		
		Log logger 								= methIDrunTestToShellContextConfigFileMissingStringEnviroVarFAIL;
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		

		logger.debug(BaseConstants.BEGINS);

		// Here's the IMPORTANT PART!!
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(BaseTestConstants.TEST_CONFIG_FILE_MISSING_STRING_ENVIRO_VAR);
		
		myArgs									= EricTestHelper.loadArgsWithBaseParams();		

		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		

		context = cryptoAdapter.toContext( myArgs );		

		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		

		Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(context.getMessages(),
							BaseTestConstants.MISSING_KEY_STRING_ENVIRO_VAR, MessageLevel.SEVERE) == 1);

		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestToShellContextConfigFileSUCCESS()
	{		
		Log logger 								= methIDrunTestToShellContextConfigFileSUCCESS;
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		
	
		logger.debug(BaseConstants.BEGINS);
	
		// Here's the IMPORTANT PART!!
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(BaseTestConstants.TEST_CONFIG_FILE_GOOD);
		
		myArgs									= EricTestHelper.loadArgsWithBaseParams();		
	
		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		
	
		context = cryptoAdapter.toContext( myArgs );		
	
		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		
	
		for (CryptoConfigFileRequiredKey cryptoConfigFileRequiredKey : CryptoConfigFileRequiredKey.values() ) 
		{				
			logger.debug("Checking Context For CryptoConfigFileRequiredKey: " + cryptoConfigFileRequiredKey.toString());			

			value = (String)context.getParams().get(cryptoConfigFileRequiredKey.toString() );				
			
			Assert.assertNotNull( value );
		}

		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	
	@Test
	public void runTestToShellContextSUCCESS()
	{		
		Log logger 								= methIDrunTestToShellContextSUCCESS;
		CryptoAdapter cryptoAdapter 			= new CryptoAdapter(CryptoConstants.CONFIG_FILENAME);
		String[] myArgs							= null;		
		ShellContext context					= null;
		String key								= null;
		Object value							= null;
		Map<String, Object> ctxParams 			= null;		

		logger.debug(BaseConstants.BEGINS);

		myArgs				= EricTestHelper.loadArgsWithBaseParams();		

		if ( isAssertON() )
		{			
			Assert.assertNull( context );
			Assert.assertNotNull( myArgs );
		}		

		context = cryptoAdapter.toContext( myArgs );		

		if ( isAssertON() )
		{			
			Assert.assertNotNull( context );
		}		
		
		logger.debug("Context: " + context.toString() );		

		if ( isAssertON() )
		{			
			Assert.assertTrue( context.getParams().size() == 5);
		}
		
		ctxParams = context.getParams();
		
		Assume.assumeNotNull( ctxParams );		
		
		// TODO: FIXME THIS IS DES!!!
		for (DESFileRequiredKey cryptoCommandRequiredKey : DESFileRequiredKey.values() ) 
		{				
			key = cryptoCommandRequiredKey.toString();
			
			logger.debug("Looking For CryptoCommandRequiredKey: " + key);
			
			value 		= ctxParams.get( key );
			
			logger.debug("Located Key: " + key + ", Value: " + value.toString());
			
			Assume.assumeNotNull( value );			
		}				
		
		value = null;
		value = ctxParams.get( CryptoConfigFileRequiredKey.KEY1.toString() );

		Assert.assertNotNull( value );
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@After
	public void tearDown()
	{		
		return;
	}
	
	public void runAllTests()
	{
		return;
	}
		
}