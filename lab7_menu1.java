import java.sql.*; 
import java.util.*;
import java.util.Calendar;
import java.util.Date;

public class menu1 {
	Scanner sc = new Scanner(System.in);
	
	public void _menu1() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/aplab6","root","");  
			//here aplab6 is database name, root is username  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Menu");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getInt(4));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}
	}
	public void _order(int hours) {
			try{  
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/aplab6","root","");  
				//here aplab6 is database name, root is username  
				Statement stmt=con.createStatement();
				int item = 1;
				int time = 0;
				int delivery;
				int o_time = 0;
				String sql = "INSERT INTO Orders(item_type,name,price,time,delivery_type,c_name,c_address) VALUES(?,?,?,?,?,?,?)";
				System.out.print("Enter your name:");
				String c_name = sc.next();
				System.out.print("Enter your address:");
				String c_address = sc.next();
				System.out.println("Select order time:\n1. Home Delivery\n2.Pick-up");
				delivery = sc.nextInt();
				String d_type;
				if(delivery == 1) d_type = "home";
				else d_type = "pickup";
				while(item!=0) {
					System.out.println("input item ID or enter 0 to end this order.");
					item = sc.nextInt();
					if (item==0)break;
					else {
						PreparedStatement pstmt = con.prepareStatement("select * from Menu where ID = ?");
						pstmt.setString(1,Integer.toString(item));
						ResultSet rs=pstmt.executeQuery();  
						while(rs.next()) {  
							System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getInt(4));
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1,rs.getString(2));
							pstmt.setString(2,rs.getString(3));
							pstmt.setInt(3,rs.getInt(4));
							if(rs.getString(2).equals("Main Course Dishes")) time = 15;
							else time = 10;
							pstmt.setInt(4,time);
							pstmt.setString(5,d_type);
							pstmt.setString(6,c_name);
							pstmt.setString(7,c_address);
							pstmt.executeUpdate();
						}	
					}
				}
				ResultSet rs3 = stmt.executeQuery("select sum(time) from Orders where c_name = \'"+c_name+"\'");
				while(rs3.next()) {
					System.out.println("Estimated total time of the orders is: "+rs3.getInt(1));
					o_time = rs3.getInt(1)/60;
				}
		
				if((hours+o_time)>=22) {
					System.out.println("Your order is canceled as order completion time doesn't meet cafe timings.");
					String del_sql = "Delete from orders where c_name = ?";
					PreparedStatement pstmt = con.prepareStatement(del_sql);
					pstmt.setString(1,c_name);
					pstmt.executeUpdate();
				}
				else {
					ResultSet rs2=stmt.executeQuery("select * from orders where c_name = \'"+c_name+"\'");
					System.out.println("Your order list is following:");
					while(rs2.next())
						System.out.println(rs2.getString(1)+"  "+rs2.getString(2));
					ResultSet rs1 = stmt.executeQuery("select sum(price) from Orders where c_name = \'"+c_name+"\'");
					rs1.next();
					System.out.println("Total price of the orders is: "+rs1.getInt(1));
					
					//CallableStatements to execute stored procedure
					
					System.out.println("CallableStatements to execute stored procedure \"getOrders\":");
					CallableStatement cstmt = null;
					String proc_sql = "{call getOrder (?, ?,?)}";
					cstmt = con.prepareCall (proc_sql);
					cstmt.setString(1, "abdullah");
					cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
					// set INOUT parameter 
					cstmt.setString(3, "11"); 
					// register INOUT parameter 
					cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);  
					//You then execute the statement with no return value 
					cstmt.execute(); 
					// get String OUT and INOUT 
					System.out.println("address: "+cstmt.getString(2)+"\nitem: "+cstmt.getString(3)); 
					
				}
				con.close();  	
				}catch(Exception e){ System.out.println(e);}
		
	}
	
}
