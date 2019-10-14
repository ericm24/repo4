/**
 * Concrete Implementation for Command interface that performs Triple-Des encryption 
 * and follows GO4 Command Pattern.  .execute() & .undo() are required behavior methods.
 * 
 * @author Eric 
 * 
 * @see CryptoCommandOption
 * 
 */
package com.eric.shell.command.crypto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.common.validator.Validate;
import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.constant.CryptoConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.AESFileRequiredKey;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoCommandOptionalKey;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.enumeration.DESStringRequiredKey;
import com.eric.shell.domain.common.message.ExMessages;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.validator.CryptoCommandValidator;
import com.eric.utils.crypto.CryptoHelper;



public class EncryptCommand extends AbstractBaseCryptoCommand 
{
	/**
	 * Note: As with the previous example, all kinds of exceptions can be thrown
	 * in main. See the API documentation for each method used.
	 */

	private static final Log methIDExecute; 
	private static final Log methIDEncryptDESFile;
	private static final Log methIDEncryptDESString;	
	private static final Log methIDEncryptAESFile;
	private static final Log methIDEncryptAESString;	
	
	private static final Log methIDExecuteP;
	
	static 
	{
		methIDExecute 			= LogFactory.getLog(EncryptCommand.class.getName() + ".execute()");
		
		methIDEncryptDESFile 	= LogFactory.getLog(EncryptCommand.class.getName() + ".encryptDESFile()");
		methIDEncryptDESString 	= LogFactory.getLog(EncryptCommand.class.getName() + ".encryptDESString()");
		
		methIDEncryptAESFile 	= LogFactory.getLog(EncryptCommand.class.getName() + ".encryptAESFile()");
		methIDEncryptAESString 	= LogFactory.getLog(EncryptCommand.class.getName() + ".encryptAESString()");
		methIDExecuteP 			= LogFactory.getLog("PERF." + EncryptCommand.class.getName() + ".execute()");
	}

	/**
     * Executes encryption algorythim on file specified in the context.
     * @return <CODE>true</CODE> if command successful.  
     * 
     *  If false, check the message collection.
     * 
     * <CODE>false</CODE> otherwise
     */
	@Override
	public boolean execute()
	{
		Log logger 							= methIDExecute;
		Log loggerP 						= methIDExecuteP;
		String errorMsg 					= null;
		CryptoCommandOption commandOption 	= null;		
		boolean keepOnTrucking				= true;
		boolean returnValue 				= false;

		logger.debug( BaseConstants.BEGINS );
		loggerP.info( BaseConstants.BEGINS );		
		
		while ( keepOnTrucking )
		{
			if ( context == null )
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error( errorMsg );
				keepOnTrucking = false;				
				break;			
			}

			commandOption = (CryptoCommandOption) context.getParams().get( CommandConstants.COMMAND_OPTION ); 
		
			if (  commandOption == null )
			{
				errorMsg = ErrorMessageConstants.COMMAND_OPTION_IS_NULL;
				logger.error( errorMsg );
				keepOnTrucking = false;			
			}
			
			if ( commandOption.equals( CryptoCommandOption.DES_FILE_ENCRYPT ))
			{
				returnValue = this.encryptDESFile();
			}
			else if ( commandOption.equals( CryptoCommandOption.DES_STRING_ENCRYPT ))
 			{
				returnValue = this.encryptDESString();				
			}	
			else if ( commandOption.equals( CryptoCommandOption.AES_FILE_ENCRYPT ))
 			{
				returnValue = this.encryptAESFile();				
			}	
			else if ( commandOption.equals( CryptoCommandOption.AES_STRING_ENCRYPT ))
 			{
				returnValue = this.encryptAESString();				
			}	
			else
			{
				errorMsg = String.format(ErrorMessageConstants.INVALID_COMMAND_OPTION, 
						                 commandOption,
						                 EncryptCommand.class.getName());
				
				context.getMessages().add(
						new Message(MessageLevel.SEVERE, errorMsg));
				
				logger.error( errorMsg );
				keepOnTrucking = false;				
			}
				
			// Safety Purposes.
			keepOnTrucking = false;
		}
	
		loggerP.info( BaseConstants.ENDS );
		logger.debug( BaseConstants.ENDS );
		
		return ( returnValue );
	}

	/**
     * UNDO changes by execute method.  Restores context and object-graph 
     * to original form, and in this case returns target string as it existed
     * before, (could be encrypted or decrypted).
     * @return <CODE>true</CODE> if UNDO successful. 
     * <CODE>false</CODE> otherwise
     */
	@Override
	public boolean undo()
	{

		boolean returnValue = false;

		return (returnValue);
	}

	private boolean encryptDESFile()
	{
		Log logger 						= methIDEncryptDESFile;
		boolean returnValue 			= false;
		boolean keepOnTrucking 			= true;
		String errorMsg 				= null;
		byte[] salt 					= null; 
		byte[] input 					= null;		
		int bytesRead					= 0;		
		Cipher cipher					= null;
		CryptoHelper cryptoHelper 		= null;		
		Validate cValidator				= null;
		MessageCollection mc			= null;

		logger.debug(BaseConstants.BEGINS);

		// File to encrypt. It does not have to be a text file!

		while( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);				
				keepOnTrucking = false;
				break;
			}
			

			cValidator 	= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);			
			mc 			= cValidator.validate( context );
			
			// Any Problems?
			if (( mc != null ) && ( mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING )))
			{				
				errorMsg = ErrorMessageConstants.VALIDATION_FAILED;				
				logger.error( errorMsg );			
				
				// Add new messages.
				context.getMessages().addMessages(mc, false);				
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;				
			}

			try
			{
				this.setFileNameIn((String)context.getParams().get(CommandConstants.FILE_IN));			
				this.setPassword((String)context.getParams().get(CommandConstants.PASSWORD));
				
				this.setInFile(new FileInputStream(this.getFileNameIn()));				
				this.setFileNameTemp(this.getFileNameIn() + CryptoConstants.TEMP_SUFFIX);				
				this.setOutFile(new FileOutputStream(this.getFileNameTemp()));
				
				cryptoHelper 	= new CryptoHelper( context );				
				salt 			= cryptoHelper.getSalt();
				
				if ( salt == null )			
				{				
					errorMsg = ErrorMessageConstants.SALT_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;				
				}
				
				cipher = cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				this.getOutFile().write(salt);				

				// Read the file and encrypt its bytes.

				input = new byte[ BaseConstants.BUFFER_SIZE ];				
				
				for (bytesRead = this.getInFile().read(input); (bytesRead != BaseConstants.NO_BYTES_READ); )		
				{
					byte[] output = cipher.update(input, 0, bytesRead);
					if (output != null)
					{
						this.getOutFile().write( output );
					}
					bytesRead = this.getInFile().read(input);					
				}

				byte[] output = cipher.doFinal();
				
				if (output != null)
				{
					this.getOutFile().write(output);					
				}
				
				returnValue = true;				
			}
			
			catch ( IllegalBlockSizeException | BadPaddingException | FileNotFoundException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
			// Do we need this?
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(), ex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;

				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
			finally
			{				
				returnValue = this.close();				
			}
	
			// Safety Purposes
			keepOnTrucking = false;
			break;
			
		}	// End While

		logger.debug(BaseConstants.ENDS);

		return (returnValue);
	}
	
	private boolean encryptDESString()
	{
		Log logger 							= methIDEncryptDESString;		
		boolean returnValue 				= false;
		boolean keepOnTrucking 				= true;
		String errorMsg 					= null;
		byte[] salt 						= null; 
		byte[] input 						= null;
		byte[] output						= null;
		byte[] outputFinal					= null;
		byte finishedValue[] 				= null;		
		ByteArrayOutputStream outputStream	= null;		
		int bytesToConvert					= 0;		
		Cipher cipher						= null;
		CryptoHelper cryptoHelper 			= null;		
		Validate cValidator					= null;
		MessageCollection mc				= null;		
		String inputString					= null;

		logger.debug(BaseConstants.BEGINS);

		// String to encrypt. 

		while( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				
				keepOnTrucking = false;
				break;
			}

			cValidator 	= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			

			mc 			= cValidator.validate( context );
			
			// Any Problems?
			if (( mc != null ) && ( mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING )))
			{				
				errorMsg = ErrorMessageConstants.VALIDATION_FAILED;				
				logger.error( errorMsg );			
				
				// Add new messages.
				context.getMessages().addMessages(mc, false);				
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;				
			}

			try
			{
				this.setPassword((String)context.getParams().get(CommandConstants.PASSWORD));				
				
				cryptoHelper 	= new CryptoHelper( context );				
				salt 			= cryptoHelper.getSalt();
				
				if ( salt == null )			
				{				
					errorMsg = ErrorMessageConstants.SALT_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;				
				}
				
				cipher = cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				inputString = (String)this.getContext().getParams().get( DESStringRequiredKey.STRING_IN.toString() );
				
				if ( inputString == null )
				{
					errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
					logger.error(errorMsg);

					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					keepOnTrucking = false;
					break;					
				}
				
				bytesToConvert 	= inputString.length();				
				input 			= inputString.getBytes();				
				output 			= cipher.update(input, 0, bytesToConvert);
				
				if (output != null)
				{					
					outputFinal = cipher.doFinal();
				}
				
				outputStream = new ByteArrayOutputStream();
				
				outputStream.write( salt.clone() );
				outputStream.write( output );
				outputStream.write( outputFinal );
				
				finishedValue = outputStream.toByteArray( );
				
				if ( finishedValue == null )
				{
					errorMsg = ErrorMessageConstants.FINISHED_VALUE_IS_NULL;
					logger.error(errorMsg);

					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					keepOnTrucking = false;
					break;					
				}
				
				this.getContext().getParams().put(CryptoCommandOptionalKey.STRING_OUT.toString(), finishedValue);
				
				returnValue = true;				
			}
			
			catch ( IllegalBlockSizeException | BadPaddingException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
			// Do we need this?
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(), ex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;

				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
	
			// Safety Purposes
			keepOnTrucking = false;
			break;
			
		}	// End While

		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}
	
	private boolean encryptAESFile()
	{
		Log logger 						= methIDEncryptAESFile;
		boolean returnValue 			= false;
		boolean keepOnTrucking 			= true;
		String errorMsg 				= null;
		byte[] salt 					= null; 
		byte[] input 					= null;		
		int bytesRead					= 0;		
		Cipher cipher					= null;
		CryptoHelper cryptoHelper 		= null;		
		Validate cValidator				= null;
		MessageCollection mc			= null;

		logger.debug(BaseConstants.BEGINS);

		// File to encrypt. It does not have to be a text file!

		while( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);				
				keepOnTrucking = false;
				break;
			}
			
			cValidator 	= new CryptoCommandValidator<AESFileRequiredKey>(AESFileRequiredKey.COMMAND_OPTION);			
			mc 			= cValidator.validate( context );
			
			// Any Problems?
			if (( mc != null ) && ( mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING )))
			{				
				errorMsg = ErrorMessageConstants.VALIDATION_FAILED;				
				logger.error( errorMsg );			
				
				// Add new messages.
				context.getMessages().addMessages(mc, false);				
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;				
			}
			
			try
			{
				this.setFileNameIn((String)context.getParams().get(CommandConstants.FILE_IN));			
				this.setPassword((String)context.getParams().get(CommandConstants.PASSWORD));
				this.setInFile(new FileInputStream(this.getFileNameIn()));				
				this.setFileNameTemp(this.getFileNameIn() + CryptoConstants.TEMP_SUFFIX);				
				this.setOutFile(new FileOutputStream(this.getFileNameTemp()));
				
				cryptoHelper 	= new CryptoHelper( context );				
				salt 			= cryptoHelper.getSalt();
				
				if ( salt == null )			
				{				
					errorMsg = ErrorMessageConstants.SALT_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;				
				}
				
				cipher = cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				this.getOutFile().write(salt);				

				// Read the file and encrypt its bytes.

				input = new byte[ BaseConstants.BUFFER_SIZE ];				
				
				for (bytesRead = this.getInFile().read(input); (bytesRead != BaseConstants.NO_BYTES_READ); )		
				{
					byte[] output = cipher.update(input, 0, bytesRead);
					if (output != null)
					{
						this.getOutFile().write( output );
					}
					bytesRead = this.getInFile().read(input);					
				}

				byte[] output = cipher.doFinal();
				if (output != null)
				{
					this.getOutFile().write(output);					
				}
				
				returnValue = true;				
			}
			
			catch ( IllegalBlockSizeException | BadPaddingException | FileNotFoundException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
			// Do we need this?
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(), ex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;

				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
			finally
			{				
				returnValue = this.close();				
			}
	
			// Safety Purposes
			keepOnTrucking = false;
			break;
			
		}	// End While
		
		
		logger.debug(BaseConstants.ENDS);

		return (returnValue);
	}
	
	private boolean encryptAESString()
	{
		Log logger 							= methIDEncryptAESString;		
		boolean returnValue 				= false;
		boolean keepOnTrucking 				= true;
		String errorMsg 					= null;
		byte[] salt 						= null; 
		byte[] input 						= null;
		byte[] output						= null;
		byte[] outputFinal					= null;
		byte finishedValue[] 				= null;		
		ByteArrayOutputStream outputStream	= null;		
		int bytesToConvert					= 0;		
		Cipher cipher						= null;
		CryptoHelper cryptoHelper 			= null;		
		Validate cValidator					= null;
		MessageCollection mc				= null;		
		String inputString					= null;

		logger.debug(BaseConstants.BEGINS);

		// String to encrypt. 

		/*
		while( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				
				keepOnTrucking = false;
				break;
			}
			
			this.setPassword((String)context.getParams().get(CommandConstants.PASSWORD));				

			cValidator 	= new CryptoCommandValidator<DESStringRequiredKey>(DESStringRequiredKey.COMMAND_OPTION);			

			mc 			= cValidator.validate( context );
			
			// Any Problems?
			if (( mc != null ) && ( mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING )))
			{				
				errorMsg = ErrorMessageConstants.VALIDATION_FAILED;				
				logger.error( errorMsg );			
				
				// Add new messages.
				context.getMessages().addMessages(mc, false);				
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;				
			}

			try
			{
				cryptoHelper 	= new CryptoHelper( context );				
				salt 			= cryptoHelper.getSalt();
				
				if ( salt == null )			
				{				
					errorMsg = ErrorMessageConstants.SALT_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;				
				}
				
				cipher = cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				inputString = (String)this.getContext().getParams().get( DESStringRequiredKey.STRING_IN.toString() );
				
				if ( inputString == null )
				{
					errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
					logger.error(errorMsg);

					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					keepOnTrucking = false;
					break;					
				}
				
				bytesToConvert 	= inputString.length();				
				input 			= inputString.getBytes();				
				output 			= cipher.update(input, 0, bytesToConvert);
				
				if (output != null)
				{					
					outputFinal = cipher.doFinal();
				}
				
				outputStream = new ByteArrayOutputStream();
				
				outputStream.write( salt.clone() );
				outputStream.write( output );
				outputStream.write( outputFinal );
				
				finishedValue = outputStream.toByteArray( );
				
				if ( finishedValue == null )
				{
					errorMsg = ErrorMessageConstants.FINISHED_VALUE_IS_NULL;
					logger.error(errorMsg);

					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					keepOnTrucking = false;
					break;					
				}
				
				this.getContext().getParams().put(CryptoCommandOptionalKey.STRING_OUT.toString(), finishedValue);
				
				returnValue = true;				
			}
			
			catch ( IllegalBlockSizeException | BadPaddingException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
			// Do we need this?
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(), ex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;

				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
	
			// Safety Purposes
			keepOnTrucking = false;
			break;
			
		}	// End While
		*/


		logger.debug(BaseConstants.ENDS);
		
		return( returnValue );
	}	
}
