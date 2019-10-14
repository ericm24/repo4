/**
 */
package com.eric.shell.domain.common.enumeration; 

import com.eric.shell.command.AbstractBaseCommand;
import com.eric.shell.command.Command;


/**
 *  Enum of Command Options for Command Structure. 
 * 
 * @author Eric 
 * 
 * @see Command
 * @see AbstractBaseCommand
 * 
 */

	// CryptoCommandRequiredKeySet.CryptoCommandAESFileRequiredKey


	// CryptoCommandOption.AES.File.Encrypt
	// CryptoCommandOption.DES.String.Decrypt

public enum CryptoKeySet
{
	NONE("NONE"),	
	AES_FILE_ENCRYPT("aes.file.encrypt"),
	AES_STRING_ENCRYPT("aes.string.encrypt" ),  

	DES_STRING("des.string.decrypt"), 
	AES_FILE("des.file.encrypt"), 
	AES_STRING("des.file.decrypt");
	
	private CryptoKeySet(String newValue /*, Object newObj*/ )
	{
		this.value = newValue;
	}
	
	// <E extends Enum<E>>	
	//private Enum<? extends CryptoRequiredKey> subkey;
	
	
	private final String value; 
	
	public String toString() 
	{
		return( value );
	}		
};
