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
public enum CryptoCommandOptionalKey
{
	SALT(CommandConstants.SALT),							// The salt, a bytearray @SALT_SIZE
	KEY1(CommandConstants.FILE_IN),							// The First Key.
	KEY2(CommandConstants.PASSWORD),						// The Second Key.
	STRING_OUT(CommandConstants.STRING_OUT);				// The Input String, now encrypted....
	
	private CryptoCommandOptionalKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}