/**
*/
package com.eric.utils.string;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;

/**
 * Helper Class for Strings, String things.  
 * 
 * @author Eric
 * 
 */
public class StringHelper
{
	
	private static final Log methIDisNullOrEmpty_String;
	private static final Log methIDisNullOrEmpty_ObjectArray;

	static
	{
		methIDisNullOrEmpty_String 			= LogFactory.getLog(StringHelper.class.getName() + ".isNullOrEmpty( String )");
		methIDisNullOrEmpty_ObjectArray 	= LogFactory.getLog(StringHelper.class.getName() + ".isNullOrEmpty( Object[] )");		
	}
	
	/**
	 * 
	 * Convenience method that checks a String to determine if it is null or empty. Stolen from .Net.
	 *
	 * @param StringInput
	 *  
	 * @return java.lang.String
	 * 
	 */	
	public static boolean isNullOrEmpty(String theString)
	{
		Log logger 				= methIDisNullOrEmpty_String;
		boolean returnValue 	= true;

		logger.debug( BaseConstants.BEGINS );

		if ( theString != null )
		{
			if ( theString.isEmpty() )
			{
				returnValue = true;
			}
			else
			{
				returnValue = false;
			}
		}
		
		logger.debug( BaseConstants.ENDS );
		
		return( returnValue );
	}	

	/**
	 * 
	 * Convenience method that checks an Array to determine if it is null or empty. Stolen from .Net.
	 *
	 * @param StringArray
	 *  
	 * @return java.lang.String
	 * 
	 */	
	public static boolean isNullOrEmpty(String[] theArray)
	{
		Log logger 				= methIDisNullOrEmpty_ObjectArray;
		boolean returnValue 	= true;

		logger.debug( BaseConstants.BEGINS );

		if ( theArray != null )
		{
			if ( theArray.length > 0 )
			{
				for (int i = 0; ( i < theArray.length ); i++ )
				{
					if (( theArray[ i ] != null ) && ( !theArray[ i ].toString().isEmpty() )) 
					{
						returnValue = false;
						break;
					}					
				}					
			}
		}
		
		logger.debug( BaseConstants.ENDS );
		
		return( returnValue );
	}	
	

}
