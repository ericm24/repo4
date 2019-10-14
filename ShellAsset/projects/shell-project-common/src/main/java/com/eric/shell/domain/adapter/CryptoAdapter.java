package com.eric.shell.domain.adapter; 

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.AdapterMessageConstants;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.ErrorMessageConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoConfigFileRequiredKey;
import com.eric.shell.domain.common.enumeration.DESFileRequiredKey;
import com.eric.shell.domain.common.message.ExMessages;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.context.ShellContext;
import com.eric.utils.string.StringHelper;

public class CryptoAdapter extends AbstractBaseAdapter
{
	private final static Log methIDtoContext 		= LogFactory.getLog(CryptoAdapter.class.getName() + ".toContext()");
	private final static Log methIDloadArgArray 	= LogFactory.getLog(CryptoAdapter.class.getName() + ".loadArgArray()");
	private final static Log methIDloadConfigFile	= LogFactory.getLog(CryptoAdapter.class.getName() + ".loadConfigFile()");
	
	private String configFileName = null;
	

	private CryptoAdapter()
	{		
	}
	
	public CryptoAdapter(String newValue)
	{
		this.setConfigFileName( newValue );
		return;
	}
	
	public ShellContext toContext(String[] params)
	{		
		Log logger 							= methIDtoContext;		
		ShellContext	context				= null;
		
		logger.debug(BaseConstants.BEGINS);

		context = this.loadArgArray( params );
		
		context = this.loadConfigFile( context );
		
		logger.debug(BaseConstants.ENDS);
		
		return( context );
	}		
		
	private ShellContext loadArgArray(String[] params)
	{		
		Log logger 							= methIDloadArgArray;
		ShellContext returnValue 			= null;
		Map<String, Object> ctxParams 		= null;
		String errorMsg 					= null;
		MessageCollection mc				= null;
		int i								= 0;

		logger.debug(BaseConstants.BEGINS);

		returnValue 						= new ShellContext();
		ctxParams 							= new HashMap<String, Object>();
		
		returnValue.setParams( ctxParams );
		
		if (( !StringHelper.isNullOrEmpty( params )) )
		{

			//
			// There should be 3 inital required params:  CryptoOperation, FileName, Password...Basic or Advanced.
			//
			// For new version...crypto operation needs to be either:
			//		EncryptFile, EncryptString, DecryptFile, DecryptString
			//	See: CryptoCommandOption
			//			
			
			
			// TODO: Fix Me!!! This is DES ONLY!!
			
			for (DESFileRequiredKey cryptoCommandRequiredKey : DESFileRequiredKey.values() ) 
			{				

				logger.debug("Processing CryptoCommandRequiredKey: " + cryptoCommandRequiredKey.toString());			

				if ( !StringHelper.isNullOrEmpty( params[ i ] ))
				{
					
					// First Param should be the command option, if not log an error...
					if ( i == 0 )
					{
						if ( params[ i ].equals( CryptoCommandOption.DES_FILE_ENCRYPT.toString() ))
						{
							ctxParams.put(cryptoCommandRequiredKey.toString(), CryptoCommandOption.DES_FILE_ENCRYPT);						
						}
						else if (params[ i ].equals( CryptoCommandOption.DES_FILE_DECRYPT.toString() ))
						{
							ctxParams.put(cryptoCommandRequiredKey.toString(), CryptoCommandOption.DES_FILE_DECRYPT);
						}
						else
						{
							errorMsg = String.format(ErrorMessageConstants.INVALID_COMMAND_OPTION_RECEIVED,
														params[ i ]);
	
							logger.error( errorMsg );
		
							mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));				
						}
					
					}
					else
					{					
						ctxParams.put(cryptoCommandRequiredKey.toString(), params[ i ]);
					}
				}
				else
				{
					errorMsg = String.format(AdapterMessageConstants.PARAMETER_IS_NULL,
												i, 
												cryptoCommandRequiredKey.toString());
				
					logger.error( errorMsg );
					
					mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));				
				}
				
				i++;
			}
			
			returnValue.setParams(ctxParams);

		}
		else
		{
			errorMsg = AdapterMessageConstants.ARGS_ARRAY_IS_NULL;
									 	
			logger.error( errorMsg );
			
			mc = this.processMessage(mc, new Message(MessageLevel.SEVERE, errorMsg));				
			returnValue.setMessages( mc );			
		}
		
		logger.debug(BaseConstants.ENDS);		

		return( returnValue);
	}
	
	private ShellContext loadConfigFile(ShellContext context)
	{		
		Log logger 							= methIDloadConfigFile;		
		Map<String, Object> ctxParams 		= null;
		String errorMsg 					= null;
		String value						= null;
		InputStream inputStream				= null;
		InputStreamReader inputStreamReader = null;		
        JSONParser jsonParser 				= null;
        JSONObject jsonObject 				= null;		
		boolean keepOnTruckin				= true;
		
		logger.debug(BaseConstants.BEGINS);
		
		while( keepOnTruckin )
		{

			try
			{				
				logger.debug("Attempting To Open File: " + configFileName);
				
				inputStream = getClass().getClassLoader().getResourceAsStream( configFileName );
				
				if ( inputStream == null )
				{
					errorMsg = String.format(ErrorMessageConstants.CONFIG_FILE_IS_MISSING, 
												configFileName);
					
					logger.error( errorMsg );
					keepOnTruckin = false;
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					break;										
				}

		        inputStreamReader 	= new InputStreamReader( inputStream  );		        
		        jsonParser 			= new JSONParser();

				logger.debug("Attempting To Load Properties File: " + configFileName);
				
		        jsonObject = (JSONObject) jsonParser.parse( inputStreamReader );

				if (jsonObject == null) 
				{					
					errorMsg = String.format(ErrorMessageConstants.CONFIG_FILE_IS_CORRUPT, 
												configFileName);
					
					logger.error( errorMsg );
					keepOnTruckin = false;
					context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));					
					break;
				}
				
				for (CryptoConfigFileRequiredKey cryptoConfigFileRequiredKey : CryptoConfigFileRequiredKey.values() ) 
				{				
					logger.debug("Processing CryptoConfigFileRequiredKey: " + cryptoConfigFileRequiredKey.toString());			

					value = (String)jsonObject.get(cryptoConfigFileRequiredKey.toString() );				
					
					if (value != null)
					{
						ctxParams = context.getParams();
						
						ctxParams.put(cryptoConfigFileRequiredKey.toString(), value);
					}
					else
					{				
						errorMsg = String.format(ErrorMessageConstants.CONFIG_FILE_IS_MISSING_KEY,
													configFileName,
													cryptoConfigFileRequiredKey.toString());

						logger.error(errorMsg);
						keepOnTruckin = false;
						context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));				
					}

				}
				
				
			}
			catch(IOException | ParseException  mEx)
			{
				errorMsg = String.format(ExMessages.GENEXCEPTION_ENCOUNTERED,
											mEx.getClass().getName(),
											mEx.getMessage());

				logger.error( errorMsg );
				keepOnTruckin = false;
				context.getMessages().add(new Message(MessageLevel.SEVERE, errorMsg));				
			}		

			
			// Safety Purposes
			keepOnTruckin = false;
			break;			
		}		

		logger.debug(BaseConstants.ENDS);
		
		return( context );
	}
	
	public String getConfigFileName() 
	{
		return configFileName;
	}

	public void setConfigFileName(String configFileName) 
	{
		this.configFileName = configFileName;
	}
	
}
