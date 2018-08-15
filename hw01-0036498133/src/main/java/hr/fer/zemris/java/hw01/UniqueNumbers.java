package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * Glavna metoda koja se poziva pri pokretanju programa. Korisnik unosi brojeve
	 * koje želi dodati u stablo te se na kraju sadržaj stabla ispisuje.
	 * 
	 * @param args
	 *            argumenti iz naredbenog retka
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;

		while (true) {
			System.out.print("Unesite broj > ");
			String tempLine = sc.nextLine();

			if (tempLine.equals("kraj")) {
				break;
			}

			try {
				int num = Integer.parseInt(tempLine);

				if (containsValue(glava, num)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, num);
					System.out.println("Dodano.");
				}

			} catch (NumberFormatException ex) {
				System.out.println("\'" + tempLine + "\'" + " nije cijeli broj.");
			}
		}

		sc.close();

		System.out.print("Ispis od najmanjeg: " + sorted(glava));
		System.out.println();

		System.out.print("Ispis od najvećeg: " + reversedSorted(glava));
		System.out.println();
	}
	
	/**
	 * 
	 * Pomoćna klasa kojom definiramo čvor stabla
	 *
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Metoda kojom dodajemo zadani broj u stablo
	 * 
	 * @param head
	 *            glava stabla u kojeg dodajemo zadani element
	 * @param number
	 *            broj koji dodajemo u stablo
	 * @return 
	 * 			  glava stabla u kojeg smo dodali broj
	 */
	public static TreeNode addNode(TreeNode head, int number) {

		if (head == null) {
			head = new TreeNode();
			head.value = number;
			return head;
		}

		if (head.value == number) {
			return head;
		}

		if (number < head.value) {
			if (head.left == null) {
				head.left = new TreeNode();
				head.left.value = number;
			} else {
				head.left = addNode(head.left, number);
			}
		} else {
			if (head.right == null) {
				head.right = new TreeNode();
				head.right.value = number;
			} else {
				head.right = addNode(head.right, number);
			}
		}

		return head;
	}

	/**
	 * Metoda koja izračunava koliko elemenata sadrži zadano stablo
	 * 
	 * @param head
	 *            glava stabla čiju veličinu računamo
	 * @return 
	 * 			  vraća veličinu stabla
	 */
	public static int treeSize(TreeNode head) {
		if (head == null)
			return 0;
		return 1 + treeSize(head.left) + treeSize(head.right);
	}

	/**
	 * Metoda koja provjerava je li se u stablu nalazi određeni broj
	 * 
	 * @param head
	 *            glava stabla kojemu provjeravamo je li sadržava određeni broj
	 * @param number
	 *            broj čiju prisutnost tražimo
	 * @return 
	 * 			  true ako stablo sadrži traženi broj, a false ako ne sadrži
	 */
	public static boolean containsValue(TreeNode head, int number) {
		if (head == null)
			return false;
		if (head.value == number)
			return true;
		return containsValue(head.left, number) || containsValue(head.right, number);
	}

	/**
	 * Metoda koja stvara string u kojem su od, najmanjeg prema najvećem, popisani
	 * brojevi u stablu
	 * 
	 * @param head
	 *            glava stabla čije elemente ispisujemo
	 * @return 
	 * 			  string uzlazno poredanih elemenata stabla
	 */
	public static String sorted(TreeNode head) {
		if (head == null)
			return "";

		String temp = "";
		temp += sorted(head.left);
		temp += head.value + " ";
		temp += sorted(head.right);

		return temp;
	}

	/**
	 * Metoda koja stvara string u kojem su od, najvećeg prema najmanjem, popisani
	 * brojevi u stablu
	 * 
	 * @param head
	 *            glava stabla čije elemente ispisujemo
	 * @return 
	 * 			  string silazno poredanih elemenata stabla
	 */
	public static String reversedSorted(TreeNode head) {
		if (head == null)
			return "";

		String temp = "";
		temp += reversedSorted(head.right);
		temp += head.value + " ";
		temp += reversedSorted(head.left);

		return temp;
	}
}
