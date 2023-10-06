package _Bank_remaster.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Bank;
import _Bank_remaster.models.User;

public class AccountRepositoryImpl implements AccountRepository {
	
	private final Connection connection;
	
	public AccountRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Account findByNumber(String number) throws AccNotFoundException {
		String sql = "select accounts.id, account_number, balance, user_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id "
				+ "where account_number = ?";

		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new AccNotFoundException();
	}

	@Override
	public Account findById(long id) throws AccNotFoundException {
		String sql = "select accounts.id, account_number, balance, user_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id "
				+ "where accounts.id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new AccNotFoundException();
	}

	@Override
	public List<Account> findAllAccounts() {
		String sql = "select accounts.id, account_number, balance, user_id, bank_id, banks.name, opening_date from accounts "
				+ "inner join banks on banks.id = accounts.bank_id";
		
		List<Account> accounts = new LinkedList<>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while(resultSet.next()) {
					Bank bank = new Bank();
					bank.setName(resultSet.getString("name"));
					bank.setId(resultSet.getLong("bank_id"));
					
					Account account = Account.builder()
							.id(resultSet.getLong("id"))
							.accountNumber(resultSet.getString("account_number"))
							.openingDate(resultSet.getDate("opening_date").toLocalDate())
							.balance(resultSet.getBigDecimal("balance"))
							.bank(bank).build();
					
					accounts.add(account);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}
	
	public List<Account> findAccountsByUser (User user) {
		List<Account> accounts = new LinkedList<>();
		String sql = "select accounts.id, account_number, balance, user_id, surname, users.name, patronymic, bank_id, banks.name, opening_date from accounts" 
				+ " inner join banks on banks.id = accounts.bank_id"
				+ " inner join users on users.id = accounts.user_id"
				+ " where surname = ? and users.name = ? and patronymic = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, user.getSurname());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getPatronymic());
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while(resultSet.next()) {
					Bank bank = Bank.builder()
							.id(resultSet.getLong("bank_id"))
							.name(resultSet.getString("users_name"))
							.build();
					
					user.setId(resultSet.getLong("user_id"));
					
					Account account = Account.builder()
							.id(resultSet.getLong("accounts_id"))
							.accountNumber(resultSet.getString("account_number"))
							.balance(resultSet.getBigDecimal("balance"))
							.user(user)
							.bank(bank)
							.openingDate(resultSet.getDate("opening_date").toLocalDate())
							.build();
					
					accounts.add(account);
					
				}
				
				return accounts;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createAccount(Account account) {
		String sql = "INSERT INTO accounts(account_number, balance, user_id, bank_id, opening_date) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAccount(Account account) {
		String sql = "update accounts set account_number = ?, balance = ?, user_id = ?, bank_id = ?, "
				+ "opening_date = ? where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setBigDecimal(2, account.getBalance());
			preparedStatement.setLong(3, account.getUser().getId());
			preparedStatement.setLong(4, account.getBank().getId());
			preparedStatement.setDate(5, Date.valueOf(account.getOpeningDate()));
			preparedStatement.setLong(6, account.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAccount(long id) {
		String sql = "delete from accounts where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
