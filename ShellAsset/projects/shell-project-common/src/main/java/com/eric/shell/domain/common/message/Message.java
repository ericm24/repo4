/**
  */
package com.eric.shell.domain.common.message;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;

/**
 * Message 			-Has datetime, text, level, etc. 
 * 
 * @author eric 
 * 
 * @see MessageCollection
 * 
 */
public class Message extends AbstractMessage<MessageLevel> implements Comparable<Message>
{

	private static final long serialVersionUID 			= 1L;
	
	public final static String MESSAGE_DATE_TIME_FORMAT	= "yyyy-MM-dd HH.mm.ss.SSS";	

	private String text;
	private Date dateTimeNow;

	private Message()
	{
		super( MessageLevel.NONE );
		this.setDateTime( new Date() );
	}

	public Message(MessageLevel t)
	{
		super( t );
		this.setDateTime( new Date() );		
	}

	public Message(MessageLevel t, String newText)
	{
		super( t );
		this.setDateTime( new Date() );		
		this.setText(newText);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
	
	public Date getDateTime()
	{
		return dateTimeNow;
	}

	public String getDateTimeAsString()
	{
		String returnValue = null;
		SimpleDateFormat dateFormat = null;
		
	    dateFormat 	= new SimpleDateFormat( MESSAGE_DATE_TIME_FORMAT );
	    returnValue = dateFormat.format( this.getDateTime() ).toString();
		
		return( returnValue );
	}
	
	private void setDateTime(Date newValue)
	{
		this.dateTimeNow = newValue;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt( this.getID() );
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		
		Message other = (Message) obj;

		if ( !this.getID().equals( other.getID() ))
		{
			return false;
		}
		if (text == null)
		{
			if (other.text != null)
			{
				return false;
			}
		}
		else if (!text.equals( other.text ))
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
		
		
		
		sb.append(BaseConstants.FIVE_SPACES +	
				   "[ Message: ID = |" + this.getID() + 
					    "|, level = |" + this.getLevel() + 
			      "|, dateTimeNow = |" + this.getDateTimeAsString() +					    
					     "|, text = |" + this.getText() + "|");
		
		sb.append( " ]" );
		
		returnValue = sb.toString();
		return( returnValue );
	}
	
	
		
	public static final Comparator<Message> messageLevelAscendingComparator = new Comparator<Message>() 
    {
		public int compare(Message message1, Message message2) 
		{
			String level1 = Integer.toString( message1.getLevel().ordinal() ); 
			String level2 = Integer.toString( message2.getLevel().ordinal() );

			//ascending order
			return( level1.compareTo( level2 ) );
		}

    };	
	
	public static final Comparator<Message> messageLevelDescendingComparator = new Comparator<Message>() 
    {
		public int compare(Message message1, Message message2) 
		{
			String level1 = Integer.toString( message1.getLevel().ordinal() ); 
			String level2 = Integer.toString( message2.getLevel().ordinal() );

			//descending order
			return( level2.compareTo( level1 ) );
		}

    };	 
    
	public int compareTo(Message compareMessage) 
	{
		int compareOrdinal = ((Message) compareMessage).getLevel().ordinal() ;		
 
		//ascending order
		return( this.getLevel().ordinal() - compareOrdinal );
 
		//descending order
		//return( compareQuantity - this.getLevel().ordinal());		

	}
    
}
