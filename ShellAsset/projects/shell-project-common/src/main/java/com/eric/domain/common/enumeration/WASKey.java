/**

 */
package com.eric.domain.common.enumeration; 

import com.eric.shell.command.AbstractBaseCommand;
import com.eric.shell.command.Command;

/**
 *  Enum of Command Options for Command Structure. 
 * 
 * @author em32459 
 * 
 * @see Command
 * @see AbstractBaseCommand
 * 
 */
public enum WASKey
{
	SERVER_ROOT				("server.root"),
	ENVIRONMENT 			("com.citigroup.rel.websphere.env"),
	WEBSPHERE_VERSION 		("com.citigroup.rel.websphere.ver"),
	INSTANCE 				("com.citigroup.rel.websphere.instance"),
	CLUSTER 				("com.citigroup.rel.websphere.cluster"),
	CLONE 					("com.citigroup.rel.websphere.clone"),
	OLD_REGION 				("com.citimortgage.websphere.region"),
	ENVIRONMENT_FILE_PATH 	("com/citimortgage/websphere/config/system.properties");
	
	private WASKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
};
