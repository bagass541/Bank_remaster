package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.User;

public interface AccountRepository {
	
	Account findByNumber(String number) throws AccNotFoundException;
	
	Account findById(long id) throws AccNotFoundException;
	
	List<Account> findAllAccounts();
	
	List<Account> findAccountsByUser(User user);
	
	void createAccount(Account account);
	
	void updateAccount(Account account);
	
	void deleteAccount(long id);
}
