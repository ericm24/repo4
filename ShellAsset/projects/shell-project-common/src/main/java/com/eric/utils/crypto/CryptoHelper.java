/**
*/

package com.eric.utils.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.constant.CryptoConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoCommandOptionalKey;
import com.eric.shell.domain.common.message.ExMessages;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.context.ShellContext;

/**
 * Helper Class for Cryptographic, Ciper Items.   
 * 
 * @author Eric
 * 
 */
public class CryptoHelper
{
	private static final Log methIDgetPassword;
	private static final Log methIDCryptoHelper_ShellContext;
	private static final Log methIDinitDESCrypto;
	private static final Log methIDinitAESCrypto;
	private static final Log methIDgetCipherMode;
	
	static 
	{
		methIDCryptoHelper_ShellContext		= LogFactory.getLog(CryptoHelper.class.getName() + ".CryptoHelper( ShellContext )");
		methIDgetPassword 					= LogFactory.getLog(CryptoHelper.class.getName() + ".getPassword()");		
		methIDinitDESCrypto					= LogFactory.getLog(CryptoHelper.class.getName() + ".initDESCrypto()");		
		methIDinitAESCrypto					= LogFactory.getLog(CryptoHelper.class.getName() + ".initAESCrypto()");		
		methIDgetCipherMode					= LogFactory.getLog(CryptoHelper.class.getName() + ".getCipherMode()");		
	}
	
	private byte[] salt						= null;
	private Cipher cipher					= null;	
	
	/**
	 * 
     * Make Default Private
     * 
     */	
	private CryptoHelper()
	{		
	}

	public CryptoHelper(ShellContext context)
	{		
		CryptoCommandOption commandOption	= null;
		Log logger 							= methIDCryptoHelper_ShellContext;

		logger.debug(BaseConstants.BEGINS);
		
		if ( context != null )
		{
			commandOption = (CryptoCommandOption)context.getParams().get(CommandConstants.COMMAND_OPTION);
		}

		if ( commandOption != null )
		{
			
			if ( commandOption.toString().contains( CryptoConstants.AES.toLowerCase() ))
			{
				this.initAESCrypto( context );
			}
			
			if ( commandOption.toString().contains( CryptoConstants.DES.toLowerCase() ))
			{
				this.initDESCrypto( context );				
			}			
			
		}		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
	}
	
	private boolean initDESCrypto(ShellContext context)
	{		
		Log logger 							= methIDinitDESCrypto;
		String errorMsg						= null;
		String passWord						= null;		
		PBEKeySpec keySpec					= null;		
		SecretKeyFactory keyFactory			= null;
		SecretKey passwordKey				= null;
		PBEParameterSpec parameterSpec		= null;		
		Cipher theCipher					= null;
		int cipherMode						= 0; //-4;
		byte[] theSalt						= null;
		Random random						= null;		
		boolean keepOnTrucking				= true;
		boolean returnValue					= false;		
		
		logger.debug(BaseConstants.BEGINS);
		
		while ( keepOnTrucking )
		{
			if ( context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				keepOnTrucking = false;
				break;				
			}
			
			passWord = getPassword( context );
			
			// If password is null, the error is already logged...
			
			if ( passWord == null )
			{
				keepOnTrucking = false;
				break;				
			}
			
			cipherMode = getCipherMode( context );

			// If cipherMode is -4, the error is already logged...

			if ( cipherMode == BaseConstants.CIPHER_ERROR )
			{
				keepOnTrucking = false;
				break;				
			}
			
			
			try
			{			
				// Use PBEKeySpec to create a key based on a password.
				// The password is passed as a character array
		
				keySpec 	= new PBEKeySpec(passWord.toCharArray());					
		
				keyFactory 	= SecretKeyFactory.getInstance(CryptoConstants.PBEWITHMD5ANDDES);
		
				passwordKey = keyFactory.generateSecret(keySpec);
		
				// PBE = hashing + symmetric encryption. A 64 bit random
				// number (the salt) is added to the password and hashed
				// using a Message Digest Algorithm (MD5 in this example.).
				// The number of times the password is hashed is determined
				// by the iteration count. Adding a random number and
				// hashing multiple times enlarges the key space.
		
				// If Encrypting, then generate the Salt....
		
				if ( cipherMode == Cipher.ENCRYPT_MODE )
				{
					theSalt		 	= new byte[ CryptoConstants.DES_SALT_SIZE ];							
					random			= new Random();
					random.nextBytes( theSalt );						
					this.setSalt( theSalt );
				}
				
				// If Decrypting, then the salt should already be set in the context from the caller....
				if ( cipherMode == Cipher.DECRYPT_MODE )
				{
					theSalt		 	= (byte[])context.getParams().get( CryptoCommandOptionalKey.SALT.toString() );
					this.setSalt( theSalt );					
				}
				
				// Create the parameter spec for this salt and interation count		
				// Check Value of Salt Here...
				
				parameterSpec = new PBEParameterSpec(theSalt, CryptoConstants.DES_ITERATIONS);
		
				// Create the cipher and initialize it for Cryptography...
		
				theCipher = Cipher.getInstance(CryptoConstants.PBEWITHMD5ANDDES);
				
				theCipher.init(cipherMode, passwordKey, parameterSpec);
				
				this.setCipher( theCipher );
				
				returnValue = true;

			}
			catch ( NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | 
					NoSuchAlgorithmException | InvalidKeySpecException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										mEx.getClass().getName(),
										mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
	
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										Exception.class.getName(), ex.getMessage());
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
			
			
			// Safety Purposes
			keepOnTrucking = false;
			break;		
		}
		
		logger.debug(BaseConstants.BEGINS);
		
		return( returnValue );
	}

	private boolean initAESCrypto(ShellContext context)
	{		
		Log logger 							= methIDinitAESCrypto;
		String errorMsg						= null;
		String passWord						= null;		
		// PBEKeySpec keySpec					= null;		
		KeySpec keySpec						= null;		
		
		SecretKeyFactory keyFactory			= null;
		SecretKey passwordKey				= null;
		SecretKey tempKey					= null;
		PBEParameterSpec parameterSpec		= null;		
		Cipher theCipher					= null;
		int cipherMode						= 0; //-4;
		byte[] theSalt						= null;
		Random random						= null;		
		boolean keepOnTrucking				= true;
		boolean returnValue					= false;		
		
		logger.debug(BaseConstants.BEGINS);
		
		// http://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption
		
		/* Derive the key, given password and salt. */
		/*
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		*/
		/* Encrypt the message. */
		/*
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));		
		*/
		
		while ( keepOnTrucking )
		{
			if ( context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				keepOnTrucking = false;
				break;				
			}
			
			passWord = getPassword( context );
			
			// If password is null, the error is already logged...
			
			if ( passWord == null )
			{
				keepOnTrucking = false;
				break;				
			}
			
			cipherMode = getCipherMode( context );

			// If cipherMode is -4, the error is already logged...

			if ( cipherMode == BaseConstants.CIPHER_ERROR )
			{
				keepOnTrucking = false;
				break;				
			}
			
			
			try
			{			
				// Use PBEKeySpec to create a key based on a password.
				// The password is passed as a character array
		
				// OLD..
				//keySpec 	= new PBEKeySpec(passWord.toCharArray());					
		
				// If Encrypting, then generate the Salt....
				
				if ( cipherMode == Cipher.ENCRYPT_MODE )
				{
					theSalt		 	= new byte[ CryptoConstants.AES_SALT_SIZE ];							
					random			= new Random();
					random.nextBytes( theSalt );						
					this.setSalt( theSalt );
				}
				
				// Go For this
				keySpec 	= new PBEKeySpec(passWord.toCharArray(), theSalt, CryptoConstants.AES_ITERATIONS, CryptoConstants.AES_SALT_SIZE);				
				
				keyFactory 	= SecretKeyFactory.getInstance(CryptoConstants.PBKDF2WithHmacSHA1);
		
				tempKey 	= keyFactory.generateSecret( keySpec );
				
				// Use Key.1 Here?
				passwordKey = new SecretKeySpec(tempKey.getEncoded(), CryptoConstants.AES);

				
				// Not Needed like this...
				//passwordKey = keyFactory.generateSecret(keySpec);
		
		
				
				// If Decrypting, then the salt should already be set in the context from the caller....
				if ( cipherMode == Cipher.DECRYPT_MODE )
				{
					theSalt		 	= (byte[])context.getParams().get( CryptoCommandOptionalKey.SALT.toString() );
					this.setSalt( theSalt );					
				}
				
				// Create the parameter spec for this salt and interation count		
				// Check Value of Salt Here...
				
				//parameterSpec = new PBEParameterSpec(theSalt, CryptoConstants.AES_ITERATIONS);
		
				// Create the cipher and initialize it for Cryptography...
		
				theCipher = Cipher.getInstance(CryptoConstants.AES_CBC_PKCS5PADDING);
				// Old
				//theCipher.init(cipherMode, passwordKey, parameterSpec);
				
				
				// Here Dude!  Here is where exception is coming from....
				theCipher.init(cipherMode, passwordKey);
				
				this.setCipher( theCipher );
				
				returnValue = true;

			}
			//catch ( NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException |
			catch ( NoSuchPaddingException | InvalidKeyException |					
					NoSuchAlgorithmException | InvalidKeySpecException mEx )
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										mEx.getClass().getName(),
										mEx.getMessage());
	
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));			
			}
	
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										Exception.class.getName(), ex.getMessage());
				logger.error(errorMsg);
	
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
			}			
			
			
			// Safety Purposes
			keepOnTrucking = false;
			break;		
		}
		
		logger.debug(BaseConstants.BEGINS);
		
		return( returnValue );
	}
	
	private static String getPassword(ShellContext context)
	{
		Log logger 							= methIDgetPassword;
		String passWord						= null;
		String errorMsg						= null;
		boolean keepOnTrucking				= true;
		
		logger.debug(BaseConstants.BEGINS);
	
		while ( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				keepOnTrucking = false;
				break;				
			}
	
			passWord = (String)context.getParams().get(CommandConstants.PASSWORD );		
			
			if (passWord == null)
			{
				errorMsg = ErrorMessageConstants.PASSWORD_IS_NULL;
				logger.error(errorMsg);
				
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));
				keepOnTrucking = false;
				break;
			}
			
			// Safety Purposes
			keepOnTrucking = false;
			break;
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return( passWord );		
	}

	private static int getCipherMode(ShellContext context)
	{
		Log logger 							= methIDgetCipherMode;
		String errorMsg						= null;
		boolean keepOnTrucking				= true;
		int returnValue						= -1;
		CryptoCommandOption commandOption	= CryptoCommandOption.NONE;
		
		logger.debug(BaseConstants.BEGINS);
	
		while ( keepOnTrucking )
		{
			if (context == null)
			{
				errorMsg = ErrorMessageConstants.CONTEXT_IS_NULL;
				logger.error(errorMsg);
				keepOnTrucking = false;
				break;				
			}
	
			commandOption = (CryptoCommandOption)context.getParams().get( CommandConstants.COMMAND_OPTION );	

			switch ( commandOption )
			{
				case  DES_FILE_ENCRYPT:
				case  DES_STRING_ENCRYPT:					
				case  AES_FILE_ENCRYPT:
				case  AES_STRING_ENCRYPT:
						returnValue = Cipher.ENCRYPT_MODE;					
						break;

				case  DES_FILE_DECRYPT:
				case  DES_STRING_DECRYPT:
				case  AES_FILE_DECRYPT:
				case  AES_STRING_DECRYPT:
						returnValue = Cipher.DECRYPT_MODE;					
						break;
				
				case  NONE:
						errorMsg = String.format(ErrorMessageConstants.INVALID_COMMAND_OPTION,
													commandOption.toString());
						logger.error(errorMsg);
					
						context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
						break;
			}
			
			
			// Safety Purposes
			keepOnTrucking = false;
			break;
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return( returnValue );		
	}	
	
	public byte[] getSalt()
	{
		return salt;
	}

	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}

	public Cipher getCipher()
	{
		return cipher;
	}

	public void setCipher(Cipher cipher)
	{
		this.cipher = cipher;
	}
	
	
	
	
}
