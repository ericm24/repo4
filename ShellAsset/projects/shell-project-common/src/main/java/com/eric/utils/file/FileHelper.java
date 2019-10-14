/**
* ---------------------------------------------------------------------------
* COPYRIGHT NOTICE  Copyright (c) 2011 by Citigroup, Inc.  All rights reserved.
* These materials are confidential and proprietary to  Citigroup, Inc.
* No part of this code may be reproduced, published in
* any form by any means (electronic or mechanical, including photocopy or
* any information storage or retrieval system), nor may the materials be
* disclosed to third parties, or used in derivative works without the
* express written authorization of Citigroup, Inc.
* ---------------------------------------------------------------------------
*/

package com.eric.utils.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.message.ExMessages;

/**
 * Helper Class for File, FileStream I/O things.  
 * 
 * @author em32459
 * 
 */
public class FileHelper 
{

	private static final Log methIDcloseFileInputStream;
	private static final Log methIDcloseFileOutputStream;	
	private static final Log methIDcloseInputStream;
	private static final Log methIDcloseOutputStream;
	
	private static final Log methIDcloseBufferedReader;
	
	static 
	{
		methIDcloseFileInputStream 		= LogFactory.getLog(FileHelper.class.getName() + ".closeFileInputStream()");
		methIDcloseFileOutputStream 	= LogFactory.getLog(FileHelper.class.getName() + ".closeFileOutputStream()");
		methIDcloseInputStream 			= LogFactory.getLog(FileHelper.class.getName() + ".closeInputStream()");
		methIDcloseOutputStream 		= LogFactory.getLog(FileHelper.class.getName() + ".closeOutputStream()");

		
		methIDcloseBufferedReader 		= LogFactory.getLog(FileHelper.class.getName() + ".closeBufferedReader()");		
	}
	/**
     * Privatize constructor for utility class
     * 
     */	
	private FileHelper()
	{		
	}
	
	/**
     * Closes File Input Stream.  Catches & logs IOException.   
     * @return <CODE>true</CODE> if successful.
     * <CODE>false</CODE> otherwise
     *  
     * @param fileInputStream -Open handle to a FileInputStream.
     * 
     */
	public static boolean closeFileInputStream(FileInputStream fileInputStream)
	{

		Log logger = methIDcloseFileInputStream;
		
		boolean returnValue 	= false;
		String errorMsg 		= null;
		
		logger.debug( BaseConstants.BEGINS );		

		if ( fileInputStream != null )
		{
			
			try
			{
				fileInputStream.close();
				
				returnValue = true;
			}
			catch (IOException ioex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
								IOException.class.getName(),
								ioex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;	
			
			}
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
						Exception.class.getName(),
						ex.getMessage());
		
				logger.error(errorMsg);
				returnValue = false;			
			}			
			
		}
		
		logger.debug( BaseConstants.ENDS );		
		
		return( returnValue );
	}
	
	/**
	 * Closes File Output stream, after executing a flush.  Catches & logs IOException.   
	 * @return <CODE>true</CODE> if successful.
	 * <CODE>false</CODE> otherwise
	 *  
	 * @param fileOutputStream -Open handle to a FileOutputStream.
	 * 
	 */	
	public static boolean closeFileOutputStream(FileOutputStream fileOutputStream)
	{
	
		Log logger = methIDcloseFileOutputStream;
		
		boolean returnValue 	= false;
		String errorMsg 		= null;
		
		
		logger.debug( BaseConstants.BEGINS );
		
		if ( fileOutputStream != null )
		{
			
			try
			{
				fileOutputStream.flush();
				fileOutputStream.close();
				
				returnValue = true;
			}
			catch (IOException ioex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
								IOException.class.getName(),
								ioex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;	
			
			}
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
						Exception.class.getName(),
						ex.getMessage());
		
				logger.error(errorMsg);
				returnValue = false;			
			}			
			
		}
		
		logger.debug( BaseConstants.ENDS );		
		return( returnValue );
		
	}

	/**
     * Closes Simple Input Stream.  Catches & logs IOException.   
     * @return <CODE>true</CODE> if successful.
     * <CODE>false</CODE> otherwise
     *  
     * @param fileInputStream -Open handle to a FileInputStream.
     * 
     */
	public static boolean closeInputStream(InputStream inputStream)
	{

		Log logger = methIDcloseInputStream;
		
		boolean returnValue 	= false;
		String errorMsg 		= null;
		
		logger.debug( BaseConstants.BEGINS );		

		if ( inputStream != null )
		{
			
			try
			{
				inputStream.close();
				
				returnValue = true;
			}
			catch (IOException ioex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											IOException.class.getName(),
											ioex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;	
			
			}
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(),
											ex.getMessage());
		
				logger.error(errorMsg);
				returnValue = false;			
			}			
			
		}
		
		logger.debug( BaseConstants.ENDS );		
		
		return( returnValue );
	}	
	
	/**
     * Closes Simple Output stream, after executing a flush.  Catches & logs IOException.   
     * @return <CODE>true</CODE> if successful.
     * <CODE>false</CODE> otherwise
     *  
     * @param fileOutputStream -Open handle to a FileOutputStream.
     * 
     */	
	public static boolean closeOutputStream(OutputStream outputStream)
	{

		Log logger = methIDcloseFileOutputStream;
		
		boolean returnValue 	= false;
		String errorMsg 		= null;
		
		
		logger.debug( BaseConstants.BEGINS );
		
		if ( outputStream != null )
		{
			
			try
			{
				outputStream.flush();
				outputStream.close();
				
				returnValue = true;
			}
			catch (IOException ioex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											IOException.class.getName(),
											ioex.getMessage());
				
				logger.error(errorMsg);
				returnValue = false;	
			
			}
			catch (Exception ex)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											Exception.class.getName(),
											ex.getMessage());
		
				logger.error(errorMsg);
				returnValue = false;			
			}			
			
		}
		
		logger.debug( BaseConstants.ENDS );		
		return( returnValue );
		
	}	
	
	/**
     * Closes a buffered reader.  Catches & logs IOException.   
     * @return <CODE>true</CODE> if successful.
     * <CODE>false</CODE> otherwise
     *  
     * @param bufferedReader -Open handle to a BufferedReader.
     * 
     */	
	public static BufferedReader closeBufferedReader(BufferedReader bufferedReader)
	{
		Log logger 				= methIDcloseBufferedReader;
		String errorMsg			= null;
		
		logger.debug(BaseConstants.BEGINS);

		try
		{
		    if ( bufferedReader != null )
		    {
		    	bufferedReader.close();
		    	bufferedReader 	= null;
		    }		    
		    
		}
		catch ( IOException iox )
		{
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										IOException.class.getName(), iox
										.getMessage());

			logger.error( errorMsg );			
		}
		catch ( Exception ex )
		{
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										Exception.class.getName(), ex
										.getMessage());

					logger.error( errorMsg );		
		}

		
		logger.debug(BaseConstants.ENDS);
		
		return( bufferedReader );
	}	
	
}
