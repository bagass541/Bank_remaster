package _Bank_remaster.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.repositories.TransactionRepository;

public class StatementGenerator {

	private final String STATEMENTS_FOLDER = "../statements\\";
	private final TransactionRepository transactionRepo;
	private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public StatementGenerator(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	private String generateStatement(Account account, TimePeriod period) throws SQLException {
		LocalDate endDate = LocalDate.now();
		
		LocalDate startDate = switch(period) {
		case MONTH -> endDate.minusMonths(1);
		case YEAR -> endDate.minusYears(1);
		case ALL_TIME -> account.getOpeningDate();
		};
		
		String currentTime = DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now());
		String statement = "                            Выписка\n" +
				"                          " + account.getBank().getName() + "\n" + 
				"Клиент                      | " + account.getUser().getFullName() + "\n" +
				"Счет                        | " + account.getAccountNumber() + "\n" +
				"Валюта                      | BYN\n" + 
				"Дата открытия               | " + formatter.format(account.getOpeningDate()) + "\n" +
				"Период                      | " + formatter.format(startDate) + " - " + formatter.format(endDate) + "\n" +
				"Дата и время формирования   | " + formatter.format(LocalDate.now()) + ", " + currentTime + "\n" +
				"Остаток                     | " + account.getBalance() + " BYN\n" +
				"   Дата    |       Примечание             | Сумма\n" +
				"-----------------------------------------------------------\n" + 
				generateTransactions(account, period);
		
		return statement;
	}
	
	public void generate(Account account, TimePeriod period) throws SQLException, IOException, DocumentException {
		String statement = generateStatement(account, period);
		
		generateTxtStatement(account, statement);
		generatePdfStatement(account, statement);
		
	}
	
	private String generateTransactions(Account account, TimePeriod period) throws SQLException {
		StringBuilder builder = new StringBuilder();
		List<Transaction> transactions;

		if(period == TimePeriod.ALL_TIME) {
			transactions = transactionRepo.findTransactionsByAccount(account);
		} else {
			transactions = transactionRepo.findTransactionsByPeriodAccount(account, period);
		}
		
		for(Transaction transaction : transactions) {
			builder.append(formatter.format(transaction.getTime()));
			if(transaction.getType() == TransactionType.WITHDRAW) {
				builder
				.append(String.format(" | %-29s | ", TransactionType.WITHDRAW.getTranslate(),
						transaction.getSenderAccount().getUser().getSurname()))
				.append("-" + transaction.getAmount() + " BYN\n");
			} else if(transaction.getSenderAccount().getId() == account.getId() && transaction.getType() == TransactionType.TRANSFER){
				builder
				.append(String.format(" | %-29s | ", TransactionType.TRANSFER.getTranslate()))
				.append( "-" + transaction.getAmount() + " BYN\n");
			} else if(transaction.getRecieverAccount().getId() == account.getId() && transaction.getType() == TransactionType.TRANSFER) {
				builder
				.append(String.format(" | %-29s | ", TransactionType.TRANSFER.getTranslate() + " от " +
						transaction.getSenderAccount().getUser().getSurname()))
				.append(transaction.getAmount() + " BYN\n");
			} else {
				builder
				.append(String.format(" | %-29s | ", TransactionType.DEPOSIT.getTranslate()))
				.append(transaction.getAmount() + " BYN\n");
			}
		}
		
		return builder.toString();
	}
	private void generatePdfStatement(Account account, String statement) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(STATEMENTS_FOLDER + "statement_" + account.getAccountNumber() + ".pdf"));
		Font font = FontFactory.getFont("fonts/courier.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14);
		document.open();
		
		String[] rows = statement.split("\n");

		for(String row : rows) {
			Paragraph p = new Paragraph(row, font);

			document.add(p);
		}
		
		document.close();
	}
	
	private void generateTxtStatement(Account account, String statement) throws IOException {
		File file = new File(STATEMENTS_FOLDER + "statement_" + account.getAccountNumber() + ".txt");
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(statement);
		}
	}
}
	
	
