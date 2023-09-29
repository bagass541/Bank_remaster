package _Bank_remaster.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Bank;

public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public Account findByNumber(String number) {
		String sql = "select accounts.id, account_number, balance, user_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id "
				+ "where account_number = ?";

		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, number);

			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {

					Bank bank = new Bank();
					bank.setName(resultSet.getString("name"));
					
					Account account = Account.builder()
							.id(resultSet.getLong("id"))
							.accountNumber(resultSet.getString("account_number"))
							.openingDate(resultSet.getDate("opening_date").toLocalDate())
							.balance(resultSet.getBigDecimal("balance"))
							.bank(bank).build();
				
					return account;

				}
			}
		}
		throw new AccNotFoundException();
	}

	@Override
	public Account findById(long id) {
		String sql = "select accounts.id, account_number, balance, user_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id "
				+ "where accounts.id = ?";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			
			try(ResultSet resultSet  = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					
					Bank bank = new Bank();
					bank.setName(resultSet.getString("name"));
					
					Account account = Account.builder()
							.id(id)
							.accountNumber(resultSet.getString("account_number"))
							.openingDate(resultSet.getDate("opening_date").toLocalDate())
							.balance(resultSet.getBigDecimal("balance"))
							.bank(bank).build();
					
					return account;
	
				}
			}
		}
		throw new AccNotFoundException();
	}

	@Override
	public List<Account> findAllAccounts() {
		String sql = "select accounts.id, account_number, balance, user_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id";
		
		List<Account> accounts = new LinkedList<>();
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Bank bank = new Bank();
					bank.setName(resultSet.getString("name"));
					
					Account account = Account.builder()
							.id(resultSet.getLong("id"))
							.accountNumber(resultSet.getString("account_number"))
							.openingDate(resultSet.getDate("opening_date").toLocalDate())
							.balance(resultSet.getBigDecimal("balance"))
							.bank(bank).build();
					
					accounts.add(account);
				}
			}
		}
		return accounts;
	}

	@Override
	public void createAccount(Account account) {
		String sql = "INSERT INTO accounts(account_number, balance, user_id, bank_id, opening_date) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setBigDecimal(2, account.getBalance());
			preparedStatement.setLong(3, account.getUser().getId());
			preparedStatement.setLong(4, account.getBank().getId());
			preparedStatement.setDate(5, Date.valueOf(account.getOpeningDate()));
			
			preparedStatement.execute();
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				account.setId(generatedKeys.getLong(1));
			}
		}
		
	}

	@Override
	public void updateAccount(Account account) {
		String sql = "update accounts set account_number = ?, balance = ?, user_id = ?, bank_id = ?, "
				+ "opening_date = ? where id = ?";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setBigDecimal(2, account.getBalance());
			preparedStatement.setLong(3, account.getUser().getId());
			preparedStatement.setLong(4, account.getBank().getId());
			preparedStatement.setDate(5, Date.valueOf(account.getOpeningDate()));
			preparedStatement.setLong(6, account.getId());
			preparedStatement.execute();
		}
		
	}

	@Override
	public void deleteAccount(long id) {
		String sql = "delete from accounts where id = ?";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		}
		
	}

	

}
