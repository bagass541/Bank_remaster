package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;

/*
 * The interface defins operations which can be performed on the bank accounts.
 */
public interface AccountService {
	
	/*
	 * Withdraws a specified amount of money from the given account.
	 * 
	 * @param account The account from which withdrawal is made.
	 * @param sum The amount to be withdrawn.
	 */
	void withdraw(Account account, BigDecimal sum);
	
	/*
	 * Deposits a specified amount of money to the given account.
	 * 
	 * @param account The account to which the deposit is made.
	 * @param sum The amount to be deposited.
	 */
	void deposit(Account account, BigDecimal sum);
	
	/*
	 * Transfers a specified amount of money from one account to the another account.
	 * 
	 * @param sendAccount The account from which money is transfered.
	 * @param recipAccount The account to which money is transfered.
	 * @param sum The amount to be transfered.
	 */
	void transfer(Account sendAccount, Account recipAccount , BigDecimal sum);
}
