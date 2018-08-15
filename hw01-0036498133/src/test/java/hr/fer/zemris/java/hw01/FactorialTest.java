package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Rafael Josip Penić
 *
 */
public class FactorialTest {

	@Test
	public void zaNulu() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}

	@Test
	public void zaJedan() {
		Assert.assertEquals(1, Factorial.calculateFactorial(1));
	}

	@Test
	public void zaTri() {
		Assert.assertEquals(6, Factorial.calculateFactorial(3));
	}
	
	@Test
	public void izvanZadanogIntervala() {
		try {
			Factorial.calculateFactorial(-5);
			Factorial.calculateFactorial(21);
			Assert.assertTrue(false);
		} catch (IllegalArgumentException ex) {
			Assert.assertTrue(true);
		}
	}
}
