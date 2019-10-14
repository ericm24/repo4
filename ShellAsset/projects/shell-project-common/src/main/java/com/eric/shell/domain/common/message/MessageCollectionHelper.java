/**
  */
package com.eric.shell.domain.common.message;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.context.ShellContext;

public class MessageCollectionHelper 
{

	private static Log methIDverifyContextHasMessage;
	private static Log methIDverifyMessageCollectionHasMessage;
	private static Log methIDprocessMessage;

	
	static
	{
		
		methIDverifyContextHasMessage = LogFactory
				.getLog(MessageCollectionHelper.class.getName()
						+ ".verifyContextHasMessage()");

		methIDverifyMessageCollectionHasMessage = LogFactory
				.getLog(MessageCollectionHelper.class.getName()
						+ ".verifyMessageCollectionHasMessage()");
		

		methIDprocessMessage = LogFactory
				.getLog(MessageCollectionHelper.class.getName()
						+ ".processMessage()");
		
	}
	
	private MessageCollectionHelper()
	{		
	}
	
	public static int verifyContextHasMessage(ShellContext targetContext, String targetMessageText, MessageLevel targetMessageLevel )
	{
		Log logger 						= methIDverifyContextHasMessage;
		int returnValue					= 0;
		MessageCollection mc 			= null;
		boolean keepOnTruckin			= true;
		
		logger.debug(BaseConstants.BEGINS);
		
		while ( keepOnTruckin )
		{

			if ( targetContext == null )
			{
				logger.warn("Context IS NULL!!!");
				keepOnTruckin = false;
				break;				
			}			
			
			mc = targetContext.getMessages();
			
			if ( mc == null )
			{
				logger.info("No Messages in context...");
				keepOnTruckin = false;
				break;
			}				
				
			returnValue = verifyMessageCollectionHasMessage( mc, targetMessageText, targetMessageLevel );
			
			// Safety Purposes.
			keepOnTruckin = false;
			break;
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}
	
	public static int verifyMessageCollectionHasMessage(MessageCollection mc, String targetMessageText, MessageLevel targetMessageLevel )
	{
		Log logger 						= methIDverifyMessageCollectionHasMessage;
		int returnValue					= 0;
		ArrayList<Message> msgZ			= null;
		boolean keepOnTruckin			= true;
		
		logger.debug(BaseConstants.BEGINS);
		
		logger.debug("Message Search Criteria Follows.  TargetMessageLevel: " + targetMessageLevel +  " TargetMessageText: |" + targetMessageText +"|");
		
		while ( keepOnTruckin )
		{
			
			if ( mc == null )
			{
				logger.info("No Messages in context...");
				keepOnTruckin = false;
				break;
			}				
				
			logger.debug("Verifying Messages of Level: " + targetMessageLevel);
			
			// OK, the collection has messages of that level....		
			msgZ = mc.getMessagesWithLevel( targetMessageLevel );
	
			String theTxt		= null; 
			
			// There are simply no messages with this level, so just bail...
			if ( msgZ == null )
			{
				keepOnTruckin = false;
				break;				
			}

			logger.debug("Received Messages of Level: " + targetMessageLevel);
			
			for (Message message : msgZ) 
			{				
				theTxt = message.getText();
				
				logger.debug("Received Message: " + theTxt);
				
				if (theTxt.contains( targetMessageText ))
				{
					// Message of Correct Level & Text Found!
					returnValue++;
					logger.info("Received EXPECTED Message: " + theTxt);				
				}		
				
			}
			
			// Safety Purposes.
			keepOnTruckin = false;
			break;
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
		
	}
	
	public static MessageCollection processMessage(MessageCollection messageCollection, Message newMessage)
	{
		Log logger 				= methIDprocessMessage;		
		
		logger.debug( BaseConstants.BEGINS );		

		if ( messageCollection == null )
		{			
			messageCollection = new MessageCollection();			
		}
		
		messageCollection.add( newMessage );
		
		logger.debug( BaseConstants.ENDS );
		
		return( messageCollection );		
	}	
}
