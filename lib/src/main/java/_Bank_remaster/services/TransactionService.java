package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;

public interface TransactionService {
	
	public Transaction createTransaction(TransactionType type, Account senderAcc, Account recipAcc, BigDecimal amount);

}
