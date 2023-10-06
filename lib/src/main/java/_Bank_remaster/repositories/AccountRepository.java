package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;

public interface AccountRepository {
	
	Account findByNumber(String number) throws AccNotFoundException;
	
	Account findById(long id) throws AccNotFoundException;
	
	List<Account> findAllAccounts();
	
	void createAccount(Account account);
	
	void updateAccount(Account account);
	
	void deleteAccount(long id);
}
