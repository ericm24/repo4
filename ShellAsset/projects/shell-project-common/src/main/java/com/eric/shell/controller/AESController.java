/**
 *  
 * 
 * @author eric
 * 
 */
package com.eric.shell.controller;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class AESController 
{

	public static void main(String[] args) throws Exception 
	{

		//runAES128();		
		
		runAES256();		
		
		return;
	}
	
	private static void runAES128() throws Exception
	{
		
		Cipher cipher;
		
		KeyGenerator keyGeneratorAES = KeyGenerator.getInstance("AES");		
		
 	    // Generate the secret key specs.
		String keyText = "ABC";

		keyGeneratorAES.init(128);
	
		SecretKey secretKey128 = keyGeneratorAES.generateKey();
		
		cipher = Cipher.getInstance("AES");
		
		String plainText = "AES Symmetric Encryption Decryption";
		System.out.println("Plain Text Before Encryption: " + plainText);

		String encryptedText = encrypt128(cipher, plainText, secretKey128);
		
		System.out.println("Encrypted Text After Encryption: " + encryptedText);

		String decryptedText = decrypt128(cipher, encryptedText, secretKey128);
		
		System.out.println("Decrypted Text After Decryption: " + decryptedText);
		
		return;
	}

	
	private static void runAES256() throws Exception
	{		
		Cipher AESCipher1;
		Cipher AESCipher2;
		
		KeyGenerator keyGeneratorAES 	= KeyGenerator.getInstance("AES");	
		SecureRandom secureRandom 		= null;
		//SecureRandom initialVector 		= null;
		IvParameterSpec initialVector	= null;
		
		SecretKey secretKey2 			= null;
		Key key				 			= null;
		
		
	    // Doesn't work
		// KeyGenerator keyGenerator = KeyGenerator.getInstance("AES/CTR/PKCS5PADDING");		

	    // Must be: 128, 192, or 256
		keyGeneratorAES.init(256);
		
		//keyGeneratorAES.init(128);		
	
		secretKey2 = keyGeneratorAES.generateKey();
		
	    //SecretKeySpec secretKeySpec256 = new SecretKeySpec(secretKey2.getEncoded(), "AES");
		
		key = loadKey();
		
	    //SecretKeySpec secretKeySpec256 = new SecretKeySpec(key.getEncoded(), "AES");		
	    //SecretKeySpec secretKeySpec256 = new SecretKeySpec(secretKey2.getEncoded(), "AES");		
	    
	    secureRandom = SecureRandom.getInstanceStrong();
		
		
		
		//cipher = Cipher.getInstance("AES");
		
		// Doesn't work
		// cipher = Cipher.getInstance("AES/CTR/PKCS5PADDINGs");
		
		// Works!!!
		AESCipher1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");		

		
		//initialVector = AESCipher1.getIV();		
		
		
		String plainText = "AES Symmetric Encryption Decryption";
		System.out.println("    Plain Text Before Encryption: " + plainText);


		
		//String encryptedText = encryptAES256(cipher1, plainText, secretKey2, initialVector);
		//String encryptedText = encryptAES256(AESCipher1, plainText, secretKeySpec256, initialVector);		
		//String encryptedText = encryptAES256(AESCipher1, plainText, secretKeySpec256, secureRandom);
		
		// WORKS!!!  Key from KeyStore
		//String encryptedText = encryptAES256(AESCipher1, plainText, key, secureRandom);
		
		// RandomKey
		String encryptedText = encryptAES256(AESCipher1, plainText, secretKey2, secureRandom);		
		
		System.out.println(" Encrypted Text After Encryption: " + encryptedText);

		// Here Dude Crashes Here....
		initialVector = new IvParameterSpec(AESCipher1.getIV());		
		
		//String encryptedText = encrypt(plainText, secretKeySpec);
		

		//String decryptedText = decryptAES256(cipher2, encryptedText, secretKey2, initialVector);		
		//String decryptedText = decryptAES256(AESCipher1, encryptedText, secretKeySpec256, initialVector, secureRandom);
		
		// WORKS!!! Key from KeyStore
		//String decryptedText = decryptAES256(AESCipher1, encryptedText, key, initialVector, secureRandom);
		
		// RandomKey
		String decryptedText = decryptAES256(AESCipher1, encryptedText, secretKey2, initialVector, secureRandom);		

		//sString decryptedText = decrypt(encryptedText, secretKeySpec);		
		
		System.out.println(" Decrypted Text After Decryption: " + decryptedText);
		
		return;
	}
	
	
	private static String encrypt128(Cipher cipher, String plainText, SecretKey secretKey) throws Exception 
	{
		//byte[] plainTextByte = plainText.getBytes(StandardCharsets.UTF_8);
		
		byte[] plainTextByte = plainText.getBytes();		
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		
		return( encryptedText );
	}

	private static String decrypt128(Cipher cipher, String encryptedText, SecretKey secretKey) throws Exception 
	{
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return( decryptedText );
	}
	
	
	//private static String encryptAES256(Cipher cipher, String plainText, SecretKey secretKey, SecureRandom initialVector)
	//private static String encryptAES256(Cipher cipher, String plainText, SecretKeySpec secretKeySpec, SecureRandom initialVector)
	//private static String encryptAES256(Cipher cipher, String plainText, SecretKeySpec secretKeySpec, SecureRandom secureRandom)
	private static String encryptAES256(Cipher cipher, String plainText, Key secretKey, SecureRandom secureRandom)	
	{
		
		//byte[] plainTextByte = plainText.getBytes();				
		byte[] plainTextByte = plainText.getBytes(StandardCharsets.UTF_8);
		
		String encryptedText = null;
		
		try
		{
			
			
			// Here Dude!!! Crashes on a 256 type key that is 64 long...Hmmm...		
			// 128 Init Below:			
			// cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			// AES Mode:
			//cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, initialVector);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);			
			
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedText = encoder.encodeToString(encryptedByte);
		}
		catch (Exception ex)
		{
			System.out.println("*** Exception encountered: " + ex.getMessage());
		}
		
		return( encryptedText );
	}

	//private static String decryptAES256(Cipher cipher, String encryptedText, SecretKey secretKey, SecureRandom initialVector)
	//private static String decryptAES256(Cipher cipher, String encryptedText, SecretKeySpec secretKeySpec, SecureRandom initialVector)	
	//private static String decryptAES256(Cipher cipher, String encryptedText, SecretKeySpec secretKeySpec, IvParameterSpec initialVector, SecureRandom secureRandom)
	
	private static String decryptAES256(Cipher cipher, String encryptedText, Key secretKey, IvParameterSpec initialVector, SecureRandom secureRandom)	
	{
		Base64.Decoder decoder = Base64.getDecoder();
		String decryptedText = null;	
		try
		{
			
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");			
			
			// Here dude!  Bad params...wants SecretKeySpec?
			//cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, initialVector);			
			//cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, initialVector, secureRandom);
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey, initialVector, secureRandom);			
			
			byte[] encryptedTextByte = decoder.decode(encryptedText);
			
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			
			decryptedText = new String(decryptedByte);
		}
		catch (Exception ex)
		{
			System.out.println("*** Exception encountered: " + ex.getMessage());
		}
		
		return( decryptedText );
	}	
	
	
	private static String genSHA256(String targetString) throws Exception
    {
    	//String password = "123456";
    	
    	String returnValue = null;
    	
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //md.update(password.getBytes());
        
        md.update(targetString.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < byteData.length; i++) 
        {        	
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));        	
        }
     
        System.out.println("Size: " + sb.length() + " Hex format : " + sb.toString());
    	returnValue = sb.toString();        
        
        //convert the byte to hex format method 2
        
        StringBuffer hexString = new StringBuffer();
        
    	for (int i=0;i<byteData.length;i++) 
    	{
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
        //System.out.println("Size: " + sb.length() + " Hex format : " + sb.toString());
    	
    	//returnValue = hexString.toString();
    	
    	return( returnValue );
    }
	
	private static Key loadKey()
	{		
		String keyStoreLocation 	= "aes-keystore.jck";
		String keyStorePass 		= "mystorepass";
		String alias				= "jceksaes";
		String keyPass				= "mykeypass";
		Key key						= null;		
		InputStream keystoreStream 	= null;		
		KeyStore keystore			= null;		
		
		try
		{
			// for clarity, ignoring exceptions and failures  
			//InputStream keystoreStream = new FileInputStream(keyStoreLocation);  
			
			//InputStream keystoreStream = getClass().getClassLoader().getResourceAsStream( keyStoreLocation );

			keystoreStream = AESController.class.getClassLoader().getResourceAsStream( keyStoreLocation );			
			  
			keystore = KeyStore.getInstance("JCEKS");  
			keystore.load(keystoreStream, keyStorePass.toCharArray());  
			  
			if ( !keystore.containsAlias( alias )) 
			{  
			    throw new RuntimeException("Alias for key not found");  
			}  
			  
			key = keystore.getKey(alias, keyPass.toCharArray());  
		}
		catch (Exception ex)
		{
			System.out.println("*** Exception Encountered: " + ex.getMessage());
		}
		
		return( key );		
	}
	
	
}

