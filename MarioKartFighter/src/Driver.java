import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.mariokartfighter.menu.MainMenu;

public class Driver {
	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.mainMenu();
	}
}
