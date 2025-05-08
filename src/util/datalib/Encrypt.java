package util.datalib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encrypt {
	private static MessageDigest digest;
	private static final String ENCRYPTION_ALGORITHM = "PBEWithMD5AndDES";
	private static PBEKeySpec password = null;
    private static final int ITERATION_COUNT = 1000;
    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };
	
	/**
	 * Encrypts an String to SHA-256.
	 * 
	 * @param str the String to encrypt.
	 * @return the String encrypt.
	 * @author regrettabledanger10
	 */
	public final static String getEncryptSHA256(String str) {
		try {
			digest = MessageDigest.getInstance("SHA-256");
			str = Coder.getEncode(str);
			byte[] encodeHash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder(2 * encodeHash.length);
			for (short i = 0; i < encodeHash.length; i++) {
				String hex = Integer.toHexString(0xff & encodeHash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Encrypts an String to MD5.
	 * 
	 * @param str the String to encrypt.
	 * @return the String encrypt.
	 * @author regrettabledanger10
	 */
	public final static String getEncryptMD5(String str) {
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] encodeMD5 = digest.digest(str.getBytes());
			BigInteger no = new BigInteger(1, encodeMD5);
			String hashText = no.toString(16);
			while(hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] encryptData(Object data) {
        try {
        	password = generatePassword();
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM).generateSecret(password), new PBEParameterSpec(SALT, ITERATION_COUNT));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(cipherOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static Object decryptData(byte[] encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM).generateSecret(password), new PBEParameterSpec(SALT, ITERATION_COUNT));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(encryptedData);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(cipherInputStream)) {
				return objectInputStream.readObject();
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static PBEKeySpec generatePassword() {
		return new PBEKeySpec(String.valueOf(Data.generateRandomBytes(1000)).toCharArray());
	}
	
}
