/**
*/

package com.eric.utils.crypto;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.EricTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})


public class CryptoHelper_UT extends AbstractBaseTestCase
{
	private final static Log methIDrunTestGetDESSaltNULLContextFAIL;
	private final static Log methIDrunTestGetDESSaltNULLPasswordFAIL;
	private final static Log methIDrunTestGetDESSaltSUCCESS;	
	
	private final static Log methIDrunTestGetAESSaltSUCCESS;

	static
	{
		methIDrunTestGetDESSaltNULLContextFAIL		= LogFactory.getLog(CryptoHelper_UT.class.getName() + ".runTestGetDESSaltNULLContextFAIL()");
		methIDrunTestGetDESSaltNULLPasswordFAIL		= LogFactory.getLog(CryptoHelper_UT.class.getName() + ".runTestGetDESSaltNULLPasswordFAIL()");		
		methIDrunTestGetDESSaltSUCCESS				= LogFactory.getLog(CryptoHelper_UT.class.getName() + ".runTestGetDESSaltSUCCESS()");
		methIDrunTestGetAESSaltSUCCESS				= LogFactory.getLog(CryptoHelper_UT.class.getName() + ".runTestGetAESSaltSUCCESS()");		
	}	
	
	//@Autowired	
	
	@Test
	public void runTestGetDESSaltNULLContextFAIL()
	{		
		Log logger 					= methIDrunTestGetDESSaltNULLContextFAIL;
		byte[] returnValue			= null;
		ShellContext context 		= null;
		CryptoHelper cryptoHelper	= null;
		
		logger.debug(BaseConstants.BEGINS);

		if ( this.isAssertON() )
		{
			Assert.assertNull( context );
		}
		
		// Here's the important part!!
		//context = EricTestHelper.loadContextWithBaseParams( context ); 
		
		cryptoHelper = new CryptoHelper( context );
		
		returnValue = cryptoHelper.getSalt();	
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( returnValue );
		}
		
		cryptoHelper = null;		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	

	@Test
	public void runTestGetDESSaltNULLPasswordFAIL() 
	{		
		Log logger 					= methIDrunTestGetDESSaltNULLPasswordFAIL;
		byte[] returnValue			= null;
		ShellContext context 		= null;
		Map<String, Object> params	= null;
		CryptoHelper cryptoHelper	= null;
		
		
		logger.debug(BaseConstants.BEGINS);
		
		context = EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
		}		
		
		params = context.getParams();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( params );
		}		
		
		// Here's the important part!!		
		params.put(CommandConstants.PASSWORD, null); 
		
		cryptoHelper 	= new CryptoHelper( context );		
		
		returnValue 	= cryptoHelper.getSalt();		
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( returnValue );
		
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
								ErrorMessageConstants.PASSWORD_IS_NULL, MessageLevel.SEVERE) == 1);
		}
		
		cryptoHelper = null;
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	@Test
	public void runTestGetDESSaltSUCCESS() 
	{		
		Log logger 							= methIDrunTestGetDESSaltSUCCESS;
		byte[] returnValue					= null;	
		ShellContext context 				= null;		
		
		CryptoHelper     cryptoHelper		= null;		
		
		logger.debug(BaseConstants.BEGINS);	
		
		context = EricTestHelper.loadContextWithBaseParamsPlusSaltDESFileDecrypt( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
			logger.debug( context.toString() );			
		}		
		
		cryptoHelper = new CryptoHelper( context );
		
		returnValue = cryptoHelper.getSalt();		
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( returnValue );
		}		
		
		// Figure out how to convert this to something readable....
		logger.debug("Salt Returned: " + returnValue.toString() );
		
		cryptoHelper 		= null;
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}

	
	@Test
	public void runTestGetAESSaltSUCCESS() 
	{		
		Log logger 							= methIDrunTestGetAESSaltSUCCESS;
		byte[] returnValue					= null;	
		ShellContext context 				= null;		
		
		CryptoHelper     cryptoHelper		= null;		
		
		logger.debug(BaseConstants.BEGINS);	
		
		context = EricTestHelper.loadContextWithBaseParamsAESFileEncrypt( context );
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
			logger.debug( context.toString() );			
		}		
		
		cryptoHelper = new CryptoHelper( context );
		
		returnValue = cryptoHelper.getSalt();		
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( returnValue );
		}		
		
		// Figure out how to convert this to something readable....
		logger.debug("Salt Returned: " + returnValue.toString() );
		
		cryptoHelper 		= null;
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}
	
	
	
	
	

	public void runAllTests()
	{
		return;
	}	
}