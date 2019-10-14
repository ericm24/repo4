/**
  * 
 * @author eric
 * 
 */
package com.eric.domain.common.enumeration; 

import com.eric.shell.command.AbstractBaseCommand;
import com.eric.shell.command.Command;


public enum EnvironmentType
{
	DEFAULT 		("DEFAULT"),
	LOCAL 			("LOCAL"), // acts as default as well	
	DEV 			("DEV"),
	SIT				("SIT"),
	UAT 			("UAT"),	
	TRN 			("TRN"),
	LOAD			("LOAD"),
	COB				("COB"),
	PROD			("PROD");
	
	private EnvironmentType(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
};
