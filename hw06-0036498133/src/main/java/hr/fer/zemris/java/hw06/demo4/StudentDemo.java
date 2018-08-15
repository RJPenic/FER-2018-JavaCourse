package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demo method that demonstrates how streams and StudentRecord work
 * 
 * @author Rafael Josip Penić
 *
 */
public class StudentDemo {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             in case of an error while trying to read from a file
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"));
		List<StudentRecord> records = convert(lines);

		System.out.println("Number of students with the score of 25 or more: " + vratiBodovaViseOd25(records));

		System.out.println("Number of students with grade 5: " + vratiBrojOdlikasa(records));

		System.out.println("-------------------");

		List<StudentRecord> excellentStudents = vratiListuOdlikasa(records);

		System.out.println("List of student with grade 5: ");
		printList(excellentStudents);

		System.out.println("-------------------");

		List<StudentRecord> excellentStudentsSorted = vratiSortiranuListuOdlikasa(records);

		System.out.println("Sorted(by score) list of students with grade 5: ");
		printList(excellentStudentsSorted);

		System.out.println("-------------------");

		List<String> failedStudents = vratiPopisNepolozenih(records);

		System.out.println("List of students who did not pass: ");
		for (String temp : failedStudents) {
			System.out.println(temp);
		}

		System.out.println("-------------------");

		Map<Integer, List<StudentRecord>> gradesMap = razvrstajStudentePoOcjenama(records);

		System.out.println("Grades map: ");
		gradesMap.forEach((k, v) -> {
			System.out.println("Key " + k + ":");
			printList(v);
		});

		System.out.println("-------------------");

		Map<Integer, Integer> gradesNumberMap = vratiBrojStudenataPoOcjenama(records);

		System.out.println("Number of each grade: ");
		gradesNumberMap.forEach((k, v) -> {
			System.out.println(k + ":" + v);
		});

		System.out.println("-------------------");

		Map<Boolean, List<StudentRecord>> failOrPassMap = razvrstajProlazPad(records);

		System.out.println("Students who failed and students who passed: ");
		failOrPassMap.forEach((k, v) -> {
			System.out.println("Key " + k + ":");
			printList(v);
		});

	}

	/**
	 * Converts given list of strings into StudentRecord list
	 * 
	 * @param lines
	 *            list of strings to be converted
	 * @return list containing corresponding student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> resultList = new ArrayList<>();

		for (String tempString : lines) {
			if (tempString.isEmpty())
				continue;

			String[] tempStringArray = tempString.split("\t+");
			try {
				resultList.add(new StudentRecord(tempStringArray[0], tempStringArray[1], tempStringArray[2],
						Double.parseDouble(tempStringArray[3]), Double.parseDouble(tempStringArray[4]),
						Double.parseDouble(tempStringArray[5]), Integer.parseInt(tempStringArray[6])));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Error while parsing string into number.");
			} catch (IndexOutOfBoundsException ex) {
				throw new IllegalArgumentException("Certain entry doesn't have enough arguments to be constructed.");
			}
		}

		return resultList;
	}

	/**
	 * Method that counts how many students have total score greater than 25
	 * 
	 * @param records
	 *            list of student records that will be filtered
	 * @return number of students with score greater than 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter((record) -> (countScore(record) > 25)).count();
	}

	/**
	 * Method that counts number of students with the grade equal to 5
	 * 
	 * @param records
	 *            list of students that will be filtered
	 * @return number of students with the grade equal to 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(record -> record.getGrade() == 5).count();
	}

	/**
	 * Method that constructs a list containing students with grade equal to 5
	 * 
	 * @param records
	 *            list of students that will be filtered
	 * @return list containing all students who have grade 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(record -> record.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Method that constructs a sorted list containing students with grade equal to
	 * 5
	 * 
	 * @param records
	 *            list of students that will be filtered
	 * @return sorted list containing all students who have grade 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return vratiListuOdlikasa(records).stream()
				.sorted((record1, record2) -> Double.valueOf(countScore(record2)).compareTo(countScore(record1)))
				.collect(Collectors.toList());
	}

	/**
	 * Method that constructs a sorted list containing students with grade equal to
	 * 1
	 * 
	 * @param records
	 *            list of students that will be filtered
	 * @return sorted list containing all students who have grade 1
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(record -> record.getGrade() == 1).map(StudentRecord::getJmbag)
				.sorted((jmbag1, jmbag2) -> jmbag1.compareTo(jmbag2)).collect(Collectors.toList());
	}

	/**
	 * Method that constructs a map where grades are keys and lists with students
	 * who have the certain grade
	 * 
	 * @param records
	 *            list of student records from which the map will be constructed
	 * @return map containing lists of students where keys are the grades of the
	 *         students in the list
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Method that constructs a map with the grades as keys and number of students
	 * with the grade as a value
	 * 
	 * @param records
	 *            list of student records from which the map will be constructed
	 * @return map containing number of students with a student grade where the key
	 *         is that grade
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, record -> 1, (valueNow, valueNew) -> valueNow + 1));
	}

	/**
	 * Method that constructs a map with the true or false as keys and lists of
	 * students as values depending if they have a positive grade or not
	 * 
	 * @param records
	 *            list of student records from which the map will be constructed
	 * @return map containing list of students and where the keys are true and false
	 *         depending if the students in the list passed or failed
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy((StudentRecord record) -> record.getGrade() != 1));
	}

	/**
	 * Method that calculates total score of the given student
	 * 
	 * @param record
	 *            student record from which the total score will be calculated
	 * @return total score
	 */
	private static double countScore(StudentRecord record) {
		return record.getScoreLAB() + record.getScoreMI() + record.getScoreZI();
	}

	/**
	 * Method used for printing a list of student records
	 * 
	 * @param records
	 *            list that will be printed
	 */
	private static void printList(List<StudentRecord> records) {
		for (StudentRecord record : records) {
			System.out.println(record);
		}
	}
}
