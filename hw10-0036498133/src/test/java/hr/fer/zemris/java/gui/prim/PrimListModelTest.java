package hr.fer.zemris.java.gui.prim;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Assert;
import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void testWhenNonePrimesGenerated() {
		PrimListModel model = new PrimListModel();

		Assert.assertEquals(1, model.getSize());
		Assert.assertEquals(1, (int) model.getElementAt(0));
	}

	@Test
	public void testGetElementAt() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();
		model.next();

		Assert.assertEquals(1, (int) model.getElementAt(0));
		Assert.assertEquals(2, (int) model.getElementAt(1));
		Assert.assertEquals(3, (int) model.getElementAt(2));
		Assert.assertEquals(5, (int) model.getElementAt(3));
		Assert.assertEquals(7, (int) model.getElementAt(4));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetElementAtWhenArgOutOfBounds() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();
		model.next();

		model.getElementAt(7);// throws
	}

	@Test
	public void testGetSize() {
		PrimListModel model = new PrimListModel();

		model.next();
		model.next();
		model.next();

		Assert.assertEquals(4, model.getSize());
	}

	@Test(expected = RuntimeException.class)
	public void testNext() {

		PrimListModel model = new PrimListModel();

		model.addListDataListener(new ListDataListener() {

			@Override
			public void intervalAdded(ListDataEvent e) {
				throw new RuntimeException();
			}

			@Override
			public void contentsChanged(ListDataEvent e) {

			}

			@Override
			public void intervalRemoved(ListDataEvent e) {

			}
		});

		model.next();// should throw RuntimeException which means that the added listener was
						// notified
	}
}
