/**
 * @author Eric
 */
package com.eric.shell.domain.common.enumeration;

import com.eric.shell.domain.common.constant.CommandConstants;

/**
 * 
 * Description:  These are the keys that are required to be present in the CONTEXT as they
 * relate to CryptoCommand.
 * 
 * @author Eric
 * 
 * 
 */
public enum AESFileRequiredKey 
{
	COMMAND_OPTION(CommandConstants.COMMAND_OPTION),				// CommandOption: File or String, then Encrypt or Decrypt
	FILE_NAME(CommandConstants.FILE_IN),							// FileName In.
	PASSWORD(CommandConstants.PASSWORD),							// The Password
	KEY1(CommandConstants.KEY1);									// The key.1 from the config file.
	
	private AESFileRequiredKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}