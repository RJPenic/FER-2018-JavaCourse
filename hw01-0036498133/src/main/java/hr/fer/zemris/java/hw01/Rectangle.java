package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Glavna metoda u kojoj korisnik unosi vrijednosti visine i širine pravokutnika pomoću
	 * naredbenog retka ili metode rectangleSideInput te koja ispisuje površinu i
	 * opseg odgovarajućeg pravokutnika
	 * 
	 * @param args
	 *            argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		double width = 0;
		double height = 0;

		if (args.length == 0) {
			Scanner sc = new Scanner(System.in);

			width = rectangleSideInput(true, sc);
			height = rectangleSideInput(false, sc);

			sc.close();
		} else if (args.length == 2) {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);

				if (width < 0 || height < 0) {
					System.out.println("Visina i širina ne smiju biti negativni brojevi.");
					System.exit(1);
				}
			} catch (NumberFormatException ex) {
				System.out.println("U naredbenom retku su zadani neispravni argumenti.");
				System.exit(1);
			}
		} else {
			System.out.println("Zadan neispravan broj argumenata u naredbenom retku.");
			System.exit(1);
		}

		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + (width * height)
				+ " i opseg " + (2 * width + 2 * height) + ".");
	}

	/**
	 * Metoda kojom korisnik unosi željenu vrijednost duljine širine ili visine
	 * pravokutnika
	 * 
	 * @param isItWidth
	 *            određuje hoće li se ispisivati "Unesite širinu >"(true) ili
	 *            "Unesite visinu >"(false)
	 * @param sc
	 *            varijabla tipa Scanner pomoću koje korisnik unosi podatke
	 * @return 
	 * 			  vraća unesenu vrijednost
	 */
	public static double rectangleSideInput(boolean isItWidth, Scanner sc) {
		
		while (true) {
			System.out.print("Unesite " + (isItWidth ? "širinu" : "visinu") + " > ");
			String tempString = sc.nextLine();

			try {
				double x = Double.parseDouble(tempString);

				if (x < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else {
					return x;
				}
			} catch (NumberFormatException ex) {
				System.out.println("\'" + tempString + "\'" + " se ne može protumačiti kao broj.");
			}
		}
	}

}
