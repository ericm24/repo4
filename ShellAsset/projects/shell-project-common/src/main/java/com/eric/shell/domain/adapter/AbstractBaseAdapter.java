package com.eric.shell.domain.adapter;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.common.message.MessageCollectionHelper;

public abstract class AbstractBaseAdapter implements Adapter
{

	protected List messages = null;

	private static final Log methIDprocessMessage;
	
	static 
	{		
		methIDprocessMessage = LogFactory.getLog(AbstractBaseAdapter.class.getName() + ".processMessage()");
	}		
	
	public List<Message> getMessages()
	{
		return messages;
	}
	
	protected MessageCollection processMessage(MessageCollection messageCollection, Message newMessage)
	{
		Log logger 				= methIDprocessMessage;		
		
		logger.debug( BaseConstants.BEGINS );		

		messageCollection = MessageCollectionHelper.processMessage(messageCollection, newMessage);
		
		logger.debug( BaseConstants.ENDS );
		
		return( messageCollection );		
	}

}
