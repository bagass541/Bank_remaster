package _Bank_remaster.menu;

import java.util.Scanner;
/*
 * Abstract menu
 */
public abstract class Menu {

	protected final Scanner scanner = new Scanner(System.in);
	
	/*
	 * Print string which represents a menu.
	 * 
	 * @param menu.
	 */
	protected void printMenu(String menu) {
		System.out.println(menu);
	}
	
	/*
	 * Launch a menu.
	 */
	public abstract void start();
}
