/**
 * Abstract base for adaper
 * 
 * @author eric
 * 
 */
package com.eric.common.adapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.common.message.MessageCollectionHelper;


public abstract class AbstractBaseAdapter
{
	private static final Log methIDprocessMessage;
	
	static 
	{
		
		methIDprocessMessage = LogFactory
			.getLog(AbstractBaseAdapter.class.getName() + ".processMessage()");
		
	}	
	
	
	protected static MessageCollection processMessage(MessageCollection messageCollection, Message newMessage)
	{
		Log logger 				= methIDprocessMessage;		
		
		logger.debug( BaseConstants.BEGINS );		

		messageCollection = MessageCollectionHelper.processMessage(messageCollection, newMessage);
		
		logger.debug( BaseConstants.ENDS );
		
		return( messageCollection );		
	}
	
}
