/**
 *  Enum of Types
 *  
 * @author eric 
 * 
 * 
 */
package com.eric.domain.common.enumeration; 

import com.eric.shell.command.AbstractBaseCommand;
import com.eric.shell.command.Command;


public enum DefaultType
{
	VER 			("51"),
	INSTANCE 		("1"),
	CLUSTER 		("APPS"),
	CLONE 			("1");
	
	private DefaultType(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
};
