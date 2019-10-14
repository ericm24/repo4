/**
 * 
 * AbstractBase for Validater
 * 
 * @author eric
 * 
 */
package com.eric.common.validator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.AbstractDomainBase;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.common.message.MessageCollectionHelper;
import com.eric.shell.domain.context.ShellContext;


public abstract class AbstractBaseValidator implements Validate
{
	private static final Log methIDvalidate_ShellContext;
	private static final Log methIDvalidate_AbstractDomainBase;	
	private static final Log methIDprocessMessage;
	
	static 
	{
		methIDvalidate_ShellContext = LogFactory
			.getLog(AbstractBaseValidator.class.getName() + ".validate(ShellContext)");

		methIDvalidate_AbstractDomainBase = LogFactory
			.getLog(AbstractBaseValidator.class.getName() + ".validate(AbstractDomainBase)");
		
		methIDprocessMessage = LogFactory
			.getLog(AbstractBaseValidator.class.getName() + ".processMessage()");
		
	}	
	
	
	public MessageCollection validate(AbstractDomainBase base)
	{
		Log logger 						= methIDvalidate_AbstractDomainBase;		
		boolean keepOnTruckin 			= true;
		MessageCollection mc 			= null;
		String errorMsg					= null;
		String value					= null;
		
		logger.debug( BaseConstants.BEGINS );		
		
		while ( keepOnTruckin )
		{			
			if ( base == null )
			{
				errorMsg = ErrorMessageConstants.BASE_IS_NULL;
				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));
				
				keepOnTruckin = false;
				break;				
			}
			
			value = base.getID();
			if (( value == null ) || ( value.isEmpty() ))
			{
				errorMsg = ErrorMessageConstants.INTERNAL_ID_IS_NULL_EMPTY;
				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));				
			}

			// Safety Purposes
			keepOnTruckin = false;
			break;
		}
		
		logger.debug( BaseConstants.ENDS );		
		
		return( mc );
	}
	
	
	
	/**
	 * 
	 * @param BaseRELContex	-Context that has a Hashmap loaded with 
	 * 							values from a TimerJob param properties file.
	 * 
	 * @return MessageCollection with Validation errors.
	 * 
	 * @see MessageCollection, BaseRELContex
	 * 
	 * Description:  Validation performed is:
	 * 	
	 * #1	Verify Context is not null.
	 * 
	 * 
	 * 
	 */	
	public MessageCollection validate(ShellContext context)
	{
		Log logger 						= methIDvalidate_ShellContext;		
		boolean keepOnTruckin 			= true;
		MessageCollection mc 			= null;
		Map<String, Object> params		= null;		
		
		logger.debug( BaseConstants.BEGINS );		
		
		while ( keepOnTruckin )
		{			
			if ( context == null )
			{
				logger.error( ErrorMessageConstants.CONTEXT_IS_NULL );

				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, ErrorMessageConstants.CONTEXT_IS_NULL));
				
				keepOnTruckin = false;
				break;
			}		
			
			params = context.getParams();
			
			if ( params == null )
			{
				logger.debug( ErrorMessageConstants.CONTEXT_PARAMS_ARE_NULL );
				
				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, ErrorMessageConstants.CONTEXT_PARAMS_ARE_NULL));				
			}
			
			// Safety Purposes
			keepOnTruckin = false;
			break;
			
		}
		
		logger.debug( BaseConstants.ENDS );		
		
		return( mc );
		
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
