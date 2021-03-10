package week10;

public class DateObj {
	
	private int year;
	private int month;
	private int day;
	
	public DateObj(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		validate();
	}


	public DateObj nextDate() {
		int newYear = this.year;
		int newMonth = this.month;
		int newDay = this.day;
		boolean isLeap = leapYear();
		if(this.month == 12 && this.day == 31) {
			newYear = this.year+1;
			newMonth = 1;
			newDay = 1;
		}
		else if((this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10) && this.day == 31) {
			//new month
			newYear = this.year;
			newMonth = this.month+1;
			newDay = 1;
		}
		else if((this.month == 4 || this.month == 6 || this.month == 9|| this.month == 11) && this.day == 30) {
			newYear = this.year;
			newMonth = this.month+1;
			newDay = 1;
		}
		else if(isLeap == true && this.month == 2) {
			if(this.day == 28) {
				newDay = 29;
				newYear = this.year;
			}
			else {
				newMonth += 1;
				newDay = 1;
				}
		}
		else {
			newDay +=1;
		}
			
		
		return new DateObj(newYear, newMonth, newDay);
	}

	@Override
	public String toString() {
		return String.format("Date[year: %d, month: %d, day: %d]", year, month, day);
	}

	private void validate() {
		boolean leap = leapYear();
		if(this.month > 12 || this.month < 1) {
			throw new IllegalArgumentException("Invalid month value");
		}
		else if(this.month == 2 && leap == false) {
			if(this.day >= 29) {
				throw new IllegalArgumentException("Day is invalid");
			}
		}
		else if(this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12) {
			if(this.day > 31) {
				throw new IllegalArgumentException("Day is invalid");
			}
		}
		
		else if(this.month == 4 || this.month == 6 || this.month == 9|| this.month == 11) {
			if(this.day > 30) {
				throw new IllegalArgumentException("Day is invalid");
			}
		}
	}


	private boolean leapYear() {
		boolean isLeap = false;
		
        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                if ( year % 400 == 0)
                    isLeap = true;
                else
                    isLeap = false;
            }
            else
                isLeap = true;
        }
        else {
            isLeap = false;
        }
		return isLeap;
	}

}
