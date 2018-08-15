package hr.fer.zemris.java.hw07.crypto;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Utility class which offers methods for converting byte array to hexadecimal
 * numbers and vice versa.
 * 
 * @author Rafael Josip Penić
 *
 */
public class Util {

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
	 * Converts given hexadecimal string into corresponding byte array
	 * 
	 * @param keyText
	 *            hexadecimal string to be converted
	 * @return corresponding byte array
	 */
	public static byte[] hexToByte(String keyText) {
		Objects.requireNonNull(keyText, "Given string is not allowed to be null.");

		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Given string has odd number of characters.");
		}

		byte[] resultArray = new byte[keyText.length() / 2];

		for (int i = 0; i < resultArray.length; i++) {
			try {
				resultArray[i] = new BigInteger(keyText.substring(2 * i, 2 * i + 2), 16).byteValue();
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Given string is not valid.");
			}
		}

		return resultArray;
	}
}
