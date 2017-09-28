import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		try (ComputerFirm tank = new ComputerFirm();
				Scanner s = new Scanner(System.in)) {
			   
			    
			    
			boolean hasQuery = true;

			while (hasQuery) {
				 String query = s.nextLine();
				switch (query) {
				case "get laptops":
					tank.getLaptops();
					break;
				case "Printer makers":
					tank.getPrinterMaker();
					break;
				case "add-laptop":
					tank.addLaptop("model-B-lp", 128, 32, 1800, 830, 21);
					break;
				case "update-price":
					System.out.println("enter code");
					int c=s.nextInt();
					System.out.println("enter price");
					int p=s.nextInt();
					tank.updatePrice(c, p);
					break;	
				case "delete":
					System.out.println("enter code");
					int t=s.nextInt();
					tank.deleteLaptop(t);
					break;		
				default:
					hasQuery = false;
					break;
				}
			}
		}
		
		

	}

}
