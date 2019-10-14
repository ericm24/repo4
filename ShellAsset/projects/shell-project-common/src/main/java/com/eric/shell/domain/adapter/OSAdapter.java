package com.eric.shell.domain.adapter;

import java.util.HashMap;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.AdapterMessageConstants;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.context.ShellContext;

public class OSAdapter extends AbstractBaseAdapter
{

	public ShellContext toContext(String[] params)
	{

		ShellContext returnValue = null;
		HashMap ctxParams = null;
		int i = 0;
		Message msg = null;
		String txtLine = null;
		boolean control = true;

		while (control)
		{

			if (params.length > 1)
			{
				returnValue = new ShellContext();
				ctxParams = new HashMap();

				// ADDD A FOR LOOP HERE...
				// Must have a TargetFile EXE, BAT, MSI, etc to run...
				if (params[i] == null)
				{
					txtLine = AdapterMessageConstants.PARAMETER_IS_NULL.format(
							BaseConstants.ONE, BaseConstants.TARGET_RUN_FILE);

					msg = new Message(MessageLevel.SEVERE, txtLine);

					control = false;
					break;
				}

				ctxParams.put(BaseConstants.TARGET_RUN_FILE, params[i++]);

				if (params[i] == null)
				{
					control = false;
					break;
				}

				// Add In The Rest of the Parameters..
				// We do not know or care what they are....
				ctxParams.put(BaseConstants.PARAM_N, params[i++]);

				returnValue.setParams(ctxParams);

			}
			else
			{
				control = false;
				break;
			}

			// Safety Purposes.
			control = false;
			break;
		}

		return (returnValue);
	}

}
