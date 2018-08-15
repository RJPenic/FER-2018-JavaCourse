package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that represents a program that allows the user to encrypt/decrypt given
 * file using the AES cryptoalgorithm and the 128-bit encryption key or
 * calculate and check the SHA-256 file digest.
 * 
 * @author Rafael Josip Penić
 *
 */
public class Crypto {
	private static final String ALGORITHM = "SHA-256";

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Invalid number of arguments. Terminating the program.");
			System.exit(1);
		}

		Scanner sc = new Scanner(System.in);

		InputStream is = null;
		try {
			Path origin = Paths.get("./src/main/resources/" + args[1]);
			is = new BufferedInputStream(Files.newInputStream(origin, StandardOpenOption.READ));
		} catch (NoSuchFileException ex) {
			System.out.println("Entered origin file does not exist.");
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("Error while opening the file.");
			System.exit(1);
		}

		switch (args[0].toLowerCase()) {
		case ("checksha"):
			try {
				shaDigestChecker(sc, args, is);
			} catch (IOException | NoSuchAlgorithmException ex) {
				System.out.println("Error while calculating digest. " + ex.getMessage());
				System.exit(1);
			}

			break;

		case ("encrypt"):

		case ("decrypt"):
			try {
				encryptOrDecrypt(args[0].toLowerCase().equals("encrypt"), sc, args, is);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				System.out.println("Error encountered when decrypting. " + e.getMessage());
				System.exit(1);
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.exit(1);
			} catch (IOException ex) {
				System.out.println("Error while reading from file.");
				System.exit(1);
			}

			break;

		default:
			System.out.println("\"" + args[0] + "\" is not a valid command.");
			System.exit(1);
		}

		sc.close();
		try {
			is.close();
		} catch (IOException ex) {
			System.out.println("Error while closing stream.");
			System.exit(1);
		}

	}

	/**
	 * Method that checks if the digest that user entered matches the real digest
	 * and prints appropriate message
	 * 
	 * @param sc
	 *            scanner which allows user entries via console
	 * @param args
	 *            command line arguments that are given in the main method
	 * @param is
	 *            input stream of a file which digest is checked
	 * @throws IOException
	 *             in case of an error while reading from a file
	 * @throws NoSuchAlgorithmException
	 *             in case the createAndUpdateMessageDigest method throws it
	 */
	private static void shaDigestChecker(Scanner sc, String[] args, InputStream is)
			throws IOException, NoSuchAlgorithmException {
		if (args.length != 2) {
			throw new IllegalArgumentException("\"checksha\" command requires only one argument.");
		}

		System.out.println("Please provide expected sha-256 digest for " + args[1] + ": ");
		System.out.print("> ");
		String tempLine = sc.nextLine();

		MessageDigest md = null;
		try {
			md = createAndUpdateMessageDigest(is);
		} catch (IOException ex) {
			throw new IOException("Error while reading from the file!");
		}

		byte[] tempByteArray = md.digest();
		String hexadecimalString = Util.byteToHex(tempByteArray);
		System.out.print("Digesting completed. ");

		if (tempLine.equals(hexadecimalString)) {
			System.out.println("Digest of " + args[1] + " matches expected digest.");
		} else {
			System.out.println("Digest of " + args[1] + " does not match the expected digest.");
			System.out.println("Digest was: " + hexadecimalString);
		}
	}

	/**
	 * Method that is used for encrypting and decrypting
	 * 
	 * @param encrypt
	 *            boolean value that determines if the encryption or decryption is
	 *            necessary
	 * @param sc
	 *            Scanner reference that allows user to enter wanted values when
	 *            needed
	 * @param args
	 *            command line arguments that are given in the main method
	 * @param is
	 *            input stream for the specific file that will be encrypted(or
	 *            decrypted)
	 * @throws NoSuchAlgorithmException
	 *             in case the algorithm that is given when creating cipher does not
	 *             exist
	 * @throws NoSuchPaddingException
	 *             when padding is not valid
	 * @throws InvalidKeyException
	 *             in case the given cipher key is not valid
	 * @throws IOException
	 *             in case of an error while reading(or writing) from the file
	 * @throws BadPaddingException
	 *             in case of bad padding
	 */
	private static void encryptOrDecrypt(boolean encrypt, Scanner sc, String[] args, InputStream is)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		if (args.length != 3) {
			System.out.println(encrypt ? "\"encrypt\"" : "\"decrypt\"" + " command requires 2 arguments.");
			System.exit(1);
		}

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");

		String keyText = sc.next();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");

		String ivText = sc.next();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		Path target = Paths.get("./src/main/resources/" + args[2]);
		OutputStream os = new BufferedOutputStream(
				Files.newOutputStream(target, StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		byte[] buffer = new byte[1024];
		while (true) {
			int r = is.read(buffer);
			if (r < 1)
				break;
			os.write(cipher.update(buffer, 0, r));
		}

		os.close();

		System.out.println((encrypt ? "Encryption" : "Decryption") + " completed. Generated file " + args[2]
				+ " based on file " + args[1]);
	}

	/**
	 * Method that creates a message digest for a given file.
	 * 
	 * @param is
	 *            InputStream to the file that is digested
	 * @return resulting message digest
	 * @throws IOException
	 *             when error while reading from the given file
	 * @throws NoSuchAlgorithmException
	 *             when the given algorithm in the getInstance method is not valid
	 */
	private static MessageDigest createAndUpdateMessageDigest(InputStream is)
			throws IOException, NoSuchAlgorithmException {
		MessageDigest md;

		md = MessageDigest.getInstance(ALGORITHM);

		byte[] buffer = new byte[1024];
		while (true) {
			int r = is.read(buffer);
			if (r < 1)
				break;
			md.update(buffer, 0, r);
		}

		return md;
	}
}
