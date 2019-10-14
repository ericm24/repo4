/**
  */

package com.eric.shell.domain.context;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.EricTestHelper;


public class ShellContext_UT extends AbstractBaseTestCase
{
	
	// These are failure tests, rename them...
	private final static Log methIDrunTestToStringSUCCESS;
	private final static Log methIDrunTestInitMessagesEmptySUCCESS;
	
	private final static Log methIDrunTestGetFirstMessageByLevelNoExistLevelFAILURE;
	private final static Log methIDrunTestGetFirstMessageByLevelSUCCESS;
	
	private final static Log methIDrunTestGetParameterByKeyParameterNoExistFAILURE;
	private final static Log methIDrunTestGetParameterByKeySUCCESS;
	
	private final static Log methIDrunTestInitParamsSUCCESS;
	private final static Log methIDloadContextWithMessages;
	
	
	static
	{
		methIDrunTestToStringSUCCESS									= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestToStringSUCCESS()");
		methIDrunTestInitMessagesEmptySUCCESS							= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestInitMessagesEmptySUCCESS()");		
		methIDrunTestInitParamsSUCCESS									= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestInitParamsSUCCESS()");		

		methIDrunTestGetFirstMessageByLevelNoExistLevelFAILURE			= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestGetFirstMessageByLevelNoExistLevelFAILURE()");
		methIDrunTestGetFirstMessageByLevelSUCCESS						= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestGetFirstMessageByLevelSUCCESS()");

		methIDrunTestGetParameterByKeyParameterNoExistFAILURE			= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestGetParameterByKeyParameterNoExistFAILURE()");
		methIDrunTestGetParameterByKeySUCCESS							= LogFactory.getLog(ShellContext_UT.class.getName() + ".runTestGetParameterByKeySUCCESS()");
		methIDloadContextWithMessages									= LogFactory.getLog(ShellContext_UT.class.getName() + ".loadContextWithMessages()");
	}
	
	private String s1 = "This is ShellContext_UT";
	
	
	@Before
	public void setUp()
	{
		return;
	}	
	
	@Test
	public void runTestInitMessagesEmptySUCCESS()
	{		
		Log logger 							= methIDrunTestInitMessagesEmptySUCCESS;
		MessageCollection mc				= null;
		ShellContext context				= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		// Here's the important part!		
		//context = this.loadContextWithMessages( context );
		
		Assert.assertNotNull( context );

		// This will be null at init time, however when executing the getter, the messages
		// will become initialized.  This is eager-initialized!!!
		
		Assert.assertNotNull( context.getMessages() );		
		Assert.assertTrue( context.getMessages().getMessages().size() == 0 );
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);

		return;
	}	
	
	
	@Test
	public void runTestInitParamsSUCCESS()
	{		
		Log logger 							= methIDrunTestInitParamsSUCCESS;
		MessageCollection mc				= null;
		ShellContext context				= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		// Here's the important part!		
		//context = this.loadContextWithBaseParams( context );		
		
		Assert.assertNotNull( context );

		// Params
		Assert.assertNull( context.getParams() );		
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);

		return;
	}	
		
	@Test
	public void runTestToStringSUCCESS()
	{		
		Log logger 							= methIDrunTestToStringSUCCESS;
		MessageCollection mc				= null;
		ShellContext context				= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		context = EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );
		
		context = this.loadContextWithMessages( context );
		
		Assert.assertNotNull( context );
		
		mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Testing For Presence of Message Level: " + messageLevel.toString());			
			Assert.assertTrue(mc.containsMessagesWithLevelOrAbove( messageLevel ));	
		}
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;	
	}

	@Test
	public void runTestGetFirstMessageByLevelNoExistLevelFAILURE()
	{		
		Log logger 							= methIDrunTestGetFirstMessageByLevelNoExistLevelFAILURE;
		MessageCollection mc				= null;
		ShellContext context				= null;
		Message message						= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();
		
		// Here's the important Part!
		//context = this.loadContextWithMessages( context );
		
		Assert.assertNotNull( context );
		
		mc = context.getMessages();
		
		Assert.assertNotNull( mc );
		
		for (MessageLevel targetMessageLevel : MessageLevel.values() ) 
		{
			logger.info("Attempting To Get First Message With Level: " + targetMessageLevel.toString());		
			
			message = null;
			message = mc.getFirstMessageWithLevel( targetMessageLevel );
			
			Assert.assertNull( message );			
		}
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;	
	}	
	
	@Test
	public void runTestGetFirstMessageByLevelSUCCESS()
	{		
		Log logger 							= methIDrunTestGetFirstMessageByLevelSUCCESS;
		MessageCollection mc				= null;
		ShellContext context				= null;
		Message message						= null;

		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();
	
		context = this.loadContextWithMessages( context );
		
		Assert.assertNotNull( context );
		
		mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		for (MessageLevel targetMessageLevel : MessageLevel.values() ) 
		{
			logger.info("Attempting To Get First Message With Level: " + targetMessageLevel.toString());
			
			message = null;
			message = mc.getFirstMessageWithLevel( targetMessageLevel );
			
			Assert.assertNotNull( message );
			
			logger.info("Got First Message: " + message.toString());		
		}
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;	
	}
	
	@Test
	public void runTestGetParameterByKeyParameterNoExistFAILURE()
	{		
		Log logger 							= methIDrunTestGetParameterByKeyParameterNoExistFAILURE;
		Map<String, Object> params			= null;
		ShellContext context				= null;
		String key							= null;
		String value						= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();

		context = EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );

		Assert.assertNotNull( context );
		
		params = context.getParams();
		
		Assert.assertNotNull( params );

		// Here's the important Part!
		key = CommandConstants.PASSWORD;
		params.remove( key );
		
		value 		= (String) params.get( key );

		Assert.assertNull( value );
		
		Assert.assertTrue( params.size() == 2 ); 
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;	
	}

	@Test
	public void runTestGetParameterByKeySUCCESS()
	{		
		Log logger 							= methIDrunTestGetParameterByKeySUCCESS;
		Map<String, Object> params			= null;
		ShellContext context				= null;
		String key							= null;
		String value						= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("ShellContext Tests Begins...");
		
		logger.debug("Initializing.....");
		
		context = new ShellContext();

		context = EricTestHelper.loadContextWithBaseParamsDESFileDecrypt( context );

		Assert.assertNotNull( context );
		
		params = context.getParams();
		
		Assert.assertNotNull( params );

		// Here's the important Part!
		key = CommandConstants.PASSWORD;
		
		value 		= (String) params.get( key );
		
		Assert.assertNotNull( value );

		logger.info("Located Key: " + key + " Value: " + value );
		
		Assert.assertTrue( params.size() == 3 ); 
		
		Assert.assertNotNull( context );
		
		logger.debug( context.toString() );
		
		logger.info("ShellContext Tests Ends...");
		
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
	private ShellContext loadContextWithMessages(ShellContext context)
	{
		Log logger 					= methIDloadContextWithMessages;

		MessageCollection mc1		= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
	
		
		mc1 = EricTestHelper.createMessageCollection3GroupsAllLevels();		

		context.setMessages( mc1 );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}	
	


}