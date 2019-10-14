/**
 */

package com.eric.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.shell.command.crypto.AbstractBaseCryptoTestCommand;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.enumeration.TargetFileOption;
import com.eric.shell.domain.common.message.ExMessages;
import com.eric.shell.domain.constant.BaseTestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})

public class FileHelper_UT extends AbstractBaseCryptoTestCommand
{
	private final static Log methIDrunTestCloseNULLFileInputStreamFAIL;
	private final static Log methIDrunTestCloseFileInputStreamSUCCESS;	
	private final static Log methIDrunTestCloseNULLInputStreamFAIL;
	private final static Log methIDrunTestCloseInputStreamSUCCESS;	
	
	private final static Log methIDrunTestCloseNULLFileOutputStreamFAIL;	
	private final static Log methIDrunTestCloseFileOutputStreamSUCCESS;
	
	private final static Log methIDrunTestCloseNULLBufferedReaderFAIL;
	private final static Log methIDrunTestCloseBufferedReaderSUCCESS;

	static
	{
		methIDrunTestCloseNULLFileInputStreamFAIL		= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseNULLFileInputStreamFAIL()");
		methIDrunTestCloseFileInputStreamSUCCESS		= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseFileInputStreamSUCCESS()");
		methIDrunTestCloseNULLInputStreamFAIL			= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseNULLInputStreamFAIL()");
		methIDrunTestCloseInputStreamSUCCESS			= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseInputStreamSUCCESS()");		
		methIDrunTestCloseNULLFileOutputStreamFAIL		= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseNULLFileOutputStreamFAIL()");
		methIDrunTestCloseFileOutputStreamSUCCESS		= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseFileOutputStreamSUCCESS()");
		
		methIDrunTestCloseNULLBufferedReaderFAIL		= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseNULLBufferedReaderFAIL()");
		methIDrunTestCloseBufferedReaderSUCCESS			= LogFactory.getLog(FileHelper_UT.class.getName() + ".runTestCloseBufferedReaderSUCCESS()");
	}	
	
	@Autowired

	@Test
	public void runTestCloseNULLFileInputStreamFAIL()
	{		
		Log logger 				= methIDrunTestCloseNULLFileInputStreamFAIL;
		FileInputStream	in		= null;
		
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);

		if ( this.isAssertON() )
		{
			Assert.assertNull( in );
		}
		
		returnValue = FileHelper.closeFileInputStream( in );		
		
		if ( this.isAssertON() )
		{
			Assert.assertFalse( returnValue );
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	@Test
	public void runTestCloseFileInputStreamSUCCESS() 
	{		
		Log logger 				= methIDrunTestCloseFileInputStreamSUCCESS;
		boolean returnValue		= false;
		FileInputStream in		= null;
		File file				= null;
		String fileName			= null;
		String errorMsg			= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		fileName = 		BaseTestConstants.TEST_FILE_NAME_GOOD;
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( in );
		}
		
		this.writeTargetFile(TargetFileOption.TEST_FILE_CONTENTS_TEXT
				.toString());

		try
		{
			file 	= new File( fileName );
			in 		= new FileInputStream( file );
			
			returnValue = file.exists();
			
			if ( this.isAssertON() )
			{
				Assert.assertTrue( returnValue );
			}
			
		}
		catch( FileNotFoundException fnex)
		{
			
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
					NoSuchPaddingException.class.getName(), fnex
							.getMessage());
			
			logger.error( errorMsg );
		}

		returnValue = FileHelper.closeInputStream( in );
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue( returnValue );
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	
	@Test
	public void runTestCloseNULLInputStreamFAIL()
	{		
		Log logger 				= methIDrunTestCloseNULLInputStreamFAIL;
		InputStream	in			= null;
		
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);

		if ( this.isAssertON() )
		{
			Assert.assertNull( in );
		}
		
		returnValue = FileHelper.closeInputStream( in );		
		
		if ( this.isAssertON() )
		{
			Assert.assertFalse( returnValue );
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	@Test
	public void runTestCloseInputStreamSUCCESS() 
	{		
		Log logger 				= methIDrunTestCloseInputStreamSUCCESS;
		boolean returnValue		= false;
		InputStream in			= null;
		File file				= null;
		String fileName			= null;
		String errorMsg			= null;
		ClassLoader cl			= null;		
		
		logger.debug(BaseConstants.BEGINS);
		
		fileName 				= "shell-project-common-config.xml";
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( in );
		}

		logger.debug("Using File: " +  fileName );
		
		cl 		= getClass().getClassLoader();		
		Assert.assertNotNull( cl );
		
		in 		= cl.getResourceAsStream( fileName );

		// Fix This!!!
		Assert.assertNotNull( in );

		returnValue = FileHelper.closeInputStream( in );
		
		if ( this.isAssertON() )
		{
			Assert.assertTrue( returnValue );
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}			
	
	@Test
	public void runTestCloseNULLFileOutputStreamFAIL()
	{		
		Log logger 				= methIDrunTestCloseNULLFileOutputStreamFAIL;
		FileOutputStream out	= null;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( out );
		}
		
		returnValue = FileHelper.closeOutputStream( out );		

		if ( this.isAssertON() )
		{
			Assert.assertFalse( returnValue );
		}

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	@Test
	public void runTestCloseFileOutputStreamSUCCESS()
	{		
		Log logger 				= methIDrunTestCloseFileOutputStreamSUCCESS;
		boolean returnValue		= false;
		FileOutputStream out	= null;
		String errorMsg			= null;
		String fileName			= null;
		File file				= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		fileName = 		BaseTestConstants.TEST_FILE_NAME_GOOD;
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( out );
		}

		try
		{
			file 	= new File( fileName );
			out		= new FileOutputStream( file );
			
			returnValue = file.exists();
			
			if ( !returnValue )
			{
				file.createNewFile();
			}

			returnValue = file.exists();

			if ( this.isAssertON() )
			{
				Assert.assertTrue( returnValue );
			}
			
			returnValue = FileHelper.closeOutputStream( out );
			
			if ( this.isAssertON() )
			{
				Assert.assertTrue( returnValue );
			}
			
		}
		catch( IOException iox)
		{
			
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
					IOException.class.getName(), iox
							.getMessage());
			
			logger.error( errorMsg );
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;	}		
	
	@Test
	public void runTestCloseNULLBufferedReaderFAIL()
	{		
		Log logger 							= methIDrunTestCloseNULLBufferedReaderFAIL;
		BufferedReader bufferedReader		= null;
		FileReader fileReader				= null;
		boolean returnValue					= false;
		
		logger.debug(BaseConstants.BEGINS);

		if ( this.isAssertON() )
		{
			Assert.assertNull( fileReader );			
			Assert.assertNull( bufferedReader );
		}
		
		bufferedReader = FileHelper.closeBufferedReader( bufferedReader );

		if ( this.isAssertON() )
		{
			Assert.assertFalse( returnValue );
			Assert.assertNull( bufferedReader );			
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	@Test
	public void runTestCloseBufferedReaderSUCCESS()
	{		
		Log logger 							= methIDrunTestCloseBufferedReaderSUCCESS;
		BufferedReader bufferedReader		= null;
		FileReader fileReader				= null;
		String fileName						= null;
		String errorMsg						= null;
		
		logger.debug(BaseConstants.BEGINS);

		fileName = BaseTestConstants.TEST_FILE_NAME_GOOD;		
		
		if ( this.isAssertON() )
		{
			Assert.assertNull( fileReader );			
			Assert.assertNull( bufferedReader );
		}
		
		try
		{
			fileReader 		= new FileReader( fileName );
			
			if ( fileReader != null )
			{
				bufferedReader 	= new BufferedReader( fileReader );
			}
			
			if ( this.isAssertON() )
			{
				Assert.assertNotNull( bufferedReader );
			}
			
			bufferedReader = FileHelper.closeBufferedReader( bufferedReader );
			
			if ( this.isAssertON() )
			{
				Assert.assertNull( bufferedReader );
			}

		}
		catch ( FileNotFoundException fnfe )
		{
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										FileNotFoundException.class.getName(), fnfe
										.getMessage());

			logger.error( errorMsg );
			
			Assert.assertTrue( false );
		}
		catch ( Exception ex )
		{
			errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
										Exception.class.getName(), ex
										.getMessage());

			logger.error( errorMsg );	
			
			Assert.assertTrue( false );			
		}
		finally
		{
			if ( bufferedReader != null )
			{
				bufferedReader = FileHelper.closeBufferedReader( bufferedReader );
			}
		}
		
		logger.debug(BaseConstants.ENDS);		
		
		return;	
	}		
		
	
	public void runAllTests()
	{

		return;
	}	
}