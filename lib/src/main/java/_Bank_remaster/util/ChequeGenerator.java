package _Bank_remaster.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;

/*
 * The class is for generating cheques.
 */
public class ChequeGenerator {

	private final String CHECKS_FOLDER = "../check\\";
	
	/*
	 * Generates a cheque in the folder - check.
	 * 
	 * @param transaction The transaction that will be demonstrated in the cheque.
	 */
	public void generateCheque(Transaction transaction) {
		File chequeFile = new File(CHECKS_FOLDER + "check_" + transaction.getId() + ".txt");
		try(PrintWriter printWriter = new PrintWriter(new FileWriter(chequeFile.getAbsolutePath()))) {
			
			printWriter.println("----------------------------------------------");
			printWriter.println("|              Банковский чек                |");
			printWriter.printf("| Чек:%38s |\n", transaction.getId());
			printWriter.printf("| %10s %31s |\n", transaction.getTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 
					transaction.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			printWriter.printf("| Тип транзакции:%27s |\n", transaction.getType().getTranslate());
			if(transaction.getType() == TransactionType.DEPOSIT || transaction.getType() ==  TransactionType.WITHDRAW) {
				printWriter.printf("| Банк:%37s |\n", transaction.getRecieverAccount().getBank().getName());
				printWriter.printf("| Счет:%37s |\n", transaction.getRecieverAccount().getAccountNumber());
			} else {
				printWriter.printf("| Банк отправителя:%25s |\n", transaction.getSenderAccount().getBank().getName());
				printWriter.printf("| Банк получателя:%26s |\n", transaction.getRecieverAccount().getBank().getName());
				printWriter.printf("| Счет отправителя:%25s |\n", transaction.getSenderAccount().getAccountNumber());
				printWriter.printf("| Счет получателя:%26s |\n", transaction.getRecieverAccount().getAccountNumber());
			}
			printWriter.printf("| Сумма:%32.2f BYN |\n", transaction.getAmount());
			printWriter.println("----------------------------------------------");
			
			System.out.println("Чек сформирован.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
