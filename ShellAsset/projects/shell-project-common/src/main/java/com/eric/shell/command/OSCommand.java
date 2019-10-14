package com.eric.shell.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.domain.common.constant.BaseConstants;

public class OSCommand extends AbstractBaseCommand
{

	private static final Log methIDExecute = LogFactory.getLog(OSCommand.class
			.getName() + ".execute()");

	@Override
	public boolean execute()
	{
		boolean returnValue = false;

		Log logger = methIDExecute;

		logger.debug(BaseConstants.BEGINS);

		// This Works
		String basePath = "src/main/resources/";
		String command = "cloak2.bat";// null; //"tree6.com /A";

		String line = null;
		Process proc = null;

		try
		{

			// System.out.println( strings );
			// Change Path and Run...fails.
			// proc = Runtime.getRuntime().exec("tree3.com /A", strings);

			// Run from established os-path...works!
			// proc = Runtime.getRuntime().exec("tree.com /A");

			// FQDN....works!
			// proc = Runtime.getRuntime().exec("c:\\dev\\cmdz2\\tree3.com /A");

			command = (basePath != null) ? basePath + command : command;

			logger.debug("Attempting To Run: " + command);

			// Run from embedded in project...
			proc = Runtime.getRuntime().exec(command);

			BufferedReader input = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			logger.debug("Output Starts...");

			while ((line = input.readLine()) != null)
			{
				logger.debug("Line: " + line);
			}

			input.close();

			System.out.println("Output Ends...");

		}
		catch (Exception ex)
		{
			System.out.println("Exception Encountered:  " + ex.getMessage());
		}

		logger.debug(BaseConstants.ENDS);

		return (returnValue);
	}

	@Override
	public boolean undo()
	{
		boolean returnValue = false;

		return (returnValue);
	}

}
