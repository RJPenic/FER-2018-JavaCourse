package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 */

public class Factorial {

	/**
	 * Glavna metoda u kojoj korisnik unosi brojeve čije faktorijele želi
	 * izračunati, a izračunavanje faktorijela se vrši pomoću metode
	 * calculateFactorial(x)
	 * 
	 * @param args
	 *            argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String tempString = sc.nextLine();

			if (tempString.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int number = Integer.parseInt(tempString);

				if (number < 1 || number > 20) {
					System.out.println("\'" + number + "\'" + " nije broj u dozvoljenom rasponu.");
				} else {
					System.out.println(number + "!" + " = " + calculateFactorial(number));
				}
			} catch (NumberFormatException ex) {
				System.out.println("\'" + tempString + "\'" + " nije cijeli broj.");
				continue;
			}
		}

		sc.close();

	}

	/**
	 * Metoda koja računa faktorijel zadanog broja pomoću rekurzivne relacije x! = x
	 * * (x-1)! (poznato je da je 0! = 1)
	 * 
	 * @param x
	 *            broj čiji faktorijel izračunavamo
	 * @return 
	 * 			  vraća vrijednost izraza x!
	 * @throws IllegalArgumentException
	 * 			  ako se kao argument da broj izvan intervala [0, 20]
	 */
	public static long calculateFactorial(int x) {
		if(x < 0 || x > 20) {
			throw new IllegalArgumentException();
		}
		
		if (x < 1) {
			return 1;
		}
		
		return x * calculateFactorial(x - 1);
	}
}
