import static org.junit.Assert.*;

import org.junit.Test;

public class shopping_cart_test {

	@Test
	public void testNewShoppingCart() {
		ShoppingCart sc = new ShoppingCart();
		assertEquals(0, sc.getItemCount());
	}

	@Test
	public void testEmptyShoppingCart() {
		ShoppingCart sc = new ShoppingCart();
		sc.addItem(new Product("Pie", 3.14));
		sc.addItem(new Product("Apple", 1.73));
		sc.addItem(new Product("Shoe", 50.99));
		assertEquals(3, sc.getItemCount());
		sc.empty();
		assertEquals(0, sc.getItemCount());
	}

	@Test
	public void testAddProduct() {
		ShoppingCart sc = new ShoppingCart();
		sc.addItem(new Product("Pie", 3.14));
		assertEquals(1, sc.getItemCount());
		sc.addItem(new Product("Apple", 1.73));
		sc.addItem(new Product("Shoe", 50.99));
		assertEquals(3, sc.getItemCount());
	}
	
	@Test
	public void testRemoveProduct()  {
		ShoppingCart sc = new ShoppingCart();
		Product p1 = new Product("Pie", 3.14);
		sc.addItem(p1);
		sc.addItem(new Product("Apple", 1.73));
		sc.addItem(new Product("Shoe", 50.99));
		assertEquals(3, sc.getItemCount());
		try {
			sc.removeItem(p1);
		} catch (ProductNotFoundException e) {

		}
		assertEquals(2, sc.getItemCount());
		
		try {
			sc.removeItem(new Product("TV",1000));
		} catch (ProductNotFoundException e) {
			
		}
		assertEquals(2, sc.getItemCount());
	}
}
