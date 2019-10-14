/**
*/

package com.eric.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A generic base class for Shell test cases.
 * 
 * @author
 * 
 */
public abstract class AbstractBaseTestCase implements EricTestCase
{
	private static Log methIDSetAssertON;

	private boolean assertON = true;

	private final static String SET_ASSERT_ON = "Setting ASSERTON To: %s";

	static
	{
		methIDSetAssertON = LogFactory.getLog(AbstractBaseTestCase.class.getName()
				+ ".setAssertON()");
	}

	
	public abstract void runAllTests();
	
	public boolean isAssertON()
	{
		return assertON;
	}

	public void setAssertON(boolean newValue)
	{
		Log logger = methIDSetAssertON;
		String lineItem = null;

		lineItem = String.format(this.SET_ASSERT_ON, newValue);

		logger.info( lineItem );

		this.assertON = newValue;

		return;
	}

}
