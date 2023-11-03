package _Bank_remaster.menu;

import java.sql.Connection;

import _Bank_remaster.repositories.AccountRepository;
import _Bank_remaster.repositories.AccountRepositoryImpl;
import _Bank_remaster.repositories.BankRepository;
import _Bank_remaster.repositories.BankRepositoryImpl;
import _Bank_remaster.repositories.TransactionRepository;
import _Bank_remaster.repositories.TransactionRepositoryImpl;
import _Bank_remaster.repositories.UserRepository;
import _Bank_remaster.repositories.UserRepositoryImpl;

/*
 * The CRUD main menu that wants you to choose model that will be used in CRUD-operations.
 */
public class CRUDMainMenu extends CRUDMenu{

	private final BankRepository bankRepo;
	private final AccountRepository accountRepo;
	private final TransactionRepository transactionRepo;
	private final UserRepository userRepo;
	
	private final String MENU = """
			\n----------------------------
			1: Операции со счетами
			2: Операции с пользователями
			3: Операции с транзакциями
			4: Операции с банками
			----------------------------""";
	
	public CRUDMainMenu(Connection connection) {
		bankRepo = new BankRepositoryImpl(connection);
		accountRepo = new AccountRepositoryImpl(connection);
		transactionRepo = new TransactionRepositoryImpl(connection);
		userRepo = new UserRepositoryImpl(connection);
	}

	@Override
	public void start() {
	
		while(true) {
			printMenu(MENU);
			switch (scanner.nextInt()) {
				case 1 -> {
					AccountMenu accountMenu = new AccountMenu(accountRepo);
					pushMenu(accountMenu);
					accountMenu.start();
				}
				case 2 -> {
					UserMenu userMenu = new UserMenu(userRepo);
					pushMenu(userMenu);
					userMenu.start();
				}
				case 3 -> {
					TransactionMenu transactionMenu = new TransactionMenu(transactionRepo);
					pushMenu(transactionMenu);
					transactionMenu.start();
				}
				case 4 -> {
					BankMenu bankMenu = new BankMenu(bankRepo);
					pushMenu(bankMenu);
					bankMenu.start();
				}
			}
		}
	}
}
