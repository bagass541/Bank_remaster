package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;

public interface AccountService {
	
	void withdraw(Account account, BigDecimal sum);
	
	void deposit(Account account, BigDecimal sum);
	
	void transfer(Account sendAccount, Account recipAccount , BigDecimal sum);
}
