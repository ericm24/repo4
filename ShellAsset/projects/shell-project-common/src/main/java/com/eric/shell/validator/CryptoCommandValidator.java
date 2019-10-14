/**
 */
package com.eric.shell.validator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.common.validator.AbstractBaseValidator;
import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CryptoConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.context.ShellContext;




public class CryptoCommandValidator<E extends Enum<E>> extends AbstractBaseValidator
{
	
	private static final Log methIDvalidate;
	private static final Log methIDvalidatePassword;
	private static final Log methIDvalidateDESFileKeySet;
	private static final Log methIDvalidateDESStringKeySet;	
	private static final Log methIDvalidateAESFileKeySet;
	private static final Log methIDvalidateAESStringKeySet;
	private static final Log methIDvalidateKeySet;
	
	static 
	{
		methIDvalidate 					= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validate()");	
		methIDvalidatePassword 			= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validatePassword()");	
		methIDvalidateDESFileKeySet		= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validateDESFileKeySet()");		
		methIDvalidateDESStringKeySet	= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validateDESStringKeySet()");
		methIDvalidateAESFileKeySet		= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validateAESFileKeySet()");		
		methIDvalidateAESStringKeySet	= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validateAESStringKeySet()");
		
		methIDvalidateKeySet			= LogFactory.getLog(CryptoCommandValidator.class.getName() + ".validateKeySet()");		
	}	
	
	
	public CryptoCommandValidator(final E e)
	{
		this.key = e;		
	}
	
	private final Enum<?> key;
	
	/**
	 * 
	 * @param ShellContext	-Context that has a Hashmap loaded with 
	 * 							values from a TimerJob param properties file.
	 * 
	 * 
	 * 
	 * 
	 * @return MessageCollection with Validation errors.
	 * 
	 * @see MessageCollection, ShellContext
	 * 
	 * Description:  Validation performed is:
	 * 	
	 * #1	Verify Context is not null.
	 * #2	Pull OS Key  (ID's native OS we're running on). 
	 * #3	Check OS Key against Key in Job file.  
	 * 			If this is the aix config file, we better be running on aix!
	 * 
	 * 
	 * 
	 */
	
	@Override	
	public MessageCollection validate(ShellContext context)
	{		
		Log logger 						= methIDvalidate;		
		MessageCollection mc 			= null;
		Map<String, Object> params		= null;		
		boolean keepOnTruckin			= true;
		
		logger.debug( BaseConstants.BEGINS );		
		
		while ( keepOnTruckin )
		{		
			mc = super.validate( context );		
			// If MessageCollection is init'd, then either the context or the params was null...
			// if it stays null, if safe to assume we can proceed.
			if ( mc != null )
			{
				keepOnTruckin = false;
				break;				
			}
			
			params 	= context.getParams();	
			
			if ( params != null )
			{
				mc = this.validateKeySet( mc, params );			
				mc = this.validatePassword( mc, params );
			}			
			// Safety Purposes
			keepOnTruckin = false;
			break;
		}
		
		logger.debug( BaseConstants.ENDS );		
		
		return ( mc );
	}
	
	private MessageCollection validateKeySet(MessageCollection mc, Map params)
	{		
		Log logger 				= methIDvalidateKeySet;	
		String errorMsg			= null;
		Object value			= null;		
		
		logger.debug( BaseConstants.BEGINS );		
		
		for (Enum<?> key : key.getDeclaringClass().getEnumConstants() )		
		{				
			logger.debug("Validating Presence of CryptoCommandRequiredKey: " + key.toString());			

			value 	= params.get( key.toString() );			
			
			if ( value == null )
			{
				errorMsg 	= String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
												key.toString());
				
				logger.error( errorMsg );
				
				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));				
			}		
			
		}
		
		logger.debug( BaseConstants.ENDS );
		
		return( mc );
	}	
	
	private MessageCollection validatePassword(MessageCollection mc, Map params)
	{
		Log logger 				= methIDvalidatePassword;		

		String key				= null;
		String valueFromConfig	= null;
		String errorMsg			= null;
		boolean keepOnTruckin	= true;
		
		logger.debug( BaseConstants.BEGINS );
		
		while( keepOnTruckin )
		{
			valueFromConfig = (String)params.get( DESFileRequiredKey.PASSWORD.toString() );

			// Should have been validated earlier, but double-check...
			if ( valueFromConfig == null )
			{
				errorMsg 	= String.format(ErrorMessageConstants.CRYPTO_COMMAND_MISSING_REQUIRED_KEY,
											DESFileRequiredKey.PASSWORD.toString());
				logger.error( errorMsg );

				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTruckin = false;
				break;				
			}
		
			// Check password meets minimum length.
			if ( valueFromConfig.length() < CryptoConstants.MIN_PASSWORD_LEN )
			{
				errorMsg 	= String.format(ErrorMessageConstants.PASSWORD_IS_TOO_SHORT,
											CryptoConstants.MIN_PASSWORD_LEN);
				
				logger.error( errorMsg );

				mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));
				
				// TODO: Decide to keep going and further validate, or drop out here...I say keep going, that
				// way the caller knows what is all wrong.
				//keepOnTruckin = false;
				//break;			
			}
			
			// Here Dude!!  
			// TODO: Add checks for contains an upper, contains a lower, contains a number, contains a special.
			// Use REGEX for these...
			
			
			// Safety Purposes.
			keepOnTruckin = false;
			break;		
		}
		
		logger.debug( BaseConstants.ENDS );
		
		return( mc );
	}	

}
