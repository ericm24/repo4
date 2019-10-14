/**
 */
package com.eric.utils.string;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.test.AbstractBaseTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
	"classpath:test-context/shell-project-common-context.xml"
})

public class StringHelper_UT extends AbstractBaseTestCase
{
	
	private final static Log methIDrunTestStringIsNullSUCCESS;
	private final static Log methIDrunTestArrayIsNullSUCCESS;	
	private final static Log methIDrunTestStringIsEmptySUCCESS;
	private final static Log methIDrunTestArrayIsEmptySUCCESS;	
	private final static Log methIDrunTestStringNotEmptyNotNullSUCCESS;	
	private final static Log methIDrunTestArrayNotEmptyNotNullSUCCESS;
	private final static Log methIDrunTestArrayNotEmptyEndsNullSUCCESS;
	private final static Log methIDrunTestStringIsBlankSpaceSUCCESS;	

	static
	{
		methIDrunTestStringIsNullSUCCESS				= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestStringIsNullSUCCESS()");
		methIDrunTestArrayIsNullSUCCESS					= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestArrayIsNullSUCCESS()");		
		methIDrunTestStringIsEmptySUCCESS				= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestStringIsEmptySUCCESS()");
		methIDrunTestArrayIsEmptySUCCESS				= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestArrayIsEmptySUCCESS()");		
		methIDrunTestStringNotEmptyNotNullSUCCESS		= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestStringNotEmptyNotNullSUCCESS()");
		methIDrunTestArrayNotEmptyNotNullSUCCESS		= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestArrayNotEmptyNotNullSUCCESS()");		
		methIDrunTestArrayNotEmptyEndsNullSUCCESS		= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestArrayNotEmptyEndsNullSUCCESS()");		
		methIDrunTestStringIsBlankSpaceSUCCESS			= LogFactory.getLog(StringHelper_UT.class.getName() + ".runTestStringIsBlankSpaceSUCCESS()");		
	}	
	@Autowired

	@Test
	public void runTestStringIsNullSUCCESS()
	{		
		Log logger 				= methIDrunTestStringIsNullSUCCESS;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( (String)null );		
		
		Assert.assertTrue( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	@Test
	public void runTestArrayIsNullSUCCESS()
	{		
		Log logger 				= methIDrunTestArrayIsNullSUCCESS;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( (String[])null );		
		
		Assert.assertTrue( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	@Test
	public void runTestStringIsEmptySUCCESS()
	{		
		Log logger 				= methIDrunTestStringIsEmptySUCCESS;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( BaseConstants.BLANK_STRING );		
		
		Assert.assertTrue( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	

	
	@Test
	public void runTestArrayIsEmptySUCCESS()
	{		
		Log logger 				= methIDrunTestArrayIsEmptySUCCESS;
		boolean returnValue		= false;
		String[] theArray		= { null, null, null };		
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( theArray );		
		
		Assert.assertTrue( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}	
	
	@Test
	public void runTestStringNotEmptyNotNullSUCCESS()
	{		
		Log logger 				= methIDrunTestStringNotEmptyNotNullSUCCESS;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( "foo" );		
		
		Assert.assertFalse( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	@Test
	public void runTestArrayNotEmptyNotNullSUCCESS()
	{		
		Log logger 				= methIDrunTestArrayNotEmptyNotNullSUCCESS;
		boolean returnValue		= false;
		String[] theArray		= { "one", "two", "three" };		
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( theArray );		
		
		Assert.assertFalse( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		

	@Test
	public void runTestArrayNotEmptyEndsNullSUCCESS()
	{		
		Log logger 				= methIDrunTestArrayNotEmptyEndsNullSUCCESS;
		boolean returnValue		= false;
		String[] theArray		= { null, "one", "two", "three", null, null };		
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( theArray );		
		
		Assert.assertFalse( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	
	@Test
	public void runTestStringIsBlankSpaceSUCCESS()
	{		
		Log logger 				= methIDrunTestStringIsBlankSpaceSUCCESS;
		boolean returnValue		= false;
		
		logger.debug(BaseConstants.BEGINS);
		
		returnValue = StringHelper.isNullOrEmpty( BaseConstants.SPACE );		
		
		Assert.assertFalse( returnValue );

		logger.debug(BaseConstants.ENDS);		
		
		return;
	}		
	
	
	
	public void runAllTests()
	{

		return;
	}	
}