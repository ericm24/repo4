/**
 */
package com.eric.shell.domain.common.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.AbstractDomainBase;
import com.eric.shell.domain.common.constant.BaseConstants;

/**
 * Collection of messages, with convenience methods for handling various things. 
 * 
 * @author eric 
 * 
 * @see Message
 * @see MessageCollection
 * 
 */
public class MessageCollection extends AbstractDomainBase
{
	private final static Log methIDgetFirstMessageWithLevel;
	private final static Log methIDgetFirstMessageWithHighestLevel;	
	private final static Log methIDgetLastMessageWithHighestLevel;
	private final static Log methIDgetLastMessageWithLevel;
	
	static
	{
		methIDgetFirstMessageWithLevel 			= LogFactory.getLog(MessageCollection.class.getName() + ".getFirstMessageWithLevel()");		
		methIDgetFirstMessageWithHighestLevel 	= LogFactory.getLog(MessageCollection.class.getName() + ".getFirstMessageWithHighestLevel()");
		methIDgetLastMessageWithHighestLevel 	= LogFactory.getLog(MessageCollection.class.getName() + ".getLastMessageWithHighestLevel()");		
		methIDgetLastMessageWithLevel 			= LogFactory.getLog(MessageCollection.class.getName() + ".getLastMessageWithLevel()");
	}	

	private static final long serialVersionUID = 1L;
	private List<Message> messages;

	private final int DEFAULT_SIZE = 25;

	public MessageCollection()
	{
	}

	public MessageCollection(int size)
	{
		if (size > 0)
		{
			messages = new ArrayList<Message>(size);
		}
	}
	/**
	 * Description:  Returns current list of messages in collection
	 * 
	 * 
	 * @see Message
	 */
	public List<Message> getMessages()
	{
		if (messages == null)
		{
			this.init(DEFAULT_SIZE);
		}

		return messages;
	}
	/**
	 * Description:  Adds a new list of messages to this collection.
	 * 
	 * @param List<Message>
	 * 
	 * @see Message
	 */
	public void setMessages(List<Message> messages)
	{
		this.messages = messages;
	}
	/**
	 * Description:  adds a message of type Message to the collection.
	 * 
	 * @param Message
	 * 
	 * @see Message
	 */
	public void add(Message newMessage)
	{

		if (messages == null)
		{
			this.init(DEFAULT_SIZE);
		}

		if (newMessage != null)
		{
			messages.add(newMessage);
		}

		return;
	}
	/**
	 * Description:  Adds source message collection to this message collection.  
	 * 					If duplicates are allowed [ default ] it is a simple blind copy.
	 * 					If duplicates are not allowed, it will only copy messages that do
	 * 						no presently exist, based off of msg-id.
	 * 
	 * @param MessageCollection 
	 * @param boolean
	 * 
	 * @see Message, MessageCollection
	 * 
	 * @return null
	 * 
	 */
	public void addMessages(MessageCollection srcMessageCollection, boolean allowDupes)	
	{
		// If srcCollection is null, simply return.  This is to allow cascading (stacking calls).
		
		boolean alreadyExists = false;
		
		if ( srcMessageCollection != null )
		{				
			Iterator<Message> itr = srcMessageCollection.getMessages().iterator();

			Message newMsg = null; 
			
			while( itr.hasNext() )
			{
				newMsg =  (Message)itr.next();
				
				newMsg = (( !allowDupes  ) && ( this.getMessages().contains( newMsg ) )) ? null : newMsg;
				
				if ( newMsg != null )
				{
					this.messages.add( newMsg );					
				}
				
			}
		}
		
		return;		
	}	
	
	/**
	 * Description:  returns true if messages are present with this level ONLY.
	 * 
	 * @param MessageLevel
	 * @return boolean
	 * 
	 * @see MessageLevel
	 */	
	public boolean containsMessageLevel(MessageLevel thisMessageLevel)
	{
		boolean returnValue = false;
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{

			for (Message message : messages) 
			{
			    
				if ( thisMessageLevel.equals( message.getLevel() ))
				{
					returnValue = true;
					break;
				}
				
			}			
			
		}
		
		return( returnValue );
	}
	/**
	 * Description:  returns true if messages are present with this level or above (in severity).
	 * 
	 * @param MessageLevel
	 * @return boolean
	 * 
	 * @see MessageLevel
	 */
	public boolean containsMessagesWithLevelOrAbove(MessageLevel thisMessageLevel)
	{
		boolean returnValue = false;
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{
			for (Message message : messages) 
			{			    
				if 	( message.getLevel().compareTo( thisMessageLevel ) >= 0 )
				{			
					returnValue = true;
					break;
				}				
			}	
		}
		
		return( returnValue );
	}	
	
	/**
	 * Description:  returns Arraylist of messages that have this message level.
	 * 
	 * @param MessageLevel
	 * @return boolean
	 * 
	 * @see MessagLevel
	 */	
	public ArrayList<Message> getMessagesWithLevel(MessageLevel thisMessageLevel)
	{		
		ArrayList<Message> returnValue = null;
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{

			for (Message message : messages) 
			{
			    
				if ( thisMessageLevel.equals( message.getLevel() ))
				{
				
					if ( returnValue == null )
					{
						returnValue = new ArrayList<Message>();				
					}					
					
					returnValue.add( message );					
				}
				
			}			
			
		}
		
		return( returnValue );
	}
	
	/**
	 * Description:  Return a single message from the collection with the highest-level.  When we have multiple 
	 * 					messages with same level, it returns the one with was created earlier.  
	 * 
	 * @return Message
	 * 
	 * @see MessagLevel
	 */	
	public Message getFirstMessageWithHighestLevel()
	{		
		Log logger 			= methIDgetFirstMessageWithHighestLevel;
		Message returnValue = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{
			// Re-order to Descending, then take the first-one of that level
			Collections.sort( (List<Message>) messages, Message.messageLevelDescendingComparator );

			for (Message message : messages) 
			{
			    logger.debug("Message Be: " + message.toString() );
		    	returnValue = message;
		    	break;
			}			
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}		
	/**
	 * Description:  Return a single message from the collection with the specific-level.  When we have multiple 
	 * 					messages with same level, it returns the one with was created earlier.  
	 * 
	 * @param MessageLevel
	 * 
	 * @return Message
	 * 
	 * @see MessagLevel
	 */	
	public Message getFirstMessageWithLevel(MessageLevel targetMessageLevel)
	{		
		Log logger 			= methIDgetFirstMessageWithLevel;
		Message returnValue = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{
			// Re-order to Descending, then take the first-one of that level
			Collections.sort( (List<Message>) messages, Message.messageLevelDescendingComparator );

			for (Message message : messages) 
			{
			    logger.debug("Message Be: " + message.toString() );
			    
			    if ( message.getLevel().equals( targetMessageLevel ))
			    {
			    	returnValue = message;
			    	break;
			    }
			    
			}			
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}	
	
	/**
	 * Description:  Return a single message from the collection with the highest-level.  When we have multiple 
	 * 					messages with same level, it returns the one that was created last.  
	 * 
	 * @return Message
	 * 
	 * @see MessagLevel
	 */	
	public Message getLastMessageWithHighestLevel()
	{		
		Log logger 			= methIDgetLastMessageWithHighestLevel;
		Message returnValue = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{
			// Re-order to Ascending, then take the last-one of that level
			Collections.sort( (List<Message>) messages, Message.messageLevelAscendingComparator );

			for (Message message : messages) 
			{
			    logger.debug("Message Be: " + message.toString() );
		    	returnValue = message;
			}			
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}			
	
	/**
	 * Description:  Return a single message from the collection with the specific-level.  When we have multiple 
	 * 					messages with same level, it returns the one with was created last.  
	 * 
	 * @param MessageLevel
	 * 
	 * @return Message
	 * 
	 * @see MessagLevel
	 */	
	public Message getLastMessageWithLevel(MessageLevel targetMessageLevel)
	{		
		Log logger 			= methIDgetLastMessageWithLevel;
		Message returnValue = null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if (( messages != null ) && ( !messages.isEmpty() ))
		{
			// Re-order to Ascending, then take the last-one of that level
			Collections.sort( (List<Message>) messages, Message.messageLevelAscendingComparator );

			for (Message message : messages) 
			{
			    logger.debug("Message Be: " + message.toString() );
			    
			    if ( message.getLevel().equals( targetMessageLevel ))
			    {
			    	returnValue = message;
			    }
			    
			}			
			
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}		
	private void init(int size)
	{
		int useSize = 0;

		useSize = ((size == 0) ? DEFAULT_SIZE : size);

		if (messages == null)
		{
			messages = new ArrayList<Message>(useSize);
		}

		return;

	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt( this.getID() );

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass().equals( obj.getClass() ))
		{
			return false;
		}
		
		MessageCollection other = (MessageCollection) obj;
		
		if ( !this.getID().equals( other.getID() ))
		{
			return false;
		}

		return true;
	}	
	@Override
	public String toString()
	{
		String returnValue 		= null;		
		StringBuilder sb 		= new StringBuilder();
		
		sb.append("[ MessageCollection: [ID = |" + this.getID() +"|" );
		
		if ( messages != null )
		{
			for (Message message : messages) 
			{
			    sb.append( message.toString() );
			}
		}
			
		sb.append( " ]" );
		
		returnValue = sb.toString();
		
		return( returnValue );
	}

}
