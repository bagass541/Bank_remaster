package _Bank_remaster.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.text.DateFormatter;

import _Bank_remaster.models.Account;
import _Bank_remaster.repositories.TransactionRepository;

public class StatementGenerator {

	private final TransactionRepository transactionRepo;
	private final SimpleDateFormat formatter  = new SimpleDateFormat("dd.MM.yyyy");

	public StatementGenerator(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	private String generateStatement(Account account, TimePeriod period) {
		LocalDate endDate = LocalDate.now();
		
		LocalDate startDate = switch(period) {
		case MONTH -> endDate.minusMonths(1);
		case YEAR -> endDate.minusYears(1);
		case ALL_TIME -> account.getOpeningDate();
		};
		
		
		LocalTime currentTime = LocalTime.now();
		String statement = " Выписка\n " +
				"Клиент                      | " + account.getUser().getFullName() + "\n" +
				"Счет                        | " + account.getAccountNumber() + "\n" +
				"Валюта                      | BYN\n" + 
				"Дата открытия               | " + formatter.format(account.getOpeningDate()) + "\n" +
				"Период                      | " + formatter.format(startDate) + " - " + formatter.format(endDate) + "\n" +
				"Дата и время формирования   | " + formatter.format(LocalDate.now()) + ", " + currentTime.getHour() + "." + currentTime.getMinute() +
				"Остаток                     | " +
	}
	
	public void generate() {
		
	}
	
	private void generateTransactions() {
		
	}
	
	private void generatePdfStatement() {
		
	}
	
	private void generateTxtStatement() {
		
	}
}
	
	
