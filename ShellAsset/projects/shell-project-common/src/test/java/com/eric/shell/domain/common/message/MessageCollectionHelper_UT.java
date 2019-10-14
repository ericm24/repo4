/**
 */
package com.eric.shell.domain.common.message;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.AbstractBaseTestCase;


public class MessageCollectionHelper_UT extends AbstractBaseTestCase
{
	private final static Logger methIDrunTestFindValueWithContextSUCCESS;	
	private final static Logger methIDrunTestValueWithContextNoContextFAIL;
	private final static Logger methIDrunTestFindMultipleValuesWithContextSUCCESS;
	private final static Logger methIDrunTestNoFindValueWithContextSUCCESS;
	
	private final static Logger methIDrunTestFindValueWithNoMessagesAtTargetSearchLevelFAIL;
	private final static Logger methIDrunTestFindValueWithMessageCollectionSUCCESS;	
	private final static Logger methIDrunTestFindValueWithMessageCollectionNoMessageCollectionFAIL;
	
	private final static Logger methIDrunTestFindMultipleValuesWithMessageCollectionSUCCESS;	
	
	
	static
	{
		methIDrunTestFindValueWithContextSUCCESS							= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindValueWithContextSUCCESS()");
		methIDrunTestValueWithContextNoContextFAIL							= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestValueWithContextNoContextFAIL()");		
		methIDrunTestFindMultipleValuesWithContextSUCCESS	 				= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindMultipleValuesWithContextSUCCESS()");		
		methIDrunTestNoFindValueWithContextSUCCESS	 						= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestNoFindValueWithContextSUCCESS()");
				
		methIDrunTestFindValueWithNoMessagesAtTargetSearchLevelFAIL			= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindValueWithNoMessagesAtTargetSearchLevelFAIL()");
		methIDrunTestFindValueWithMessageCollectionSUCCESS	 				= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindValueWithMessageCollectionSUCCESS()");		
		methIDrunTestFindValueWithMessageCollectionNoMessageCollectionFAIL	= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindValueWithMessageCollectionNoMessageCollectionFAIL()");		
		methIDrunTestFindMultipleValuesWithMessageCollectionSUCCESS			= Logger.getLogger(MessageCollectionHelper_UT.class.getName() + ".runTestFindMultipleValuesWithMessageCollectionSUCCESS()");		
	}
	
	@Autowired 
	@Qualifier("messageCollectionHelper")
	private MessageCollectionHelper messageCollectionHelper;
	
	
	private String s1 = "This is MessageCollectionHelper_UT";
	
	
	@Before
	public void setUp()
	{
		return;
	}	
	
	@Test
	public void runTestValueWithContextNoContextFAIL()
	{
		Logger logger = methIDrunTestValueWithContextNoContextFAIL;

		logger.debug(BaseConstants.BEGINS);

		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");

		// Leave This AS NULL!!!

		ShellContext context = null; //new BaseRELContext();
		
		//context.getMessages().add(
		//		new Message(MessageLevel.SEVERE, ErrorMessageConstants.PASSWORD_TOO_SHORT.toString()));
	
		if ( this.isAssertON() )
		{
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.SEVERE) == 0);
		}
			
		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	@Test
	public void runTestFindValueWithContextSUCCESS()
	{		
		Logger logger = methIDrunTestFindValueWithContextSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(
				new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
	
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( context );
			Assert.assertTrue( context.getMessages().getMessages().size() == 1 );

			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.SEVERE) == 1);
		}		
		
		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	
	@Test
	public void runTestFindMultipleValuesWithContextSUCCESS()
	{		
		Logger logger = methIDrunTestFindMultipleValuesWithContextSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();

		for (int i = 0; ( i <= 6 ); i++)
		{
			context.getMessages().add(
					new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		}
		
		if ( this.isAssertON() )
		{		
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.SEVERE) == 7);
		}
		
		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	
	@Test
	public void runTestNoFindValueWithContextUCCESS()
	{		
		Logger logger = methIDrunTestNoFindValueWithContextSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
	
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();

		context.getMessages().add(
				new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue(MessageCollectionHelper.verifyContextHasMessage(context,
					"FooCannotFindThis", MessageLevel.SEVERE) == 0);
		}
		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}


	@Test
	public void runTestFindValueWithNoMessagesAtTargetSearchLevelFAIL()
	{
		Logger logger 				= methIDrunTestFindValueWithNoMessagesAtTargetSearchLevelFAIL;
		MessageCollection mc 		= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
		
		mc = new MessageCollection();
		
		for (int i = 0; ( i <= 6 ); i++)
		{
			mc.add(new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		}
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.FATAL) == 0);
		}

		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestFindValueWithMessageCollectionSUCCESS()
	{
		Logger logger 				= methIDrunTestFindValueWithMessageCollectionSUCCESS;
		MessageCollection mc 		= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");

		mc = new MessageCollection();
		
		mc.add(new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.SEVERE) == 1);
		}

		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	
	@Test
	public void runTestFindValueWithMessageCollectionNoMessageCollectionFAIL()
	{
		Logger logger 				= methIDrunTestFindValueWithMessageCollectionNoMessageCollectionFAIL;
		MessageCollection mc 		= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
		
		// Leave This OUT on purpose
		//mc = new MessageCollection();
		//mc.add(new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( mc );
			
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
					"FooCannotFindThis", MessageLevel.SEVERE) == 0);
		}

		logger.info("MessageCollectionHelper Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
		
	
	
	@Test
	public void runTestFindMultipleValuesWithMessageCollectionSUCCESS()
	{		
		Logger logger 				= methIDrunTestFindMultipleValuesWithMessageCollectionSUCCESS;
		MessageCollection mc		= null;
		
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollectionHelper Tests Begins...");
		logger.debug("Initializing.....");
	
		mc = new MessageCollection();

		for (int i = 0; ( i <= 6 ); i++)
		{
			mc.add(new Message(MessageLevel.SEVERE, BaseTestConstants.BAD_PASSWORD_TOO_SHORT.toString()));
		}
		
		if ( this.isAssertON() )
		{		
			Assert.assertTrue(MessageCollectionHelper.verifyMessageCollectionHasMessage(mc,
					BaseTestConstants.BAD_PASSWORD_TOO_SHORT, MessageLevel.SEVERE) == 7);
		}
		
		logger.info("MessageCollectionHelper Tests Ends...");
		
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