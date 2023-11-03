package _Bank_remaster.menu;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import _Bank_remaster.exceptions.TransactionNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.repositories.TransactionRepository;

/*
 * The transaction menu for CRUD-operations with transactions.
 */
public class TransactionMenu extends CRUDMenu {
	
	private final TransactionRepository transactionRepo;

	private final String MENU = """
			\n----------------------------
			1: Просмотреть все транзакции
			2: Добавить транзакцию
			3: Изменить транзакцию
			4: Удалить транзакцию
			5: Вернуться назад
			---------------------------- """;
	
	
	
	public TransactionMenu(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}



	@Override
	public void start() {
		while(true) {
			printMenu(MENU);
			switch(scanner.nextInt()) {
				case 1 -> {
					transactionRepo.findAllTransactions().forEach(System.out::println);
				}
				case 2 -> {
					Transaction transaction = new Transaction();
					askProperties(transaction);
					transactionRepo.createTransaction(transaction);
				}
				case 3 -> {
					Transaction transaction = null;
					System.out.println("\nВведите id тразнакции, которую нужно изменить: ");
					try {
						transaction = transactionRepo.findById(scanner.nextLong());
					} catch (TransactionNotFoundException e) {
						System.out.println("\nТранзакция не найдена");
						break;
					}
					
					askProperties(transaction);
					
					transactionRepo.updateTransaction(transaction);
					
				}
				case 4 -> {
					System.out.println("\nВведите id транзакции, которую нужно удалить: ");
					transactionRepo.deleteTransaction(scanner.nextLong());
				}
				case 5 -> {
					returnToPreviousMenu();
				}
			}
		}
	}
	
	/*
	 * Requests user input and sets properties for an transaction.
	 * 
	 * @param transaction The transaction for which properties need to be set.
	 */
	private void askProperties(Transaction transaction) {
		System.out.println("\nВведите дату транзакции: ");
		LocalDate date = Date.valueOf(scanner.next()).toLocalDate();
		
		System.out.println("\nВведите время транзакции: ");
		LocalTime time = Time.valueOf(scanner.next()).toLocalTime();
		
		transaction.setTime(LocalDateTime.of(date, time));
		
		System.out.println("\nВведите id счета отправителя: ");
		Account sendAcc = new Account();
		sendAcc.setId(scanner.nextLong());
		transaction.setSenderAccount(sendAcc);
		
		System.out.println("\nВведите id счета получателя: ");
		Account recipAcc = new Account();
		recipAcc.setId(scanner.nextLong());
		transaction.setRecieverAccount(recipAcc);
		
		System.out.println("\nВведите сумму транзакции: ");
		transaction.setAmount(scanner.nextBigDecimal());
		
		System.out.println("\nВведите тип транзакции: ");
		transaction.setType(TransactionType.valueOf(scanner.next()));
	}

}
