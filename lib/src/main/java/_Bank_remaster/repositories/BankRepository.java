package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.models.Bank;

public interface BankRepository {
	
	Bank findById(long id);
	
	List<Bank> findAllBanks();
	
	void createBank(Bank bank);
	
	void updateBank(Bank bank);
	
	void deleteBank(long id);
}
