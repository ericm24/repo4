/**
 */

package com.eric.shell.domain.common.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.context.ShellContext;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.EricTestHelper;


public class MessageCollection_UT extends AbstractBaseTestCase
{
	
	// These are failure tests, rename them...
	private final static Log methIDrunTestNoMessagesPresentFAIL;
	private final static Log methIDrunTestFindNOValuesGreaterThanINFOSUCCESS;
	private final static Log methIDrunTestFindNOValuesGreaterThanWARNINGSUCCESS;
	private final static Log methIDrunTestFindNOValuesGreaterThanSEVERESUCCESS;
	private final static Log methIDrunTestFindNOValuesGreaterThanFATALSUCCESS;
	private final static Log methIDrunTestFindFATALValuesSUCCESS;
	private final static Log methIDrunTestFindALLValuesGreaterThanSUCCESS;
	
	private final static Log methIDrunTestAddMessagesNODupesAllowedFAIL;
	private final static Log methIDrunTestAddMessagesNoTargetCollectionFAIL;
	private final static Log methIDrunTestAddMessagesNoSourceCollectionFAIL;	
	private final static Log methIDrunTestAddMessagesNODupesAllowSUCCESS;	
	private final static Log methIDrunTestAddMessagesWithAllowDupesSUCCESS;
	private final static Log methIDrunTestAddMessagesNODupesMultipleDupesSUCCESS;
	
	private final static Log methIDrunTestGetMessageWithHighestLevelNoMessagesPresentFAIL;
	private final static Log methIDrunTestGetMessageWithLevelFatalSUCCESS;
	
	private final static Log methIDrunTestGetFirstMessageWithHighestLevelEmptyCollectionFAIL;
	private final static Log methIDrunTestGetFirstMessageWithHighestLevelSUCCESS;
	
	private final static Log methIDrunTestGetLastMessageWithHighestLevelEmptyCollectionFAIL;
	private final static Log methIDrunTestGetLastMessageWithLevelEmptyCollectionFAIL;
	
	private final static Log methIDrunTestGetLastMessageWithHighestLevelSUCCESS;
	private final static Log methIDrunTestGetLastMessageWithLevelSUCCESS;
	
	private final static Log methIDcreateMessageCollection3GroupsAllLevels;	
	
	
	static
	{
		methIDrunTestNoMessagesPresentFAIL								= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestNoMessagesPresentFAIL()");		
		methIDrunTestFindALLValuesGreaterThanSUCCESS					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindALLValuesGreaterThanSUCCESS()");
		methIDrunTestFindNOValuesGreaterThanINFOSUCCESS					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindNOValuesGreaterThanINFOSUCCESS()");
		methIDrunTestFindNOValuesGreaterThanWARNINGSUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindNOValuesGreaterThanWARNINGSUCCESS()");		
		methIDrunTestFindNOValuesGreaterThanSEVERESUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindNOValuesGreaterThanSEVERESUCCESS()");
		methIDrunTestFindNOValuesGreaterThanFATALSUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindNOValuesGreaterThanFATALSUCCESS()");		

		methIDrunTestFindFATALValuesSUCCESS								= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestFindFATALValuesSUCCESS()");
		methIDrunTestAddMessagesNoTargetCollectionFAIL					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesNoTargetCollectionFAIL()");
		methIDrunTestAddMessagesNoSourceCollectionFAIL					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesNoSourceCollectionFAIL()");		
		methIDrunTestAddMessagesNODupesAllowedFAIL						= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesNODupesAllowedFAIL()");
		methIDrunTestAddMessagesNODupesAllowSUCCESS						= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesNODupesAllowSUCCESS()");
		methIDrunTestAddMessagesWithAllowDupesSUCCESS					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesWithAllowDupesSUCCESS()");
		methIDrunTestAddMessagesNODupesMultipleDupesSUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestAddMessagesNODupesMultipleDupesSUCCESS()");		
		
		methIDrunTestGetMessageWithHighestLevelNoMessagesPresentFAIL	= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetMessageWithHighestLevelNoMessagesPresentFAIL()");

		methIDrunTestGetFirstMessageWithHighestLevelEmptyCollectionFAIL	= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetFirstMessageWithHighestLevelEmptyCollectionFAIL()");		
		methIDrunTestGetMessageWithLevelFatalSUCCESS					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetMessageWithLevelFatalSUCCESS()");
		methIDrunTestGetFirstMessageWithHighestLevelSUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetFirstMessageWithHighestLevelSUCCESS()");
		
		methIDrunTestGetLastMessageWithHighestLevelEmptyCollectionFAIL	= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetLastMessageWithHighestLevelEmptyCollectionFAIL()");
		methIDrunTestGetLastMessageWithLevelEmptyCollectionFAIL			= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetLastMessageWithLevelEmptyCollectionFAIL()");		
		methIDrunTestGetLastMessageWithHighestLevelSUCCESS				= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetLastMessageWithHighestLevelSUCCESS()");		
		methIDrunTestGetLastMessageWithLevelSUCCESS						= LogFactory.getLog(MessageCollection_UT.class.getName() + ".runTestGetLastMessageWithLevelSUCCESS()");		
		
		methIDcreateMessageCollection3GroupsAllLevels					= LogFactory.getLog(MessageCollection_UT.class.getName() + ".createMessageCollection3GroupsAllLevels()");
	}
	
	@Autowired 
	@Qualifier("messageCollection")
	
	private String s1 = "This is MessageCollection_UT";
	
	
	@Before
	public void setUp()
	{
		return;
	}	
	
	@Test
	public void runTestNoMessagesPresentFAIL()
	{		
		Log logger = methIDrunTestNoMessagesPresentFAIL;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Testing For Presence of Message Level: " + messageLevel.toString());			
			Assert.assertFalse(mc.containsMessagesWithLevelOrAbove( messageLevel ));	
		}
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;	
	}
	
	@Test
	public void runTestFindALLValuesGreaterThanSUCCESS()
	{		
		Log logger 		= methIDrunTestFindALLValuesGreaterThanSUCCESS;	
		boolean returnValue = false;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Message With Level: " + messageLevel.toString());
			context.getMessages().add(new Message(messageLevel, messageLevel.name() + "-Level Message"));	
		}
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Testing For Presence of Message Level: " + messageLevel.toString());
			
			returnValue = false;
			returnValue = mc.containsMessagesWithLevelOrAbove( messageLevel );
			
			if ( returnValue )
			{
				logger.info("Level: " + messageLevel.toString() + " is PRESENT!");				
			}
			Assert.assertTrue( returnValue );
			
		}
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	@Test
	public void runTestFindNOValuesGreaterThanINFOSUCCESS()
	{		
		Log logger = methIDrunTestFindNOValuesGreaterThanINFOSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message1"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message2"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message3"));		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message4"));		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message5"));		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message6"));
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		Assert.assertFalse(mc.containsMessagesWithLevelOrAbove( MessageLevel.INFO ));
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	@Test
	public void runTestFindNOValuesGreaterThanWARNINGSUCCESS()
	{		
		Log logger = methIDrunTestFindNOValuesGreaterThanWARNINGSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message1"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message2"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message3"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message4"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message5"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message6"));
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		Assert.assertFalse(mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING ));
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestFindNOValuesGreaterThanSEVERESUCCESS()
	{		
		Log logger = methIDrunTestFindNOValuesGreaterThanSEVERESUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message1"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message2"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message3"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message4"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message5"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message6"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message7"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message8"));		
		context.getMessages().add(new Message(MessageLevel.WARNING,		"WARNING Message9"));
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		Assert.assertFalse(mc.containsMessagesWithLevelOrAbove( MessageLevel.SEVERE ));
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestFindNOValuesGreaterThanFATALSUCCESS()
	{		
		Log logger = methIDrunTestFindNOValuesGreaterThanFATALSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message1"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message2"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message3"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message4"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message5"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message6"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message7"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message8"));		
		context.getMessages().add(new Message(MessageLevel.WARNING,		"WARNING Message9"));
		context.getMessages().add(new Message(MessageLevel.SEVERE, 		"SEVERE Message10"));		
		context.getMessages().add(new Message(MessageLevel.SEVERE, 		"SEVERE Message11"));		
		context.getMessages().add(new Message(MessageLevel.SEVERE,		"SEVERE Message12"));
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		Assert.assertFalse(mc.containsMessagesWithLevelOrAbove( MessageLevel.FATAL ));
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestFindFATALValuesSUCCESS()
	{		
		Log logger = methIDrunTestFindFATALValuesSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		ShellContext context = new ShellContext();
		
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message1"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message2"));
		context.getMessages().add(new Message(MessageLevel.NONE, 		"None Message3"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message4"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message5"));		
		context.getMessages().add(new Message(MessageLevel.INFO, 		"INFO Message6"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message7"));		
		context.getMessages().add(new Message(MessageLevel.WARNING, 	"WARNING Message8"));		
		context.getMessages().add(new Message(MessageLevel.WARNING,		"WARNING Message9"));
		context.getMessages().add(new Message(MessageLevel.SEVERE, 		"SEVERE Message10"));		
		context.getMessages().add(new Message(MessageLevel.SEVERE, 		"SEVERE Message11"));		
		context.getMessages().add(new Message(MessageLevel.SEVERE,		"SEVERE Message12"));
		context.getMessages().add(new Message(MessageLevel.FATAL, 		"FATAL Message13"));		
		context.getMessages().add(new Message(MessageLevel.FATAL, 		"FATAL Message14"));		
		context.getMessages().add(new Message(MessageLevel.FATAL,		"FATAL Message15"));
		
		MessageCollection mc = context.getMessages();
		
		Assert.assertNotNull( mc );

		Assert.assertTrue(mc.containsMessagesWithLevelOrAbove( MessageLevel.FATAL ));
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}

	
	@Test
	public void runTestAddMessagesNODupesAllowedFAIL()
	{		
		Log logger = methIDrunTestAddMessagesNODupesAllowedFAIL;
		
		logger.debug(BaseConstants.BEGINS);		
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();
		
		Assert.assertNotNull( mc1 );		
		Assert.assertNotNull( mc2 );
		
		Message msg = new Message(MessageLevel.NONE, "Some Message Text.");

		mc1.add( msg );
		
		Assert.assertTrue( mc1.getMessages().size() == 1 );
		
		mc2.add( msg );
		
		Assert.assertTrue( mc2.getMessages().size() == 1 );
		
		mc2 = null;		
		Assert.assertNull( mc2 );
		
		mc1.addMessages(mc2, false);		
		Assert.assertTrue( mc1.getMessages().size() == 1 );		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestAddMessagesNoTargetCollectionFAIL()
	{		
		Log logger = methIDrunTestAddMessagesNoTargetCollectionFAIL;
		
		logger.debug(BaseConstants.BEGINS);		
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();
		
		Assert.assertNotNull( mc1 );		
		Assert.assertNotNull( mc2 );

		Assert.assertTrue( mc1.getMessages().size() == 0 );
		
		Message msg = new Message(MessageLevel.NONE, "Some Message Text.");

		mc2.add( msg );
		
		Assert.assertTrue( mc2.getMessages().size() == 1 );
		
		mc1.addMessages(mc2, false);		
		Assert.assertTrue( mc1.getMessages().size() == 1 );		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestAddMessagesNoSourceCollectionFAIL()
	{		
		Log logger = methIDrunTestAddMessagesNoSourceCollectionFAIL;
		
		logger.debug(BaseConstants.BEGINS);		
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();
		
		Assert.assertNotNull( mc1 );		
		Assert.assertNotNull( mc2 );
	
		Message msg = new Message(MessageLevel.NONE, "Some Message Text.");

		mc1.add( msg );
		
		Assert.assertTrue( mc2.getMessages().size() == 0 );
		
		mc1.addMessages(mc2, false);		
		Assert.assertTrue( mc1.getMessages().size() == 1 );		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestAddMessagesNODupesAllowSUCCESS()
	{		
		Log logger = methIDrunTestAddMessagesNODupesAllowSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();
		
		Assert.assertNotNull( mc1 );		
		Assert.assertNotNull( mc2 );
		
		Message msg = new Message(MessageLevel.NONE, "Some Message Text.");

		mc1.add( msg );
		
		Assert.assertTrue( mc1.getMessages().size() == 1 );
		
		mc2.add( msg );
		
		Assert.assertTrue( mc2.getMessages().size() == 1 );
		
		mc1.addMessages(mc2, false);
		
		Assert.assertTrue( mc1.getMessages().size() == 1 );		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	
	@Test
	public void runTestAddMessagesWithAllowDupesSUCCESS()
	{		
		Log logger = methIDrunTestAddMessagesWithAllowDupesSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();
		
		Assert.assertNotNull( mc1 );		
		Assert.assertNotNull( mc2 );
		
		Message msg = new Message(MessageLevel.NONE, "Some Message Text.");

		mc1.add( msg );
		
		Assert.assertTrue( mc1.getMessages().size() == 1 );
		
		mc2.add( msg );
		
		Assert.assertTrue( mc2.getMessages().size() == 1 );
		
		mc1.addMessages(mc2, true);
		
		Assert.assertTrue( mc1.getMessages().size() == 2 );		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestAddMessagesNODupesMultipleDupesSUCCESS()
	{		
		Log logger = methIDrunTestAddMessagesNODupesMultipleDupesSUCCESS;
		
		logger.debug(BaseConstants.BEGINS);
		
		MessageCollection mc1 = new MessageCollection();		
		MessageCollection mc2 = new MessageCollection();

		if ( this.isAssertON() )
		{		
			Assert.assertNotNull( mc1 );		
			Assert.assertNotNull( mc2 );
		}
		
		Message msg = null; 
		
		int i = 1;

		logger.info("Multiple Duplicates Test...");		
		
		logger.info("All msgs goto #1, evens goto #2, then add a single to 1, combine to 2");		
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			// Add Evens to second collection...
			if ( (i % 2) == 0 )
			{
				logger.debug("Adding To Collection2 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText());
				mc2.add( msg );
			}

			i++;
			msg = null;
		}
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue( mc1.getMessages().size() == 5 );			
			Assert.assertTrue( mc2.getMessages().size() == 2 );			
		}
		
		msg = new Message(MessageLevel.INFO,  MessageLevel.INFO.toString() + " Message Text: " + 99); 
		
		logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText());
		mc1.add( msg );
		
		if ( this.isAssertON() )
		{		
			Assert.assertTrue( mc1.getMessages().size() == 6 );
		}
		
		mc2.addMessages(mc1, false);
		
		if ( this.isAssertON() )
		{		
			Assert.assertTrue( mc2.getMessages().size() == 6 );
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	

	
	@Test
	public void runTestGetMessageWithHighestLevelNoMessagesPresentFAIL()
	{	
		
		Log logger = methIDrunTestGetMessageWithHighestLevelNoMessagesPresentFAIL;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.info("MessageCollection Tests Begins...");
		logger.debug("Initializing.....");
	
		MessageCollection mc = new MessageCollection();
		
		Assert.assertNotNull( mc );

		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Testing For Presence of Message Level: " + messageLevel.toString());
			
			Assert.assertNull( mc.getFirstMessageWithLevel( messageLevel ));	
		}
		
		logger.info("MessageCollection Tests Ends...");
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestGetMessageWithLevelFatalSUCCESS()
	{		
		Log logger 				= methIDrunTestGetMessageWithLevelFatalSUCCESS;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		mc1 = EricTestHelper.createMessageCollection3GroupsAllLevels();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 15 );	
			
			msg = mc1.getFirstMessageWithLevel( MessageLevel.FATAL );
			
			Assert.assertNotNull( msg );
			Assert.assertTrue( msg.getText().contains("GroupB") );
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	@Test
	public void runTestGetFirstMessageWithHighestLevelEmptyCollectionFAIL()
	{		
		Log logger 				= methIDrunTestGetFirstMessageWithHighestLevelEmptyCollectionFAIL;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);

		mc1 = new MessageCollection();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 0 );	
			
			msg = mc1.getFirstMessageWithHighestLevel();

			Assert.assertNull( msg );			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	@Test
	public void runTestGetFirstMessageWithHighestLevelSUCCESS()
	{		
		Log logger 				= methIDrunTestGetFirstMessageWithHighestLevelSUCCESS;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		mc1 = EricTestHelper.createMessageCollection3GroupsAllLevels();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 15 );	
			
			msg = mc1.getFirstMessageWithHighestLevel();

			Assert.assertNotNull( msg );
			
			logger.debug("Message: " + msg.toString() );
			
			Assert.assertTrue( msg.getText().contains("GroupB") );
			Assert.assertTrue( msg.getLevel().equals( MessageLevel.FATAL ));
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}	
	
	
	@Test
	public void runTestGetLastMessageWithHighestLevelEmptyCollectionFAIL()
	{		
		Log logger 				= methIDrunTestGetLastMessageWithHighestLevelEmptyCollectionFAIL;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);

		mc1 = new MessageCollection();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 0 );	
			
			msg = mc1.getLastMessageWithHighestLevel();

			Assert.assertNull( msg );			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}				
	
	@Test
	public void runTestGetLastMessageWithHighestLevelSUCCESS()
	{		
		Log logger 				= methIDrunTestGetLastMessageWithHighestLevelSUCCESS;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);

		mc1 = EricTestHelper.createMessageCollection3GroupsAllLevels();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 15 );	
			
			msg = mc1.getLastMessageWithHighestLevel();

			Assert.assertNotNull( msg );			
			logger.debug("Message: " + msg.toString() );

			Assert.assertTrue( msg.getText().contains("GroupC") );
			Assert.assertTrue( msg.getLevel().equals( MessageLevel.FATAL ));
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}		
	
	@Test
	public void runTestGetLastMessageWithLevelEmptyCollectionFAIL()
	{		
		Log logger 				= methIDrunTestGetLastMessageWithLevelEmptyCollectionFAIL;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);

		mc1 = new MessageCollection();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 0 );	
			
			msg = mc1.getLastMessageWithLevel(MessageLevel.WARNING);

			Assert.assertNull( msg );			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}			
	
	@Test
	public void runTestGetLastMessageWithLevelSUCCESS()
	{		
		Log logger 				= methIDrunTestGetLastMessageWithLevelSUCCESS;
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i 					= 0;
		
		logger.debug(BaseConstants.BEGINS);

		mc1 = EricTestHelper.createMessageCollection3GroupsAllLevels();
		
		if ( this.isAssertON() )
		{
			Assert.assertNotNull( mc1 );
			Assert.assertTrue( mc1.getMessages().size() == 15 );	
		}		
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Testing Messages of Level: " + messageLevel.toString());
			
			msg = mc1.getLastMessageWithLevel( messageLevel );
			
			if ( this.isAssertON() )
			{
				Assert.assertNotNull( msg );
				
				logger.debug("Message: " + msg.toString() );

				Assert.assertTrue( msg.getText().contains("GroupC") );
				Assert.assertTrue( msg.getLevel().equals( messageLevel ));
			}
	
			msg = null;
		}
			
		logger.debug(BaseConstants.ENDS);
		
		return;
	}			
	
	/*
	public static MessageCollection createMessageCollection3GroupsAllLevels()
	{
		
		Log logger 				= methIDcreateMessageCollection3GroupsAllLevels;
		
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i					= 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		mc1 = new MessageCollection();
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupB"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupA"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupC"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( mc1 );
		
	}
	*/
	
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