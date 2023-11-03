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

/*
 * The class is for generating statements.
 */
public class StatementGenerator {

	private final String STATEMENTS_FOLDER = "../statements\\";
	private final TransactionRepository transactionRepo;
	private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public StatementGenerator(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	/*
	 * Generated a formatted string that represents a table with transactions for the given account and period.
	 * 
	 * @param account The account for which to be retrieved transactions.
	 * @param period The period for which to be retrieved transactions.
	 * @return The formatted string that ready to wrtie in files.
	 * @throws SQLException If there's an issue with the SQL query execution.
	 */
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
	
	/*
	 * Generates statement in both pdf and txt formats for the given account and time period.
	 * 
	 * @param account The account for which to be retrieved transactions.
	 * @param period The period for which to be retrieved transactions.
	 * @throws SQLException if there's an issue with the SQL query execution.
	 * @throws IOException If there's an issue with file I/O.
	 * @throws DocumentException If there's an issue with PDF document generation.
	 */
	public void generate(Account account, TimePeriod period) throws SQLException, IOException, DocumentException {
		String statement = generateStatement(account, period);
		
		generateTxtStatement(account, statement);
		generatePdfStatement(account, statement);
		
	}
	
	/*
	 * Generates a formatted string with rows of transactions for given account and time period.
	 * 
	 * @param account The account for which to be retrieved transactions.
	 * @param period The period for which to be retrieved transactions.
	 * @return The formatted string that has each transaction matching the conditions. 
	 * @throws SQLException if there's an issue with the SQL query execution.
	 */
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
	
	/*
	 * Generates statement in pdf format for the given account using the provided statement text.
	 * 
	 * @param account The account for which the statement is generated.
	 * @param statement The statement text to include in the pdf file.
	 * @throws FileNotFoundException If the PDF file path cannot be found.
	 * @throws DocumentException If there's an issue with PDF document generation.
	 */
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
	
	/*
	 * Generates statement in txt format for the given account using the provided statement text.
	 * 
	 * @param account The account for which the statement is generated.
	 * @param statement The statement text to include in the txt file.
	 * @throws IOException If there's an issue with file I/O.
	 */
	private void generateTxtStatement(Account account, String statement) throws IOException {
		File file = new File(STATEMENTS_FOLDER + "statement_" + account.getAccountNumber() + ".txt");
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(statement);
		}
	}
}
	
	
