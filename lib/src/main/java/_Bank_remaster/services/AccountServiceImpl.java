package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.repositories.AccountRepository;

public class AccountServiceImpl implements AccountService{
	
	private final AccountRepository accountRepo;
	private final TransactionService transactionService;
	
	public AccountServiceImpl(AccountRepository accountRepo, TransactionService transactionService) {
		this.accountRepo = accountRepo;
		this.transactionService = transactionService;
	}

	@Override
	public void withdraw(Account account, BigDecimal sum) {
		account.setBalance(account.getBalance().subtract(sum));
		accountRepo.updateAccount(account);
		
		transactionService.createTransaction(TransactionType.WITHDRAW, account, account, sum);
	}

	@Override
	public void deposit(Account account, BigDecimal sum) {
		account.setBalance(account.getBalance().add(sum));
		accountRepo.updateAccount(account);
		
		transactionService.createTransaction(TransactionType.DEPOSIT, account, account, sum);
	}

	@Override
	public void transfer(Account sendAccount, Account recipAccount, BigDecimal sum) {
		sendAccount.setBalance(sendAccount.getBalance().subtract(sum));
		recipAccount.setBalance(recipAccount.getBalance().add(sum));
		
		accountRepo.updateAccount(sendAccount);
		accountRepo.updateAccount(recipAccount);
		
		transactionService.createTransaction(TransactionType.TRANSFER, sendAccount, recipAccount, sum);
		
	}

	

}
