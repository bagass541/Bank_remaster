package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.models.Account;

public interface AccountRepository {
	
	Account findByNumber(String number);
	
	Account findById(long id);
	
	List<Account> findAllAccounts();
	
	void createAccount(Account account);
	
	void updateAccount(Account account);
	
	void deleteAccount(long id);
}
