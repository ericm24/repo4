/**
 */
package com.eric.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.domain.common.enumeration.MessageLevel;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.constant.CommandConstants;
import com.eric.shell.domain.common.enumeration.CryptoCommandOption;
import com.eric.shell.domain.common.enumeration.CryptoCommandOptionalKey;
import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.constant.BaseTestConstants;
import com.eric.shell.domain.context.ShellContext;

public class EricTestHelper
{

	private final static Log methIDcreateMessageCollection3GroupsAllLevels;	
	private final static Log methIDloadContextWithBaseParamsDESFileDecrypt;
	private final static Log methIDloadContextWithBaseParamsDESFileEncrypt;
	private final static Log methIDloadContextWithBaseParamsDESStringDecrypt;	
	private final static Log methIDloadContextWithBaseParamsDESStringEncrypt;	
	private final static Log methIDloadContextWithBaseParamsPlusSaltDESFileDecrypt;
	private final static Log methIDloadContextWithBaseParamsPlusSaltAESFileDecrypt;
	
	private final static Log methIDloadContextWithBaseParamsAESFileDecrypt;
	private final static Log methIDloadContextWithBaseParamsAESStringDecrypt;
	private final static Log methIDloadContextWithBaseParamsAESFileEncrypt;
	private final static Log methIDloadContextWithBaseParamsAESStringEncrypt;
	
	private final static Log methIDloadArgsWithBaseParams;	
	private final static Log methIDdumpContext;
	
	static
	{
		methIDcreateMessageCollection3GroupsAllLevels			= LogFactory.getLog(EricTestHelper.class.getName() + ".createMessageCollection3GroupsAllLevels()");
		methIDloadContextWithBaseParamsDESFileDecrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsDESFileDecrypt()");		
		methIDloadContextWithBaseParamsDESFileEncrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsDESFileEncrypt()");		
		methIDloadContextWithBaseParamsDESStringDecrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsDESStringDecrypt()");
		methIDloadContextWithBaseParamsDESStringEncrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsDESStringEncrypt()");		
		methIDloadContextWithBaseParamsPlusSaltDESFileDecrypt	= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsPlusSaltDESFileDecrypt()");		
		methIDloadContextWithBaseParamsPlusSaltAESFileDecrypt	= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsPlusSaltAESFileDecrypt()");		
		
		methIDloadContextWithBaseParamsAESFileDecrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsAESFileDecrypt()");		
		methIDloadContextWithBaseParamsAESStringDecrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsAESStringDecrypt()");		
		methIDloadContextWithBaseParamsAESFileEncrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsAESFileEncrypt()");		
		methIDloadContextWithBaseParamsAESStringEncrypt			= LogFactory.getLog(EricTestHelper.class.getName() + ".loadContextWithBaseParamsAESStringEncrypt()");
		
		methIDloadArgsWithBaseParams							= LogFactory.getLog(EricTestHelper.class.getName() + ".loadArgsWithBaseParams()");		
		methIDdumpContext										= LogFactory.getLog(EricTestHelper.class.getName() + ".dumpContext()");		
	}
	
	public static MessageCollection createMessageCollection3GroupsAllLevels()
	{
		
		Log logger 				= methIDcreateMessageCollection3GroupsAllLevels;
		
		MessageCollection mc1 	= null;
		Message msg				= null;
		int i					= 0;
		
		logger.debug(BaseConstants.BEGINS);
		
		mc1 = new MessageCollection();
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupB"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupA"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		for (MessageLevel messageLevel : MessageLevel.values() ) 
		{
			logger.info("Adding Messages of Level: " + messageLevel.toString());
			msg = new Message(messageLevel, messageLevel.toString() + " Message Text: " + i + " GroupC"); 
			
			// Add them ALL to first Collection.
			logger.debug("Adding To Collection1 messageID: " + msg.getID() + " level: " + msg.getLevel().toString() + " text: " + msg.getText() );
			mc1.add( msg );

			i++;
			msg = null;
		}
		
		logger.debug(BaseConstants.ENDS);
		
		return( mc1 );
	}
	
	
	public static ShellContext loadContextWithBaseParamsPlusSaltDESFileDecrypt(ShellContext context)
	{
		
		Log logger 					= methIDloadContextWithBaseParamsPlusSaltDESFileDecrypt;
		
		logger.debug(BaseConstants.ENDS);
		
		context = loadContextWithBaseParamsDESFileDecrypt( context );
		
		context.getParams().put(CryptoCommandOptionalKey.SALT.toString(), BaseTestConstants.DES_TEST_SALT);
		
		logger.debug(BaseConstants.ENDS);
		
		return( context );		
	}
		

	public static ShellContext loadContextWithBaseParamsPlusSaltAESFileDecrypt(ShellContext context)
	{
		
		Log logger 					= methIDloadContextWithBaseParamsPlusSaltAESFileDecrypt;
		
		logger.debug(BaseConstants.ENDS);
		
		context = loadContextWithBaseParamsAESFileDecrypt( context );
		
		context.getParams().put(CryptoCommandOptionalKey.SALT.toString(), BaseTestConstants.AES_TEST_SALT);
		
		logger.debug(BaseConstants.ENDS);
		
		return( context );		
	}
	
	public static ShellContext loadContextWithBaseParamsDESFileDecrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsDESFileDecrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_FILE_DECRYPT);
		params.put(CommandConstants.FILE_IN, BaseTestConstants.TEST_FILE_NAME_GOOD);
		params.put(CommandConstants.PASSWORD, BaseTestConstants.GOOD_PASSWORD);

		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}

	public static ShellContext loadContextWithBaseParamsDESFileEncrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsDESFileEncrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_FILE_ENCRYPT);
		params.put(CommandConstants.FILE_IN, BaseTestConstants.TEST_FILE_NAME_GOOD);
		params.put(CommandConstants.PASSWORD, BaseTestConstants.GOOD_PASSWORD);

		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}
	
	
	
	public static ShellContext loadContextWithBaseParamsDESStringDecrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsDESStringDecrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_STRING_DECRYPT);		
		params.put(CommandConstants.STRING_IN, BaseTestConstants.TEST_FILE_CONTENTS_TEXT);		
		params.put(CommandConstants.PASSWORD, BaseTestConstants.GOOD_PASSWORD);
		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}

	public static ShellContext loadContextWithBaseParamsDESStringEncrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsDESStringEncrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.DES_STRING_ENCRYPT);		
		params.put(CommandConstants.STRING_IN, BaseTestConstants.TEST_FILE_CONTENTS_TEXT);		
		params.put(CommandConstants.PASSWORD, BaseTestConstants.GOOD_PASSWORD);
		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}
	
	public static ShellContext loadContextWithBaseParamsAESFileDecrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsAESFileDecrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();
	
		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_FILE_DECRYPT);
		params.put(CommandConstants.FILE_IN, 		BaseTestConstants.TEST_FILE_NAME_GOOD);
		params.put(CommandConstants.PASSWORD, 		BaseTestConstants.GOOD_PASSWORD);
		params.put(CommandConstants.KEY1, 			BaseTestConstants.GOOD_KEY1);
		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}

	
	public static ShellContext loadContextWithBaseParamsAESFileEncrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsAESFileEncrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_FILE_ENCRYPT);
		params.put(CommandConstants.FILE_IN, 		BaseTestConstants.TEST_FILE_NAME_GOOD);
		params.put(CommandConstants.PASSWORD, 		BaseTestConstants.GOOD_PASSWORD);
		params.put(CommandConstants.KEY1, 			BaseTestConstants.GOOD_KEY1);		

		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}
	
	
	
	public static ShellContext loadContextWithBaseParamsAESStringDecrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsAESStringDecrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_STRING_DECRYPT);		
		params.put(CommandConstants.STRING_IN, 		BaseTestConstants.TEST_FILE_CONTENTS_TEXT);		
		params.put(CommandConstants.PASSWORD, 		BaseTestConstants.GOOD_PASSWORD);
		params.put(CommandConstants.KEY1, 			BaseTestConstants.GOOD_KEY1);		
		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}

	public static ShellContext loadContextWithBaseParamsAESStringEncrypt(ShellContext context)
	{
		Log logger 					= methIDloadContextWithBaseParamsAESStringEncrypt;
		
		Map<String, Object> params	= null;
		
		logger.debug(BaseConstants.BEGINS);
		
		if ( context == null )
		{
			context = new ShellContext();
		}
		
		params 						= new HashMap<String, Object>();

		params.put(CommandConstants.COMMAND_OPTION, CryptoCommandOption.AES_STRING_ENCRYPT);		
		params.put(CommandConstants.STRING_IN, 		BaseTestConstants.TEST_FILE_CONTENTS_TEXT);		
		params.put(CommandConstants.PASSWORD, 		BaseTestConstants.GOOD_PASSWORD);
		params.put(CommandConstants.KEY1, 			BaseTestConstants.GOOD_KEY1);		
		
		context.setParams( params );
		
		logger.debug(BaseConstants.ENDS);		
		
		return( context );		
	}
		
	public static String[] loadArgsWithBaseParams()
	{
		Log logger 					= methIDloadArgsWithBaseParams;
		String[] baseArray			= 
		{
				CryptoCommandOption.DES_FILE_ENCRYPT.toString(),				
				BaseTestConstants.TEST_FILE_NAME_GOOD,
				BaseTestConstants.GOOD_PASSWORD
		};

		logger.debug(BaseConstants.BEGINS);
	
		logger.debug(BaseConstants.ENDS);		
		
		return( baseArray );		
	}
	
	public static void dumpContext(ShellContext context)
	{
		Log logger 					= methIDdumpContext;
		Map<String, Object> params	= null;
		String key					= null;
		String value				= null;
		Set propsSettings			= null;
		Iterator itr				= null;		
	
		
		logger.debug(BaseConstants.BEGINS);
		
		Assert.assertNotNull( context );
		
		params = context.getParams();
		
		Assert.assertNotNull( params );		
		
		propsSettings 	= params.keySet();			
		itr 			= propsSettings.iterator();
		
		logger.debug("Dumping Context....");
		
		while( itr.hasNext() ) 
		{
			key  		= (String) itr.next();
			value 		= (String) params.get( key ).toString();
			
			logger.debug("Located Key: " + key + " | Value: " +  value);
				
		}
		
		
		logger.debug(BaseConstants.ENDS);
		
		return;
		
	}		
	
	
}
