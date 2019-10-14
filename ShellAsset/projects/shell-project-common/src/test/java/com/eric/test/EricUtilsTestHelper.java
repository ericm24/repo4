/**
 */
package com.eric.test;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;

import com.eric.shell.domain.common.constant.BaseConstants;

public class EricUtilsTestHelper
{
	
	private final static Log methIDsetPropertyReference;
	
	static
	{
		methIDsetPropertyReference 					= LogFactory.getLog(EricUtilsTestHelper.class.getName() + ".setPropertyReference()");
	};
	
	private EricUtilsTestHelper()
	{
	}

	public static void setPropertyReference(String propKey,  String newValue)
	{		
		Log logger 						= methIDsetPropertyReference;		
		Set<Object> propsSettings		= null;
		Iterator<Object> itr			= null;		
		String key						= null;
		String value 					= null;
		Properties props				= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( propKey == null )
		{
			logger.error("Property Key Is NULL!!!");
			Assert.assertTrue( false );
		}
		
		if ( newValue != null )
		{
			logger.info("Setting System Property: " + propKey + " to:  "
					+ newValue);

			props = System.getProperties();

			props.setProperty(propKey, newValue);
		}
		else
		{
			logger.info("Clearing System Property: " + propKey );
			System.clearProperty( propKey );
		}
		
		logger.info("Verifying System Property: " + propKey);
		
		props = null;
		
		props = System.getProperties();
		Assert.assertNotNull ( props );
		
		propsSettings 	= props.keySet();			
		itr 			= propsSettings.iterator();
		
		while( itr.hasNext() ) 
		{
			key  		= (String) itr.next();
			value 		= props.get( key ).toString();
			
			logger.debug("Located Key: " + key + " | Value: " +  value);
			
			if ( key.equals( propKey ) )
			{
				logger.info("***Property Key Present: " + propKey);
				logger.info("*****Property Key Value: " + value);				
				
				Assert.assertTrue( key.equals( propKey ));
				Assert.assertTrue( value.equals( newValue ));				
				
			}
		}
		
		logger.debug(BaseConstants.ENDS);

		return;
	}		
	
	
	
	
}
