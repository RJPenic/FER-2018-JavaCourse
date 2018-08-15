package hr.fer.zemris.java.cryptoUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Util class offering useful crypto algorithms and methods
 * 
 * @author Rafael Josip Penić
 *
 */
public class CryptoUtil {

	/**
	 * Method used for converting byte array into string representation of
	 * corresponding hexadecimal number
	 * 
	 * @param byteArray
	 *            array of bytes to be converted
	 * @return hexadecimal string
	 */
	public static String byteToHex(byte[] byteArray) {
		Objects.requireNonNull(byteArray, "Given array cannot be null.");

		StringBuilder sb = new StringBuilder();

		for (byte tempByte : byteArray) {
			sb.append(String.format("%02x", tempByte));
		}

		return sb.toString();
	}

	/**
	 * Hashes password using SHA-1 algorithm and returns hexadecimal string
	 * representation of the result
	 * 
	 * @param password
	 *            user's password
	 * @return hashed password
	 */
	public static String hashPassword(String password) {
		byte[] hashPassword;

		try {
			hashPassword = MessageDigest.getInstance("SHA-1").digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Given crypto algorithm does not exist");
		}

		return byteToHex(hashPassword);
	}
}
