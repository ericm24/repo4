/**
 * @author Eric
 */
package com.eric.shell.domain.common.enumeration;

/**
 * 
 * Description:  These are the keys that are required to be present in the CONTEXT as they
 * relate to ANY job file.
 * 
 * @author EM32459
 * 
 * 
 */
public enum ShellContextRequiredKey
{
	FILE_NAME("timer2.file.name"),					// Fully Quallified Path-FileName
	FILE_ID("timer2.file.id"),						// File Identifier
	FILE_LOCATION("timer2.file.location"),			// Internal or External
	OS_PLATFORM("timer2.file.platform");			// Win XP or AIX
	
	private ShellContextRequiredKey(String newValue)
	{
		this.value = newValue;
	}
	
	private final String value;
	
	public String toString() 
	{
		return value;
	}		
	
}