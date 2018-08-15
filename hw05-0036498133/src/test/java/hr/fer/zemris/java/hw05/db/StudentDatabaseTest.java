package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw05.db.StudentDatabase.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

public class StudentDatabaseTest {

	@Test
	public void testForJmbag() {
		StudentDatabase sd = databaseFactory();
		
		StudentRecord sr = sd.forJMBAG("1241");
		
		Assert.assertEquals("Ivana", sr.getFirstName());
		Assert.assertEquals("Brlić Mažuranić", sr.getLastName());
		Assert.assertEquals(3, sr.getGrade());
	}
	
	@Test
	public void testWhenFilterAlwaysTrue() {
		StudentDatabase sd = databaseFactory();
		
		List<StudentRecord> tempList = sd.filter((record) -> true);
		
		Assert.assertEquals(6, tempList.size());
	}
	
	@Test
	public void testWhenFilterAlwaysFalse() {
		StudentDatabase sd = databaseFactory();
		
		List<StudentRecord> tempList = sd.filter((record) -> false);
		
		Assert.assertEquals(0, tempList.size());
	}
	
	private StudentDatabase databaseFactory() {
		List<String> list = new ArrayList<>();
		
		list.add("0000	Marulić	Marko	5");
		list.add("0005	Marković	Ivan	2");
		list.add("1241	Brlić Mažuranić	Ivana	3");
		list.add("7321	Ivanković	Josip	1");
		list.add("1224	Martinković	Ivan	3");
		list.add("1245	Ivanković	Klara	3");
		
		return new StudentDatabase(list);
	}
}
