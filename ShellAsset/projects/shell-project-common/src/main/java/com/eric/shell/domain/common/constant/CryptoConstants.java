/**
 */
package com.eric.shell.domain.common.constant;

public final class CryptoConstants
{

	// Need: BouncyCastle "PBEWITHSHA256AND128BITAES-CBC-BC"
	
	public final static String 	PBEWITHMD5ANDDES 			= "PBEWithMD5AndDES";
	public final static String  PBKDF2WithHmacSHA1			= "PBKDF2WithHmacSHA1";
	public final static String  AES_CBC_PKCS5PADDING		= "AES/CBC/PKCS5Padding";
	
	public final static String 	AES							= "AES";
	public final static String  DES							= "DES";
	
	public final static int 	DES_SALT_SIZE				= 8;
	public final static int		AES_SALT_SIZE				= 8; //256;
	public final static int		MIN_PASSWORD_LEN			= 8;
	public final static int		MAX_KEY_LEN					= 128;
	public final static String	TEMP_SUFFIX					= ".tmp";
	public final static int		DES_ITERATIONS				= 100;
	public final static int	    AES_ITERATIONS				= 65536;
	public final static String  CONFIG_FILENAME				= "shell-project-common-config.json";

	// Hide This!
	private CryptoConstants()
	{		
	}
	
}
