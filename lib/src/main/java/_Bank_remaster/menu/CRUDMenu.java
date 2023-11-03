package _Bank_remaster.menu;

import java.util.Stack;

/*
 * Abstract CRUD-menu.
 */
public abstract class CRUDMenu extends Menu {
	
protected static final Stack<Menu> menuStack = new Stack<>();

	/*
	 * Pushes the string which displays a menu to menuStack.
	 * 
	 * @param menu The menu that must be added to the menuStack.
	 */
	protected void pushMenu(Menu menu) {
		menuStack.push(menu);
	}
	
	/*
	 * Pops the current menu from menuStack.
	 */
	protected void popMenu() {
		menuStack.pop();
	}
	
	/*
	 * Returns to the previous menu by popping the current menu from menuStack.
	 * If menuStack is empty, this method does nothing.
	 */
	protected void returnToPreviousMenu() {
		if(!menuStack.isEmpty()) {
			popMenu();
			menuStack.peek().start();
		}
	}
}
