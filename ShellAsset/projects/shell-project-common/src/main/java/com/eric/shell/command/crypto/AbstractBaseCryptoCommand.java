/**
 */
package com.eric.shell.command.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.command.AbstractBaseCommand;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.utils.file.FileHelper;

/**
 * Abstract-Default Implementation for Crypto-Command interface  
 * and follows Abstract-Interface Pattern. (Combined w/Command Pattern). 
 * -Provides convenience implementation of swapping files (encrypted/decrypted back
 *  to target file).
 * -Closes input and output filestreams via utility class.
 * 
 * @author em32459
 * 
 */
public abstract class AbstractBaseCryptoCommand extends AbstractBaseCommand
{
	private String fileNameIn 				= null;
	private String fileNameTemp 			= null;
	private String password 				= null;
	private FileInputStream inFile 			= null;
	private FileOutputStream outFile 		= null;

	private static final Log methIDSwapFiles; 
	private static final Log methIDClose;
	
	static 
	{
		methIDSwapFiles = LogFactory
			.getLog(AbstractBaseCryptoCommand.class.getName() + ".swapFiles()");
		
		methIDClose = LogFactory
			.getLog(AbstractBaseCryptoCommand.class.getName() + ".close()");
		
	}
	
	/**
     * Swaps scratch-temp file for permanent file.   
     * @return <CODE>true</CODE> if command successful.
     * <CODE>false</CODE> otherwise
     *  If false, check the message collection. 
     *  
     * @param targetFileName - File that is left, contents of source copied to this one.
     * @param sourceFileName - File that contains contents to be copied to target.
     * 
     */	
	protected boolean swapFiles(String targetFileName, String sourceFileName)
	{

		boolean returnValue = false;
		
		Log logger = methIDSwapFiles;
		
		logger.debug(BaseConstants.BEGINS);
		
		File targetFile = null; 
		File sourceFile = null;
		
		targetFile = new File(targetFileName);
		
		returnValue = targetFile.exists();
		
		if ( returnValue )
		{		
			returnValue = targetFile.delete();
		}
		
		if ( !returnValue )
		{
			String errorMsg = String.format(ErrorMessageConstants.UNABLE_TO_DELETE_FILE, targetFileName);
			logger.error( errorMsg );
		}
		
		sourceFile = new File(sourceFileName);

		sourceFile.renameTo(targetFile);

		logger.debug(BaseConstants.ENDS);
		
		return (returnValue);
	}
	
	/**
     * Closes BOTH input and output file streams.  Provides wrapper for exception
     * handling.  For output stream, a flush is executed prior.
     *    
     * @return <CODE>true</CODE> if command successful.
     * <CODE>false</CODE> otherwise
     *  If false, check the message collection. 
     *  
     * @see FileHelper
     * 
     */	
	protected boolean close()
	{
		boolean returnValue = false;
		Log logger = methIDClose;
		
		logger.debug(BaseConstants.BEGINS);
		
		FileHelper.closeInputStream( inFile );		
		
		// This is the file that counts!
		returnValue = FileHelper.closeOutputStream( outFile );	

		if ( returnValue )
		{
			// Now Swap them.
			returnValue = swapFiles(fileNameIn, fileNameTemp);
		}
		
		logger.debug(BaseConstants.ENDS);
	
		return (returnValue);
	}

	public String getFileNameIn()
	{
		return fileNameIn;
	}

	public void setFileNameIn(String fileNameIn)
	{
		this.fileNameIn = fileNameIn;
	}

	public String getFileNameTemp()
	{
		return fileNameTemp;
	}

	public void setFileNameTemp(String fileNameTemp)
	{
		this.fileNameTemp = fileNameTemp;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public FileInputStream getInFile()
	{
		return inFile;
	}

	public void setInFile(FileInputStream inFile)
	{
		this.inFile = inFile;
	}

	public FileOutputStream getOutFile()
	{
		return outFile;
	}

	public void setOutFile(FileOutputStream outFile)
	{
		this.outFile = outFile;
	}
	
}
