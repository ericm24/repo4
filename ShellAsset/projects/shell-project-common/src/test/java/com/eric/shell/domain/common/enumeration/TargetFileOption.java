/**
 */
package com.eric.shell.domain.common.enumeration; 

/**
 *  Enum of Target File Options for encryption results. 
 * 
 * @author Eric 
 * 
 */

import com.eric.shell.domain.constant.CryptoTestConstants;

public enum TargetFileOption
{
	TEST_FILE_CONTENTS_EMPTY(""),
	
	TEST_FILE_CONTENTS_TEXT("One Big Hairy Fat Cow...DIED."),

	// BE VERY, VERY CAREFULL with this constant.  It contains the encrypted representation
	// of our test-text, which includes Non-Unicode-8 characters, which are denoted by the use
	// of the hexcode (ie escaped slash-u hexcode).   Maven requires adherence to utf-8
	// character set.  Eclipse based editors use cp1252 by default, which allows for a 
	// broader set of characters, and this will compile and run fine in Eclipse, however
	// it will fail in maven, therefore we must convert it to the hex representation to 
	// get it back to utf-8

	// The next 4 Entries below are for DECRYPTING ONLY	
	TEST_FILE_CONTENTS_DES_ENCRYPTED( 		  			"e0H" 	+
														"\u00E2" 	+   
														"\u00C8" 	+ 															  
														"*t-" 	+
														"\u00F3" 	+
														"\u00DC" 	+															  
														"c,:`"   	+
														"\u00E7" 	+
														""		+
														"\u00E7" 	+
														"\u0152" 	+ 
														"\u00FE" 	+															  															  
														"SteqD" 	+
														"\u00DC" 	+
														"\u00B1" 	+
														"9T" 		+
														"\u2026"  +
														"\u00A6" 	+
														"m<" 		+
														"\u00D0" 	+
														"y" 		+
														"\u00AC"  ),									  

			  
	TEST_FILE_CONTENTS_DES_ENCRYPTED_BLOCKSIZE_ERROR( 	"e0H" 	+
												  		"\u00E2" 	+   
														"\u00C8" 	+ 															  
														"*t-" 	+
														"\u00F3" 	+
														"\u00DC" 	+															  
														"c,:`"   	+
														"\u00E7" 	+
														""		+
														"\u00E7" 	+
														"\u0152" 	+ 
														"\u00FE" 	+															  															  
														"SteqD" 	+
														"\u00DC" 	+
														"9T" 		+
														"\u2026"  +
														"\u00A6" 	+
														"m<" 		+
														"\u00D0" 	+
														"y" 		+
														"\u00AC"), 
   
    // Initial Copy..														
	TEST_FILE_CONTENTS_AES_ENCRYPTED( 		  			"e0H" 	+
														"\u00E2" 	+   
														"\u00C8" 	+ 															  
														"*t-" 	+
														"\u00F3" 	+
														"\u00DC" 	+															  
														"c,:`"   	+
														"\u00E7" 	+
														""		+
														"\u00E7" 	+
														"\u0152" 	+ 
														"\u00FE" 	+															  															  
														"SteqD" 	+
														"\u00DC" 	+
														"\u00B1" 	+
														"9T" 		+
														"\u2026"  +
														"\u00A6" 	+
														"m<" 		+
														"\u00D0" 	+
														"y" 		+
														"\u00AC"  ),									  

    // Initial Copy
	TEST_FILE_CONTENTS_AES_ENCRYPTED_BLOCKSIZE_ERROR( 	"e0H" 	+
												  		"\u00E2" 	+   
														"\u00C8" 	+ 															  
														"*t-" 	+
														"\u00F3" 	+
														"\u00DC" 	+															  
														"c,:`"   	+
														"\u00E7" 	+
														""		+
														"\u00E7" 	+
														"\u0152" 	+ 
														"\u00FE" 	+															  															  
														"SteqD" 	+
														"\u00DC" 	+
														"9T" 		+
														"\u2026"  +
														"\u00A6" 	+
														"m<" 		+
														"\u00D0" 	+
														"y" 		+
														"\u00AC"), 
												  
   
   
   
   TEST_FILE_CONTENTS_DES_ENCRYPTED2( CryptoTestConstants.RAW_BYTES_1 );
	
	private TargetFileOption(String newValue)
	{
		this.stringValue 	= newValue;
		this.byteValue 		= null;
	}
	
	private TargetFileOption(byte[] newValue)
	{
		this.stringValue 	= null;
		this.byteValue 		= newValue;
	}
	
	private final String stringValue;
	private final byte[] byteValue;
	
	public String toString() 
	{
		return( stringValue );
	}
	
	public byte[] toBytes()
	{
		return( byteValue );	
	}
	
};


