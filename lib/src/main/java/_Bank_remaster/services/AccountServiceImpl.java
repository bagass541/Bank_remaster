package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.repositories.AccountRepository;
import _Bank_remaster.util.ChequeGenerator;

public class AccountServiceImpl implements AccountService{
	
	private final AccountRepository accountRepo;
	private final TransactionService transactionService;
	private final ChequeGenerator chequeGenerator;
	
	public AccountServiceImpl(AccountRepository accountRepo, TransactionService transactionService, ChequeGenerator chequeGenerator) {
		this.accountRepo = accountRepo;
		this.transactionService = transactionService;
		this.chequeGenerator = chequeGenerator;
	}

	@Override
	public void withdraw(Account account, BigDecimal sum) {
		account.setBalance(account.getBalance().subtract(sum));
		accountRepo.updateAccount(account);
		chequeGenerator.generateCheque(transactionService.createTransaction(TransactionType.WITHDRAW, account, account, sum));
	}

	@Override
	public void deposit(Account account, BigDecimal sum) {
		account.setBalance(account.getBalance().add(sum));
		accountRepo.updateAccount(account);
		chequeGenerator.generateCheque(transactionService.createTransaction(TransactionType.DEPOSIT, account, account, sum));
	}

	@Override
	public void transfer(Account sendAccount, Account recipAccount, BigDecimal sum) {
		
		Object firstLock = sendAccount.getId() < recipAccount.getId() ? recipAccount.getId() : sendAccount.getId();
		Object secondLock = sendAccount.getId() < recipAccount.getId() ? sendAccount.getId() : recipAccount.getId();
		
		synchronized (firstLock) {
			synchronized (secondLock) {
				sendAccount.setBalance(sendAccount.getBalance().subtract(sum));
				recipAccount.setBalance(recipAccount.getBalance().add(sum));
				
				accountRepo.updateAccount(sendAccount);
				accountRepo.updateAccount(recipAccount);
			}
		}
		
		chequeGenerator.generateCheque(transactionService.createTransaction(TransactionType.TRANSFER, sendAccount, recipAccount, sum));
	}

	

}
