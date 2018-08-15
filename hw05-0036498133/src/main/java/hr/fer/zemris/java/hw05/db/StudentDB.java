package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import hr.fer.zemris.java.hw05.db.QueryFilter.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

/**
 * Class that realizes a simple database emulator
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class StudentDB {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             in case of an error while reading from file
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(				// Loading database from the file
				 Paths.get("./src/main/resources/database.txt"),
				 StandardCharsets.UTF_8
				);
		
		StudentDatabase database = null;
		
		try {
			database = new StudentDatabase(lines);
		} catch(IllegalArgumentException ex) {
			System.err.println("There is an error in the given database.\n" + ex.getMessage() +"\nTerminating the program.");
			System.exit(1);
		}
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			
			String tempString = sc.nextLine();
			
			if(tempString.toLowerCase().trim().equals("exit")) {	// if the last entry is "exit"(case non-sensitive to make the program more robust)
				System.out.println("Goodbye!");						// loop breaks and with that the program ends(Scanner will be closed)
				break;
			}
			
			if(!(tempString.trim().startsWith("query ") || tempString.trim().equals("query"))) {		// if the entry doesn't start "query" that means that the entry
				System.out.println("Command is not recognized. Please check if your entry is valid.");	// is not valid. Trimming is necessary because of case like
				continue;																				// "      query...". We also need to look at case when entry only
			}																							// consists of "query"(with or without spaces before it and after).
																										// That case is covered by questioning equality of the entry and "query"(that means there are no conditions)
			
			tempString = tempString.substring(tempString.indexOf("query") + 5);			// with this, we basically remove the first part of the entered string(part with the word query)
																						// because of QueryParser which works directly with inquiry and not with the query command itself
			
			QueryParser qp = new QueryParser(tempString);
			
			List<StudentRecord> recordsList;
			
			try {
				if(qp.isDirectQuery()) {											// if the inquiry is direct, we will use faster method
					System.out.println("Using index for record retrieval.");		// for getting the searched student record(get on index)
					StudentRecord record = database.forJMBAG(qp.getQueriedJMBAG());
					
					recordsList = new ArrayList<>();
					
					if(record != null) {			// if the record is null that means that there are no such elements in the index, so there is no point
						recordsList.add(record);	// in adding it into the list.
					}
				} else {
					recordsList = database.filter(new QueryFilter(qp.getQuery()));	// filters the database with the entered conditions and returns list with
				}																	// records that fulfill those conditions
			} catch (IllegalArgumentException ex) {	// if the exception is thrown that means that the entry is invalid(e.g. 2 wildcard symbols)
				System.out.println("Invalid entry! Please enter again.");
				continue;
			}
			
			if(recordsList.size() != 0) {	//there is no point in printing the empty list
				printTable(recordsList);
			}
			
			System.out.println("Records selected: " + recordsList.size() + "\n");
		}
		
		sc.close();
	}
	
	/**
	 * Method that calculates length of the longest name(first or last) from the
	 * given record list
	 * 
	 * @param recordList
	 *            list which contains student records which names will be taken in
	 *            consideration
	 * @param func
	 *            function which determines which string from the records will be
	 *            taken(first name or last name)
	 * @return length of the longest name
	 */
	private static int calculateLongestName(List<StudentRecord> recordList, Function<StudentRecord, Integer> func) {
		int max = 0;
		
		for(StudentRecord rec : recordList) {
			if(max < func.apply(rec)) {
				max = func.apply(rec);
			}
		}
		
		return max;
	}
	
	/**
	 * Generates string like "==...==" with the length equal to given number
	 * incremented by two
	 * 
	 * @param numberOfEqualCharacters
	 *            determines the length of the resulting string
	 * @return string that consists only from '=' characters
	 */
	private static String generateEquals(int numberOfEqualCharacters) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < numberOfEqualCharacters + 2; i++) {
			sb.append("=");
		}
		
		return sb.toString();
	}
	
	/**
	 * Method that prints the header
	 * 
	 * @param longestFirstName
	 *            length of the longest filtered first name
	 * @param longestLastName
	 *            length of the longest filtered last name
	 */
	private static void printHeader(int longestFirstName, int longestLastName) {
		String firstNameEquals = generateEquals(longestFirstName);
		String lastNameEquals = generateEquals(longestLastName);
		
		System.out.println("+============+" + lastNameEquals + "+" + 
							firstNameEquals + "+===+");	
	}
	
	/**
	 * Method that prints table with student records from the given list
	 * 
	 * @param recordsList
	 *            list that contains student records that will be printed
	 */
	private static void printTable(List<StudentRecord> recordsList) {
		int longestLastName = calculateLongestName(recordsList, record -> record.getLastName().length());
		int longestFirstName = calculateLongestName(recordsList, record -> record.getFirstName().length());
		
		printHeader(longestLastName, longestFirstName);
		
		for(StudentRecord tempRecord : recordsList) {
			String format = "| %-10s | %-" + longestLastName + "s | %-" +	// '-' is used in format to get prints aligned to left
								longestFirstName + "s | %-1d |\n";
			
			System.out.printf(format, tempRecord.getJmbag(),
										tempRecord.getLastName(),
										tempRecord.getFirstName(),
										tempRecord.getGrade());
		}
		
		printHeader(longestLastName, longestFirstName);
	}
}
