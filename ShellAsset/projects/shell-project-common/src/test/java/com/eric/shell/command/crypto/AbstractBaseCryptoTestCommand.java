/**
s*/
package com.eric.shell.command.crypto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.enumeration.TargetFileOption;
import com.eric.test.AbstractBaseTestCase;
import com.eric.test.CryptoTestHelper;

public abstract class AbstractBaseCryptoTestCommand extends AbstractBaseTestCase
{

	private final static Log methIDWriteTargetFile_String;
	private final static Log methIDWriteTargetFile_Byte;	
	private final static Log methIDCheckTargetFile;	
	private final static CryptoTestHelper cryptoHelper;
	
	static
	{
		methIDWriteTargetFile_String = LogFactory.getLog(AbstractBaseCryptoTestCommand.class.getName() + 
					".writeTargetFile(String)");

		methIDWriteTargetFile_Byte = LogFactory.getLog(AbstractBaseCryptoTestCommand.class.getName() + 
				".writeTargetFile(byte[])");		
		
		methIDCheckTargetFile = LogFactory.getLog(AbstractBaseCryptoTestCommand.class.getName() + 
					".checkTargetFile()");
		
		cryptoHelper = new CryptoTestHelper();		
	}


	private boolean targetFileMatches 			= true;	
	private TargetFileOption targetFileResult 	= null;
	 	
	protected void writeTargetFile(TargetFileOption targetfileOption)	
	{
		Log logger = methIDWriteTargetFile_Byte;

		logger.debug(BaseConstants.BEGINS);
		
		cryptoHelper.writeTargetFile( targetfileOption.toBytes() );		
		
		logger.debug(BaseConstants.ENDS);

		return;
	}
	
	
	protected void writeTargetFile(String targetStringValue)
	{
		Log logger = methIDWriteTargetFile_String;

		logger.debug(BaseConstants.BEGINS);

		cryptoHelper.writeTargetFile(targetStringValue);		
		
		logger.debug(BaseConstants.ENDS);

		return;
	}

	protected boolean checkTargetFile(String matchString)
	{
		Log logger = methIDCheckTargetFile;

		logger.debug(BaseConstants.BEGINS);

		boolean returnValue = false;
		
		returnValue = cryptoHelper.checkTargetFile( matchString );
		
		logger.debug(BaseConstants.ENDS);

		return ( returnValue );
	}

	public boolean isTargetFileMatches()
	{
		return targetFileMatches;
	}

	public void setTargetFileMatches(boolean targetFileMatches)
	{
		this.targetFileMatches = targetFileMatches;
	}

	public TargetFileOption getTargetFileResult()
	{
		return targetFileResult;
	}

	public void setTargetFileResult(TargetFileOption targetFileResult)
	{
		this.targetFileResult = targetFileResult;
	}
	
}
