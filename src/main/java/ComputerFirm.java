import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerFirm implements AutoCloseable {

	private Connection conn;
	private List<String> makers = new ArrayList();

	public ComputerFirm() {
		try {
			this.conn = DriverManager.getConnection(
					"jdbc:sqlserver://DESKTOP-71FQ6SA\\SQLEXPRESS;database=ComputerFirm;user=test;password=test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//
	void updatePrice(int code, int price) {
		String query = "Update laptop set price=? where code=?";
		try ( PreparedStatement ps = conn.prepareStatement(query) )
		{
				ps.setInt(1, price);
				ps.setInt(2, code);
				int r = ps.executeUpdate();
				System.out.println("The changes have been made.");
//				ResultSet rs = ps.executeQuery();
//				while(rs.next()){
//					System.out.println();
//				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void deleteLaptop(int code){
		String query = "Delete from laptop where code=?";
		try(PreparedStatement ps = conn.prepareStatement(query))
		 { 
				ps.setInt(1, code);
				int b = ps.executeUpdate();
				System.out.println("Deletion is made.");
//				ResultSet rs = ps.executeQuery("Select * from laptop");
//				while(rs.next()){
//					System.out.println(rs.getInt("code")+" "+rs.getString("model")+" "+rs.getInt("price"));
//				}
				this.getLaptops();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	
	void getLaptops(){
		String query = "Select * from laptop";
		try(PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()){
			while(rs.next()){
				System.out.println(rs.getInt("code")+" "+rs.getString("model")+" "+rs.getInt("price"));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
//
	void addLaptop( String model, int speed, int ram, int hd, int price, int screen) {
		String query = "Insert into laptop ( model, speed, ram, hd, price, screen)"
				+ " values (?, ?, ?, ?, ?, ?)";
		String identity = "Select @@IDENTITY";
		
		try (PreparedStatement ps = conn.prepareStatement(query);
				PreparedStatement psIdentity = conn.prepareStatement(identity)) {
		//	ps.setInt(1, code);
			ps.setString(1, model);
			ps.setInt(2, speed);
			ps.setInt(3, ram);
			ps.setInt(4, hd);
			ps.setInt(5, price);
			ps.setInt(6, screen);
			
			int a = ps.executeUpdate();
			System.out.println(a+" rows Updated");
			
			ResultSet rs = psIdentity.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(""));
 			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	
	void getPrinterMaker() {
		if (makers.isEmpty()) {
			String query = "Select DISTINCT maker from Product where type = 'Printer'";
			try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					makers.add(rs.getString("maker"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (String maker : makers) {
			System.out.println(maker);
		}
	}

	public void cleanCaches() {
		this.makers.clear();

	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

}
