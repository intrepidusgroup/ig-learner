package com.intrepidusgroup.learner;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;


// This class is included to help out coding the encryption / decryption routine in case the challenger can't figure it out on their own.
public class AESHelper {
	public static String encryptNumberWithAES(String number) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// This method will return a base64 encoded value of the AES-encrypted string

		byte[] numberToEncryptBytes = number.getBytes();
		
		String keyString = "intrepidlearner1"; // The key is exactly 16 bytes long
		byte[] key = keyString.getBytes();
		
		
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		c.init(Cipher.ENCRYPT_MODE, keySpec);
		
		byte[] encryptedData = c.doFinal(numberToEncryptBytes);
		
		return android.util.Base64.encodeToString(encryptedData, Base64.DEFAULT);
		
	}
	
	public static String decryptNumberWithAES(String encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedBytes = Base64.decode(encrypted, Base64.DEFAULT);
		
		String keyString = "intrepidlearner1"; // The key is exactly 16 bytes long
		byte[] key = keyString.getBytes();
		
		
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		c.init(Cipher.DECRYPT_MODE, keySpec);
		
		byte[] decryptedData = c.doFinal(encryptedBytes);
		
		return new String(decryptedData);
		
	}
	
}
