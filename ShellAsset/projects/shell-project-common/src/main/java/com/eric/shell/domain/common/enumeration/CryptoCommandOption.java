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
public enum CryptoCommandOption
{
	NONE("NONE"),	
	DES_STRING_ENCRYPT("des.string.encrypt"), 
	DES_STRING_DECRYPT("des.string.decrypt"), 
	DES_FILE_ENCRYPT("des.file.encrypt"), 
	DES_FILE_DECRYPT("des.file.decrypt"),
	
	AES_STRING_ENCRYPT("aes.string.encrypt"), 
	AES_STRING_DECRYPT("aes.string.decrypt"), 
	AES_FILE_ENCRYPT("aes.file.encrypt"), 
	AES_FILE_DECRYPT("aes.file.decrypt");	
	
	private CryptoCommandOption(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	// TODO: Handle this at the base-class level....then use a generic to reference a discrete type....
	
	//private final CryptoRequiredKey cryptoCommandRequiredKey; 
	
	public String toString() 
	{
		return value;
	}		
};
