package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentDatabase.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

public class FieldValueGettersTest {
	
	@Test
	public void testFirstName() {
		StudentDatabase sd = databaseFactory();
		
		StudentRecord rec = sd.forJMBAG("1241");
		
		Assert.assertEquals("Ivana", FieldValueGetters.FIRST_NAME.get(rec));
	}
	
	@Test
	public void testLastName() {
		StudentDatabase sd = databaseFactory();
		
		StudentRecord rec = sd.forJMBAG("1224");
		
		Assert.assertEquals("Martinković", FieldValueGetters.LAST_NAME.get(rec));
	}
	
	@Test
	public void testJmbag() {
		StudentDatabase sd = databaseFactory();
		
		StudentRecord rec = sd.forJMBAG("1245");
		
		Assert.assertEquals("1245", FieldValueGetters.JMBAG.get(rec));
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
