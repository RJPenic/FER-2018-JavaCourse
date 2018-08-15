package hr.fer.zemris.java.hw01;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import org.junit.Assert;

/**
 * 
 * @author Rafael Josip Penić
 *
 */
public class UniqueNumbersTest {

	@Test
	public void addZaGlavuNull() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);

		Assert.assertEquals(head.value, 15);
		Assert.assertEquals(head.left, null);
		Assert.assertEquals(head.right, null);
	}

	@Test
	public void addULijevuGranu() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 10);

		Assert.assertEquals(head.left.value, 10);
		Assert.assertNull(head.left.left);
		Assert.assertNull(head.left.right);
		Assert.assertEquals(head.value, 15);
		Assert.assertNull(head.right);
	}

	@Test
	public void addUDesnuGranu() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);

		Assert.assertEquals(head.right.value, 20);
		Assert.assertNull(head.right.left);
		Assert.assertNull(head.right.right);
		Assert.assertEquals(head.value, 15);
		Assert.assertNull(head.left);
	}

	@Test
	public void addBrojKojiPostoji() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 14);
		head = UniqueNumbers.addNode(head, 20);

		Assert.assertNull(head.right.left);
		Assert.assertNull(head.right.right);
	}

	@Test
	public void containsGlavu() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 25);

		Assert.assertTrue(UniqueNumbers.containsValue(head, 15));
	}

	@Test
	public void containsBrojKojiNijeDodan() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 25);

		Assert.assertFalse(UniqueNumbers.containsValue(head, 253));
	}

	@Test
	public void treeSizeZaPraznoStablo() {
		TreeNode head = null;

		Assert.assertEquals(0, UniqueNumbers.treeSize(head));
	}

	@Test
	public void treeSizeZaSamoGlavu() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);

		Assert.assertEquals(1, UniqueNumbers.treeSize(head));
	}
	
	@Test
	public void treeSizeZaNetrivijalnoStablo() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 25);
		
		Assert.assertEquals(4, UniqueNumbers.treeSize(head));
	}
	
	@Test
	public void sortedPraznogStabla() {
		TreeNode head = null;
		
		Assert.assertEquals("", UniqueNumbers.sorted(head));
	}
	
	@Test
	public void sortedNetrivijalnogStabla() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 25);
		
		String tempArray[] = UniqueNumbers.sorted(head).split(" ");
		
		Assert.assertEquals(4, tempArray.length);
		
		for(int i = 0; i < tempArray.length; i++) {
			Assert.assertTrue(UniqueNumbers.containsValue(head, Integer.parseInt(tempArray[i])));
		}
		
		for(int i = 1; i < tempArray.length; i++) {
			Assert.assertTrue(Integer.parseInt(tempArray[i]) > Integer.parseInt(tempArray[i-1]));
		}
	}
	
	@Test
	public void reversedSortedPraznogStabla() {
		TreeNode head = null;
		
		Assert.assertEquals("", UniqueNumbers.sorted(head));
	}
	
	@Test
	public void reversedSortedNekogStabla() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 20);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 15);
		head = UniqueNumbers.addNode(head, 25);
		
		String tempArray[] = UniqueNumbers.reversedSorted(head).split(" ");
		
		Assert.assertEquals(4, tempArray.length);
		
		for(int i = 0; i < tempArray.length; i++) {
			Assert.assertTrue(UniqueNumbers.containsValue(head, Integer.parseInt(tempArray[i])));
		}
		
		for(int i = 1; i < tempArray.length; i++) {
			Assert.assertTrue(Integer.parseInt(tempArray[i]) < Integer.parseInt(tempArray[i-1]));
		}
	}
}
