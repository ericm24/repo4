/**
*/
package com.eric.shell.domain.constant;


public class BaseTestConstants     
{
	public static final String BAD_PASSWORD_TOO_SHORT 							= "foo";
	public static final String GOOD_PASSWORD 									= "mySuper7Secr3T8&";
	public static final String GOOD_KEY1										= "527653d4-2319-455d-5ca6-f97e10feb6348447d9d9e6-110e-4167-b5c8-c4a3d1d0ca28";
	
	public static final String TEST_FILE_NAME_GOOD 								= "TargetEncryptFile.txt";
	public static final String TEST_FILE_NAME_MISSING 							= "TargetEncryptFileMIA.txt";	
	public static final String TEST_CONFIG_FILE_CORRUPT							= "shell-project-common-config-CORRUPT.xml";
	public static final String TEST_CONFIG_FILE_GOOD							= "shell-project-common-config.json";
	public static final String TEST_CONFIG_FILE_MISSING_KEY1					= "shell-project-common-config-MISSING-KEY1.json";
	public static final String TEST_CONFIG_FILE_MISSING_STRING_ENVIRO_VAR		= "shell-project-common-config-MISSING-STRING-ENVIRO-VAR.json";
	
	public static final String TEST_FILE_NAME_INVALIDPATH 						= "TargetEncryptFile.txt";
	public static final String TEST_FILE_CONTENTS_TEXT 							= "One Big Hairy Fat Cow...DIED.";
	public static final String EXCEPTION_BAD_PADDING 							= "javax.crypto.BadPaddingException";
	public static final String EXCEPTION_FILE_NOT_FOUND 						= "java.io.FileNotFoundException";	 
	public static final String EXCEPTION_SAX_PARSING							= "org.xml.sax.SAXException";

	public static final String EXCEPTION_ILLEGAL_BLOCK_SIZE 					= "javax.crypto.IllegalBlockSizeException";	
	public static final String EXCEPTION_PARSE									= "org.json.simple.parser.ParseException";

	public static final String PROPERTY_FILE_NOT_FOUND_ERR_MSG					= "Property File Not Found!!!";
	public static final String UNABLE_TO_PARSE_PROPERTIES_FILE_ERR_MSG			= "Unable to parse properties file";
	public static final String PASSWORD_IS_TOO_SHORT							= "Password is TOO-SHORT.  Minimum size: 8";	
	public static final String MISSING_FILE_BY_NAME 							= "Missing Config File: TargetEncryptFileMIA.txt";
	public static final String MISSING_KEY_KEY1									= "Missing Key: key.1";
	public static final String MISSING_KEY_STRING_ENVIRO_VAR					= "Missing Key: string.enviro.var";
	
	
	public static final byte[] DES_TEST_SALT =
	{	 			0x2C, 
					0x6B, 
			(byte)	0x93,
					0x10,
					0x19,
			(byte)	0xBB,
			(byte)	0x81,
					0x52		
	};
	
	// TODO: FIX ME!!!
	public static final byte[] AES_TEST_SALT =
	{	 			0x2C, 
					0x6B, 
			(byte)	0x93,
					0x10,
					0x19,
			(byte)	0xBB,
			(byte)	0x81,
					0x52		
	};	
	
	public static final String BLOCK_ERR_MSG =	
		"*** javax.crypto.IllegalBlockSizeException Encountered! Message: Input length (with padding) not multiple of 8 bytes";


	public static final String PADDING_ERR_MSG =		
		"*** javax.crypto.BadPaddingException Encountered! Message: Given final block not properly padded";

	public static final String FILE_MISSING_ERR_MSG =		
		"*** java.io.FileNotFoundException Encountered! Message: TargetEncryptFileMIA.txt (The system cannot find the file specified";


	public static final String UTR_DATE_FORMAT 									= "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String LEFT_PAREN 										= "(";
	public static final String RIGHT_PAREN 										= ")";
	
	public static final String USERNAME 										= "USERNAME";
	public static final String CLASS 											= ".class";
	public static final String INITIALIZATION_ERROR 							= "initializationError";
	public static final String ERROR_OPTION 									= "Error Option: ";
	
}
