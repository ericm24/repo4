/**
*/
package com.eric.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.constant.BaseTestConstants;

public class CryptoTestHelper
{
	
	private static Log methIDWriteTargetFile_String;
	private static Log methIDWriteTargetFile_Byte;	
	private static Log methIDCheckTargetFile;

	static
	{
		methIDWriteTargetFile_String 	= LogFactory.getLog(CryptoTestHelper.class.getName() + ".writeTargetFile(String)");
		methIDWriteTargetFile_Byte	 	= LogFactory.getLog(CryptoTestHelper.class.getName() + ".writeTargetFile(Byte[])");		
		methIDCheckTargetFile 			= LogFactory.getLog(CryptoTestHelper.class.getName() + ".checkTargetFile()");
	}
	

	public void writeTargetFile(byte[] targetHexValues)
	{
		Log logger 					= methIDWriteTargetFile_Byte;
		//byte output[]				= null;
		boolean returnValue 		= false;
		File targetFile 			= null;
		File sourceFile 			= null;
		FileOutputStream outFile 	= null;
		FileInputStream inFile 		= null;

		logger.debug(BaseConstants.BEGINS);
		
		targetFile = new File(BaseTestConstants.TEST_FILE_NAME_GOOD);

		// Delete it first to make sure it does not exist.
		returnValue = targetFile.delete();


		try
		{
			outFile = new FileOutputStream(BaseTestConstants.TEST_FILE_NAME_GOOD);

			byte output[] = targetHexValues;
			
			outFile.write( output );
			outFile.flush();
			outFile.close();

		}
		catch (Exception ex)
		{
			// fail("Exception Encountered: " + ex.getMessage());
			logger.fatal("Exception Encountered: " + ex.getMessage());

		}

		logger.debug(BaseConstants.ENDS);

		return;
	}

	public void writeTargetFile(String targetStringValue)
	{

		Log logger = methIDWriteTargetFile_String;

		logger.debug(BaseConstants.BEGINS);

		boolean returnValue = false;

		File targetFile = null;
		File sourceFile = null;

		FileOutputStream outFile = null;
		FileInputStream inFile = null;

		targetFile = new File(BaseTestConstants.TEST_FILE_NAME_GOOD);

		// Delete it first to make sure it does not exist.
		returnValue = targetFile.delete();


		try
		{
			outFile = new FileOutputStream(BaseTestConstants.TEST_FILE_NAME_GOOD);

			byte output[] = targetStringValue.getBytes();
			outFile.write(output);
			outFile.flush();
			outFile.close();

		}
		catch (Exception ex)
		{
			// fail("Exception Encountered: " + ex.getMessage());
			logger.fatal("Exception Encountered: " + ex.getMessage());

		}

		logger.debug(BaseConstants.ENDS);

		return;
	}
	
	public boolean checkTargetFile(String matchString)
	{
		Log logger = methIDCheckTargetFile;

		logger.debug(BaseConstants.BEGINS);

		boolean returnValue = false;

		File targetFile = null;
		File sourceFile = null;

		byte input[] = null;
		FileInputStream inFile = null;
		String fileString = null;
		int maxLen = 0;

		targetFile = new File(BaseTestConstants.TEST_FILE_NAME_GOOD);

		try
		{

			inFile = new FileInputStream(BaseTestConstants.TEST_FILE_NAME_GOOD);
			input = new byte[64];

			inFile.read(input);

			inFile.close();

			fileString = new String(input);

			maxLen = matchString.length();
			fileString = fileString.substring(0, maxLen);

			returnValue = fileString.equals(matchString) ? true : false;


		}
		catch (Exception ex)
		{
			// fail("Exception Encountered: " + ex.getMessage());
			
			logger.fatal("Exception Encountered: " + ex.getMessage());

		}

		logger.debug(BaseConstants.ENDS);

		return (returnValue);
	}

}
