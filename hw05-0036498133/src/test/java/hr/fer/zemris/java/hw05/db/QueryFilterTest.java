package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.QueryFilter.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class QueryFilterTest {

	@Test
	public void testAccepts() {
		StudentDatabase sd = databaseFactory();
		QueryParser qp = new QueryParser("firstName >= \"M\" and lastName>\"M\"");
		
		QueryFilter filter = new QueryFilter(qp.getQuery());
		
		List<StudentRecord> tempList = sd.filter(filter);
		
		Assert.assertEquals(1, tempList.size());
		Assert.assertEquals("Marulić", tempList.get(0).getLastName());
	}
	
	@Test
	public void testAcceptsWhenQueryEmpty() {
		StudentDatabase sd = databaseFactory();
		QueryParser qp = new QueryParser("   ");
		
		QueryFilter filter = new QueryFilter(qp.getQuery());
		
		List<StudentRecord> tempList = sd.filter(filter);
		
		Assert.assertEquals(6, tempList.size());
	}
	
	@Test
	public void testAcceptsWhenNoneAccepted() {
		StudentDatabase sd = databaseFactory();
		QueryParser qp = new QueryParser("firstName =     \"Nikola\"");
		
		QueryFilter filter = new QueryFilter(qp.getQuery());
		
		List<StudentRecord> tempList = sd.filter(filter);
		
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
