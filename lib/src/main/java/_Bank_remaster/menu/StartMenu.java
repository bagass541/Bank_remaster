package _Bank_remaster.menu;

import java.sql.Connection;
import java.sql.SQLException;
import _Bank_remaster.db.DatabaseConnection;

/*
 * The start menu that offers to choose what operations do you want to do.
 */
public class StartMenu extends Menu {

	private final String MENU = """
			\n----------------------------
			1: CRUD-операции
			2: Пользователь
			----------------------------""" ;
	

	@Override
	public void start() {
		try(Connection connection = DatabaseConnection.getConnection()) {
			while(true) {
				printMenu(MENU);
				int chosen = scanner.nextInt();
				if(chosen == 1) {
					CRUDMenu crudMenu = new CRUDMainMenu(connection);
					crudMenu.pushMenu(crudMenu);
					crudMenu.start();
				} else if(chosen == 2) {
					Menu mainMenu = new MainMenu(connection);
					mainMenu.start();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
