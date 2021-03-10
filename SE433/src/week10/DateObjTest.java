package week10;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateObjTest {

	@Test(expected = IllegalArgumentException.class)
	public void Constructor() {
		/*Should throw exception because of invalid month entries*/
		DateObj d1 = new DateObj(2020, 13, 1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void Constructor2() {
		//Exception should raid b/c of invalid month
		DateObj d1 = new DateObj(2020, -5, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor3() {
		//Exception should be raised bc of date
		DateObj d1 = new DateObj(2020, 1, 32);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Valid_Day() {
		DateObj d1 = new DateObj(2020, 4, 32);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Leap_Year() {
		//Checks for valid leap year
		DateObj d1 = new DateObj(2021, 2, 29);
	}
	
	@Test
	public void Leap_Year2() {
		//Should be a valid lead year
		DateObj d1 = new DateObj(2020, 2, 29);
	}
	
	@Test
	public void Valid() {
		//Valid Date object
		DateObj d1 = new DateObj(2021, 4, 29);
	}
	
	@Test
	public void valid2() {
		DateObj d1 = new DateObj(2020, 4, 29);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Invalid_Date() {
		//Throws errors bc of dates
		DateObj d1 = new DateObj(2000,1,32);
		d1 = new DateObj(2000,3,32);
		d1 = new DateObj(2000,5,32);
		d1 = new DateObj(2000,7,32);
		d1 = new DateObj(2000,8,32);
		d1 = new DateObj(2000,10,32);
		d1 = new DateObj(2000,12,32);
		d1 = new DateObj(2000,1,3);
	}
	
	@Test
	public void nextDay() {
		//Test nextDay method on leap years, new year, new month
		DateObj d1 = new DateObj(2020, 1 ,31);
		DateObj d2 = new DateObj(2020, 2, 1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(3, 3, 31);
		d2 = new DateObj(3,4,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,5,31);
		d2 = new DateObj(2100,6,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,7,31);
		d2 = new DateObj(2100,8,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,8,31);
		d2 = new DateObj(2100,9,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,10,31);
		d2 = new DateObj(2100,11,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,8,30);
		d2 = new DateObj(2100,8,31);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,4,30);
		d2 = new DateObj(2100,5,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,6,30);
		d2 = new DateObj(2100,7,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,6,30);
		d2 = new DateObj(2100,7,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,9,30);
		d2 = new DateObj(2100,10,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,11,30);
		d2 = new DateObj(2100,12,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,11,29);
		d2 = new DateObj(2100,11,30);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,12,31);
		d2 = new DateObj(2101,1,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,12,30);
		d2 = new DateObj(2100,12,31);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2100,2,1);
		d2 = new DateObj(2100,2,2);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2020,2,28);
		d2 = new DateObj(2020,2,29);
		assertEquals(d2.toString(), d1.nextDate().toString());
		
		d1 = new DateObj(2020,2,29);
		d2 = new DateObj(2020,3,1);
		assertEquals(d2.toString(), d1.nextDate().toString());
	}

}
