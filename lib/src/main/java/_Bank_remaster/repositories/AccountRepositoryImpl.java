package _Bank_remaster.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Bank;

public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public Account findByNumber(String number) {
		String sql = "select account_id, bank_id, name_bank, number_acc, opening_date, money from  accounts";

		try(PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement(sql)) {
			preparedStatement.setString(1, number);

			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Account account = new Account();
					Bank bank = new Bank();
					
					bank.setId(resultSet.getLong("bank_id"));
					bank.setName(resultSet.getString("name_bank"));
					
					account.setId(resultSet.getLong("account_id"));
					account.setAccountNumber(resultSet.getString("number_acc"));
					account.setOpeningDate(resultSet.getDate("opening_date").toLocalDate());
					account.setBalance(resultSet.getBigDecimal("money"));
					account.setBank(bank);
				
					return account;

				}
			}
		}
		throw new BankAccNotFoundException();;
	}

	@Override
	public Account findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAccount(Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAccount(long id) {
		// TODO Auto-generated method stub
		
	}

	

}
