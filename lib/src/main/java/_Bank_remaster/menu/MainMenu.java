package _Bank_remaster.menu;

import java.sql.Connection;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.User;
import _Bank_remaster.repositories.AccountRepository;
import _Bank_remaster.repositories.AccountRepositoryImpl;
import _Bank_remaster.repositories.TransactionRepositoryImpl;
import _Bank_remaster.services.AccountService;
import _Bank_remaster.services.AccountServiceImpl;
import _Bank_remaster.services.TransactionServiceImpl;

public class MainMenu extends Menu {
	
	private final AccountService accountService;
	private final AccountRepository accountRepo;

	private final String MENU = """
			----------------------------
			1: Пополнить баланс
			2: Снять деньги
			3: Перевод
			4: Запросить выписку
			5: Сменить счет
			6: Выход
			----------------------------
			""";
	

	
	
	public MainMenu(Connection connection) {
		this.accountRepo = new AccountRepositoryImpl(connection);
		this.accountService = new AccountServiceImpl(accountRepo, 
				new TransactionServiceImpl(new TransactionRepositoryImpl(connection)));
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
					System.out.println("На какую сумму вы хотите пополнить баланс? ");
					accountService.deposit(account, scanner.nextBigDecimal());
				}
				case 2 -> {
					System.out.println("Какую сумму вы хотите снять? ");
					accountService.withdraw(account, scanner.nextBigDecimal());
				}
				case 3 -> {
					System.out.println("Введите номер счета, на который хотите перевести деньги: ");
					Account recipAccount;
					try {
						recipAccount = accountRepo.findByNumber(scanner.next());
					} catch (AccNotFoundException e) {
						System.out.println("Счет не найден.");
						break;
					}
					
					System.out.println("Введите сумму, которую хотите перевести: ");
					accountService.transfer(account, recipAccount, scanner.nextBigDecimal());
				}
				case 4 -> {
					throw new RuntimeException();
				}
				case 5 -> {
					System.out.println("Выберите счет:");
					askAccount(user);
				}
				case 6 -> {
					System.exit(0);
				}
					
			}
		}
		
	}
	
	private Account askAccount(User user) {
		Account account;
		List<Account> accountsByUser = accountRepo.findAccountsByUser(user);
		if(accountsByUser.size() != 0) {
			System.out.println("Выберите счет, которым хотите воспользоваться: ");
			printAllAccNumbers(accountsByUser);
			try {
				account = accountRepo.findByNumber(accountsByUser.get(scanner.nextInt() - 1).getAccountNumber());
				return account;
			} catch (AccNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		System.out.println("У данного пользователя нет счетов");
		return null;
	}
	
	private User askFIO() {
		System.out.println("Введите свое ФИО: ");
		String[] fioSplitted = scanner.next().split(" ");
		
		User user = User.builder()
				.surname(fioSplitted[0])
				.name(fioSplitted[1])
				.patronymic(fioSplitted[2])
				.build();
		
		return user;
	}
	
	private void printAllAccNumbers(List<Account> accounts) {
		for(int i = 1; i <= accounts.size(); i++) {
			System.out.format(i + ": %10s", accounts.get(i - 1));
		}
	}

}
