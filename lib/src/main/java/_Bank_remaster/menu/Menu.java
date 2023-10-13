package _Bank_remaster.menu;

import java.util.Scanner;

public abstract class Menu {

	protected final Scanner scanner = new Scanner(System.in);
	
	protected void printMenu(String menu) {
		System.out.println(menu);
	}
	
	public abstract void start();
}
