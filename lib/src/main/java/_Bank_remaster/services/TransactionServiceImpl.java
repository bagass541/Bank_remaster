package _Bank_remaster.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.repositories.TransactionRepository;

public class TransactionServiceImpl implements TransactionService{
	
	private final TransactionRepository transactionRepo;
	
	public TransactionServiceImpl(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}


	@Override
	public Transaction createTransaction(TransactionType type, Account senderAcc, Account recipAcc, BigDecimal amount) {
		Transaction transaction = Transaction.builder()
				.type(type)
				.senderAccount(senderAcc)
				.recieverAccount(recipAcc)
				.amount(amount)
				.time(LocalDateTime.now())
				.build();
		
		transactionRepo.createTransaction(transaction);
		return transaction;
	}

}
