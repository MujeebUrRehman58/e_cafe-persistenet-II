import java.sql.*;
import java.util.Calendar;
import java.util.Date; 
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);  
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		System.out.println("time is "+hours);
		if (hours >= 11 && hours <= 22) {
		menu1 object=new menu1();
		object._menu1();
		object._order(hours);
		/*Menu object=new Menu();
		object.menu();
		order object1 = new order();
		object1.orderList(object);
		  */
		}
		else System.out.println("The café plans to stay open between 11:00am – 10:00pm");
	}
}
