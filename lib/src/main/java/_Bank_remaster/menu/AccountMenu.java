package _Bank_remaster.menu;

import java.sql.Date;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Bank;
import _Bank_remaster.models.User;
import _Bank_remaster.repositories.AccountRepository;

public class AccountMenu extends CRUDMenu {
	
	private final AccountRepository accountRepo;

	private final String MENU = """
			\n----------------------------
			1: Просмотреть все счета
			2: Добавить счет
			3: Изменить счет
			4: Удалить счет
			5: Вернуться назад
			----------------------------""";
	
	public AccountMenu(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}


	@Override
	public void start() {
		while(true) {
			printMenu(MENU);
			switch(scanner.nextInt()) {
				case 1 -> {
					accountRepo.findAllAccounts().forEach(System.out::println);	
				}
				case 2 -> {
					Account account = new Account();
					askProperties(account);	
					accountRepo.createAccount(account);
				}
				case 3 -> {
					System.out.println("\nВведите id счета, который нужно изменить: ");
					Account account = null;
					try {
						account = accountRepo.findById(scanner.nextLong());
					} catch (AccNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(account);
					
					askProperties(account);
					
					try {
						accountRepo.updateAccount(account);
					} catch(RuntimeException ex) {
						System.out.println("\nЧто-то пошло не так");
					}
					
				}
				case 4 -> {
					System.out.println("\nВведите id счета, который хотите удалить: ");
					accountRepo.deleteAccount(scanner.nextLong());
				}
				case 5 -> {
					returnToPreviousMenu();
				}
			}
		}
		
	}
	
	private void askProperties(Account account) {
		System.out.println("\nВведите нужный вам баланс: ");
		account.setBalance(scanner.nextBigDecimal());
		
		System.out.println("\nВведите id пользователя для счета: ");
		User user = new User();
		user.setId(scanner.nextLong());
		account.setUser(user);
		
		System.out.println("\nВведите id банка счета: ");
		Bank bank = new Bank();
		bank.setId(scanner.nextLong());
		account.setBank(bank);
		
		System.out.println("\nВведите дату открытия счета: ");
		account.setOpeningDate(Date.valueOf(scanner.next()).toLocalDate());
	}

}
