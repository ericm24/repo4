/**
 * Concrete Implementation for Command interface that performs Triple-Des decryption 
 * and follows GO4 Command Pattern.  .execute() & .undo() are required behavior methods.
 * 
 * @author Eric
 * 
 */
package com.eric.shell.command.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoCommandOptionalKey;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.enumeration.DESStringRequiredKey;
import com.eric.shell.domain.common.message.ExMessages;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.validator.CryptoCommandValidator;
import com.eric.utils.crypto.CryptoHelper;


public class DecryptCommand extends AbstractBaseCryptoCommand 
{
	/**
	 * Note: As with the previous example, all kinds of exceptions can be thrown
	 * in main. See the API documentation for each method used.
	 */

	
	private static final Log methIDExecute; 
	private static final Log methIDDecryptFile;
	private static final Log methIDDecryptString;	
	private static final Log methIDExecuteP;
	
	static 
	{
		methIDExecute = LogFactory
			.getLog(DecryptCommand.class.getName() + ".execute()");
	
		methIDDecryptFile = LogFactory
			.getLog(DecryptCommand.class.getName() + ".decryptFile()");

		methIDDecryptString = LogFactory
			.getLog(DecryptCommand.class.getName() + ".decryptString()");		
		
		methIDExecuteP = LogFactory
			.getLog("PERF." + DecryptCommand.class.getName() + ".execute()");
	}
	
	/**
     * Executes decryption algorythim on file specified in the context.
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
		boolean returnValue 				= false;
		String errorMsg 					= null;
		CryptoCommandOption commandOption 	= null;		
		boolean keepOnTrucking				= true;

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

			commandOption = (CryptoCommandOption)context.getParams().get( CommandConstants.COMMAND_OPTION ); 
		
			if (  commandOption == null )
			{
				errorMsg = ErrorMessageConstants.COMMAND_OPTION_IS_NULL;
				logger.error( errorMsg );
				keepOnTrucking = false;			
			}
			
			if ( commandOption.equals( CryptoCommandOption.DES_FILE_DECRYPT ))
			{
				returnValue = this.decryptFile();
			}
			else if ( commandOption.equals( CryptoCommandOption.DES_STRING_DECRYPT ))
 			{
				returnValue = this.decryptString();				
			}	
			else
			{
				errorMsg = String.format(ErrorMessageConstants.INVALID_COMMAND_OPTION, 
						                 commandOption,
						                 DecryptCommand.class.getName());
				
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

		
		return (returnValue);
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

		return ( returnValue );
	}

	private boolean decryptFile()
	{
		Log logger 						= methIDDecryptFile;
		boolean returnValue 			= false;
		String errorMsg 				= null;
		boolean keepOnTrucking 			= true;
		byte[] salt 					= null; 
		byte[] input 					= null;		
		//byte[] output 					= null;		
		int bytesRead					= 0;		
		Cipher cipher					= null;
		CryptoHelper cryptoHelper 		= null;		
		Validate cValidator				= null;
		MessageCollection mc			= null;

		logger.debug(BaseConstants.BEGINS);

		while( keepOnTrucking )
		{
		
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				keepOnTrucking = false;
				break;
			}
			
			this.setFileNameIn((String)context.getParams().get(CommandConstants.FILE_IN));
			this.setPassword((String)context.getParams().get(CommandConstants.PASSWORD));				

			cValidator 	= new CryptoCommandValidator<DESFileRequiredKey>(DESFileRequiredKey.COMMAND_OPTION);
			
			mc 			= cValidator.validate( context );
			
			// Any Problems?
			if (( mc != null ) && ( mc.containsMessagesWithLevelOrAbove( MessageLevel.WARNING )))				
			{				
				errorMsg = ErrorMessageConstants.VALIDATION_FAILED;				
				logger.error(errorMsg);			

				// Add new messages.
				context.getMessages().addMessages(mc, false);
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;				
			}
			
			try
			{
				// Read it in.
				this.setInFile(new FileInputStream(this.getFileNameIn()));				
				this.setFileNameTemp(this.getFileNameIn() + CryptoConstants.TEMP_SUFFIX);				
				this.setOutFile(new FileOutputStream(this.getFileNameTemp()));
				
				salt = new byte[ CryptoConstants.DES_SALT_SIZE ];
				
				this.getInFile().read( salt );		
	
				context.getParams().put(CryptoCommandOptionalKey.SALT.toString(), salt);
				
				cryptoHelper 	= new CryptoHelper( context );						
				
				cipher = cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				input = new byte[BaseConstants.BUFFER_SIZE];
				
				for (bytesRead = this.getInFile().read(input); (bytesRead != BaseConstants.NO_BYTES_READ); )					
				{											
					byte[] output = cipher.update(input, 0, bytesRead);
					
					if (output != null)
					{
						this.getOutFile().write(output);							
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
			catch(  IllegalBlockSizeException| BadPaddingException | IOException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
			// Do We need this ?
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(), ex.getMessage());
				logger.error(errorMsg);
				returnValue = false;

				context.getMessages().add(
						new Message(MessageLevel.SEVERE, errorMsg));
			}
			
			finally
			{		
				boolean r2 = false;				
				r2 = this.close();

				returnValue = (( returnValue && r2 ) ? true : false);				
			}

			// Safety Purposes
			keepOnTrucking = false;
			break;
			
		}
		
		logger.debug(BaseConstants.ENDS);

		return (returnValue);
	}

	private boolean decryptString()
	{
		Log logger 							= methIDDecryptString;
		boolean returnValue 				= false;
		boolean keepOnTrucking 				= true;
		String errorMsg 					= null;
		byte[] salt 						= null; 
		byte[] inputBytes					= null;
		byte[] input						= null;
		byte[] output						= null;
		byte[] outputFinal					= null;
		String finishedValue 				= null;		
		ByteArrayInputStream inputStream	= null;		
		ByteArrayOutputStream outputStream	= null;		
		int bytesToConvert					= 0;		
		Cipher cipher						= null;
		CryptoHelper cryptoHelper 			= null;		
		Validate cValidator					= null;
		MessageCollection mc				= null;		
		String inputString					= null;

		logger.debug(BaseConstants.BEGINS);

		// String to Decrypt..... 

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
				inputBytes = (byte[])this.getContext().getParams().get( DESStringRequiredKey.STRING_IN.toString() );
				
				if ( inputBytes == null )
				{
					errorMsg = ErrorMessageConstants.INPUT_BYTESTREAM_IS_NULL;
					logger.error(errorMsg);

					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					keepOnTrucking = false;
					break;					
				}
				
				inputStream 	= new ByteArrayInputStream( inputBytes );
				
				salt 			= new byte[ CryptoConstants.DES_SALT_SIZE ];
				
				bytesToConvert 	= inputStream.read(salt, 0, CryptoConstants.DES_SALT_SIZE);
				
				if ( salt.length == 0 )			
				{				
					errorMsg = ErrorMessageConstants.SALT_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;				
				}
				
				context.getParams().put(CryptoCommandOptionalKey.SALT.toString(), salt);
				
				cryptoHelper 	= new CryptoHelper( context );						
				
				cipher 			= cryptoHelper.getCipher();
				
				if ( cipher == null )
				{
					errorMsg = ErrorMessageConstants.CIPHER_IS_NULL;				
					logger.error( errorMsg );			
					
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
					keepOnTrucking = false;
					break;
				}
				
				bytesToConvert 	= inputBytes.length - CryptoConstants.DES_SALT_SIZE;				
				input 			= new byte[ bytesToConvert ];				
				bytesToConvert 	= inputStream.read(input, 0, bytesToConvert);
				output 			= cipher.update(input, 0, bytesToConvert);
				
				if (output != null)
				{					
					outputFinal = cipher.doFinal();
				}
				
				outputStream = new ByteArrayOutputStream();
				
				outputStream.write( output );
				outputStream.write( outputFinal );
				
				finishedValue = outputStream.toString();
				
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
	
}
