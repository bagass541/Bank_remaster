package _Bank_remaster.menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.text.DocumentException;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.User;
import _Bank_remaster.repositories.AccountRepository;
import _Bank_remaster.repositories.AccountRepositoryImpl;
import _Bank_remaster.repositories.TransactionRepository;
import _Bank_remaster.repositories.TransactionRepositoryImpl;
import _Bank_remaster.services.AccountService;
import _Bank_remaster.services.AccountServiceImpl;
import _Bank_remaster.services.TransactionServiceImpl;
import _Bank_remaster.util.ChequeGenerator;
import _Bank_remaster.util.StatementGenerator;
import _Bank_remaster.util.TimePeriod;

/**
 * The main menu that will be displayed to a regular user.
 */
public class MainMenu extends Menu {
	
	private final AccountService accountService;
	private final AccountRepository accountRepo;
	private final StatementGenerator statementGenerator;

	private final String MENU = """
			\n----------------------------
			1: Проверить баланс
			2: Пополнить баланс
			3: Снять деньги
			4: Перевод
			5: Запросить выписку
			6: Сменить счет
			7: Выход
			----------------------------""";
	
	
	
	
	public MainMenu(Connection connection) {
		accountRepo = new AccountRepositoryImpl(connection);
		TransactionRepository transactionRepo = new TransactionRepositoryImpl(connection);
		accountService = new AccountServiceImpl(accountRepo, 
				new TransactionServiceImpl(transactionRepo), new ChequeGenerator());
		statementGenerator = new StatementGenerator(transactionRepo);
	}


	@Override
	public void start() {
		Account account;
		User user;
		while(true) {
			user = askFIO();
			account = askAccount(user);
			
			if(account != null) {
				break;
			}
		}
		
		while(true) {
			printMenu(MENU);
			switch(scanner.nextInt()) {
				case 1 -> {
					System.out.println("\nВаш баланс: " + account.getBalance() + " BYN");
					
				}
				case 2 -> {
					System.out.println("\nНа какую сумму вы хотите пополнить баланс? ");
					accountService.deposit(account, scanner.nextBigDecimal());
					
				}
				case 3 -> {
					System.out.println("\nКакую сумму вы хотите снять? ");
					accountService.withdraw(account, scanner.nextBigDecimal());
					
				}
				case 4 -> {
					System.out.println("\nВведите номер счета, на который хотите перевести деньги: ");
					Account recipAccount;
					try {
						recipAccount = accountRepo.findByNumber(scanner.next());
					} catch (AccNotFoundException e) {
						System.out.println("\nСчет не найден.");
						break;
					}
					
					System.out.println("\nВведите сумму, которую хотите перевести: ");
					accountService.transfer(account, recipAccount, scanner.nextBigDecimal());
				}
				case 5 -> {
					try {
						statementGenerator.generate(account, TimePeriod.ALL_TIME);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case 6 -> {
					account = askAccount(user);
				}
				case 7 -> {
					System.exit(0);
				}
					
			}
		}
		
	}
	
	/*
	 * Finds the user's accounts and offers to choose one.
	 * If the user doesnt have account,  
	 * 
	 * @param user The user who wants to use his accounts.
	 * @return choosed account.
	 */
	private Account askAccount(User user) {
		List<Account> accountsByUser = accountRepo.findAccountsByUser(user);
		if(accountsByUser.size() != 0) {
			
			while(true) {
				System.out.println("\nВыберите счет, которым хотите воспользоваться: ");
				printAllAccNumbers(accountsByUser);
				int i = scanner.nextInt() - 1;
				if(accountsByUser.size() > i) {
					return accountsByUser.get(i);
				}
			}

		} 
		
		System.out.println("\nУ данного пользователя нет счетов");
		return null;
	}
	
	/*
	 * Requests user input, validates input, create a user, sets properties and then returns the User.
	 * 
	 * @return user The user which was created with input properties.
	 */
	private User askFIO() {
		System.out.println("\nВведите свое ФИО: ");
		String fio = scanner.nextLine();
		
		if(!fio.matches("[А-Я]\\D+ [А-Я]\\D+ [А-Я]\\D+")) {
			return askFIO();
		}
		
		String[] fioSplitted = fio.split(" ");
		
		User user = User.builder()
				.surname(fioSplitted[0])
				.name(fioSplitted[1])
				.patronymic(fioSplitted[2])
				.build();
		
		return user;
	}
	
	/*
	 * Print all accounts in the list.
	 * 
	 * @param accounts Accounts that belong to the current user.
	 */
	private void printAllAccNumbers(List<Account> accounts) {
		for(int i = 1; i <= accounts.size(); i++) {
			System.out.format(i + ": %10s, %.2f, %10s\n", accounts.get(i - 1).getAccountNumber()
					, accounts.get(i - 1).getBalance(), accounts.get(i - 1).getOpeningDate().toString());
		}
	}

}
