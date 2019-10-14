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
public enum CryptoConfigFileRequiredKey
{
	KEY1("key.1"),
	ENVIRO_VAR("string.enviro.var");	
	
	private CryptoConfigFileRequiredKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
};
