/**
 * 
 *  @author eric
 *  
 */

package com.eric.shell.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.shell.command.Command;
import com.eric.shell.command.crypto.DecryptCommand;
import com.eric.shell.command.crypto.EncryptCommand;
import com.eric.shell.domain.adapter.Adapter;
import com.eric.shell.domain.adapter.CryptoAdapter;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CryptoConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.context.ShellContext;

public class CommandLineController
{

	/**
	 * Getting Logger Object
	 */

	private static final Log methIDMain = LogFactory.getLog(CommandLineController.class.getName() + ".main()");

	/**
	 * Main Method for invoking the either Text or GUI version.
	 * 
	 * @param args
	 * @param Command
	 *            Required
	 * @param Additional
	 *            Args Optional
	 * 
	 */
	
	public static void main(String[] args)
	{
		Log logger 					= methIDMain;
		int i 						= 0;
		boolean keepOnTruckin		= true;
		Command targetCommand 		= null;
		Adapter cAdapter 			= null; 
		ShellContext context 		= null;
		MessageCollection mc		= null;
		String errorMsg				= null;

		logger.debug(BaseConstants.BEGINS);

		if (args.length > 0)
		{
			// Order should be: alias'd command #1, then params, or
			// call to embedded file, executed via OS Command.

			while( keepOnTruckin )
			{
				logger.debug("Parameter Received: " + args[i]);
				
				cAdapter 	= new CryptoAdapter(CryptoConstants.CONFIG_FILENAME);
				context 	= cAdapter.toContext( args );

				if ( context == null )
				{					
					logger.error( ErrorMessageConstants.CONTEXT_IS_NULL );
					keepOnTruckin = false;
					break;
				}
				
				if (args[i].equalsIgnoreCase( CryptoCommandOption.DES_FILE_ENCRYPT.toString() ))
				{
					targetCommand = new EncryptCommand();
				}
				else if (args[i].equalsIgnoreCase( CryptoCommandOption.DES_FILE_DECRYPT.toString() ))
				{
					targetCommand = new DecryptCommand();
				}
				else
				{
					errorMsg = String.format(ErrorMessageConstants.INVALID_COMMAND_OPTION_RECEIVED, args[i]);
					logger.error( errorMsg  );
					keepOnTruckin = false;
					break;					
				}

				targetCommand.setContext( context );
				keepOnTruckin = false;
				break;
			}

			
			if (targetCommand != null)
			{
				targetCommand.execute();
				targetCommand = null;
			}
			
			if ( cAdapter != null )
			{
				cAdapter = null;
			}			

		}
		else
		{
			printUsage();
		}

		logger.debug(BaseConstants.ENDS);

		return;

	}

	/**
	 * 
	 * Displays usage information, params, etc.
	 * 
	 */
	protected static void printUsage()
	{
		System.out.println("Usage: java "
				+ CommandLineController.class.getName()
				+ " CommandName, <CommandParameters>");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("eg: java -classpath <jars and config files> "
				+ CommandLineController.class.getName()
				+ " commandX p1 p2 p3...");
		System.out.println("");
		System.out.println("");
	}

}
