package _Bank_remaster.menu;

import java.util.Stack;

public abstract class CRUDMenu extends Menu {
protected static final Stack<Menu> menuStack = new Stack<>();
	
	protected void pushMenu(Menu menu) {
		menuStack.push(menu);
	}
	
	protected void popMenu() {
		menuStack.pop();
	}
	
	protected void returnToPreviousMenu() {
		if(!menuStack.isEmpty()) {
			popMenu();
			menuStack.peek().start();
		}
	}
}
