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
public enum DESStringRequiredKey 
{
	COMMAND_OPTION(CommandConstants.COMMAND_OPTION),				// CommandOption: File or String, then Encrypt or Decrypt
	STRING_IN(CommandConstants.STRING_IN),							// The Target Input String
	PASSWORD(CommandConstants.PASSWORD);							// The Password.
	
	private DESStringRequiredKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}