package _Bank_remaster.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import _Bank_remaster.exceptions.TransactionNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;
import _Bank_remaster.models.User;
import _Bank_remaster.util.TimePeriod;

public class TransactionRepositoryImpl implements TransactionRepository{

	private final Connection connection;
	
	
	public TransactionRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Transaction findById(long id) throws TransactionNotFoundException  {
		String sql = "select time, sender_account_id,  receiver_account_id, amount, transaction_type "
				+ " from transactions where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Account senderAcc = new Account(); 
					Account receiverAcc = new Account();
					
					senderAcc.setId(resultSet.getLong("sender_account_id"));
					receiverAcc.setId(resultSet.getLong("receiver_account_id"));
					
					Transaction transaction = Transaction.builder()
							.id(id)
							.time(resultSet.getTimestamp("time").toLocalDateTime())
							.senderAccount(senderAcc)
							.recieverAccount(receiverAcc)
							.amount(resultSet.getBigDecimal("amount"))
							.type(TransactionType.valueOf(resultSet.getString("transaction_type"))).build();
							
					return transaction;
							
				}
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new TransactionNotFoundException();
	}
	
	@Override
	public List<Transaction> findTransactionsByAccount(Account account) throws SQLException {
		List<Transaction> transactions = new LinkedList<>();
		String sql = "select transactions.id as tr_id, time, sender_account_id, s_acc.user_id as s_user_id, s_user.surname as s_user_surname,"
				+ " receiver_account_id, r_acc.user_id as r_user_id,"
				+ " r_user.surname as r_user_surname,  amount, transaction_type from transactions"
				+ " inner join accounts as s_acc on s_acc.id = transactions.sender_account_id"
				+ " inner join accounts as r_acc on r_acc.id = transactions.receiver_account_id"
				+ " inner join users as s_user on s_user.id = s_acc.user_id"
				+ " inner join users as r_user on r_user.id = r_acc.user_id"
				+ " where sender_account_id = ? or receiver_account_id = ? order by time desc";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, account.getId());
			preparedStatement.setLong(2, account.getId());
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while(resultSet.next()) {
					
					User senderUser = new User();
					senderUser.setSurname(resultSet.getString("s_user_surname"));
					
					User receiverUser = new User();
					receiverUser.setSurname(resultSet.getString("r_user_surname"));
					
					Account senderAcc = new Account();
					senderAcc.setUser(senderUser);
					
					Account receiverAcc = new Account();
					receiverAcc.setUser(receiverUser);
					
					Transaction transaction = Transaction.builder()
							.id(resultSet.getLong("tr_id"))
							.time(resultSet.getTimestamp("time").toLocalDateTime())
							.senderAccount(senderAcc)
							.recieverAccount(receiverAcc)
							.amount(resultSet.getBigDecimal("amount"))
							.type(TransactionType.valueOf(resultSet.getString("transaction_type"))).build();
							
					transactions.add(transaction);
					
				}
			}
		}
		return transactions;
	}
	
	@Override
	public List<Transaction> findTransactionsByPeriodAccount(Account account, TimePeriod period) throws SQLException {
		List<Transaction> transactions = new LinkedList<>();
		
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = switch(period) {
		case MONTH -> endDate.minusMonths(1);
		case YEAR -> endDate.minusYears(1);
		case ALL_TIME -> account.getOpeningDate();
		};
		
		String sql = "select transactions.id as tr_id, time, sender_account_id, s_acc.user_id as s_user_id, s_user.surname as s_user_surname,"
				+ " receiver_account_id, r_acc.user_id as r_user_id,"
				+ " r_user.surname as r_user_surname,  amount, transaction_type from transactions"
				+ " inner join accounts as s_acc on s_acc.id = transactions.sender_account_id"
				+ " inner join accounts as r_acc on r_acc.id = transactions.receiver_account_id"
				+ " inner join users as s_user on s_user.id = s_acc.user_id"
				+ " inner join users as r_user on r_user.id = r_acc.user_id"
				+ " where (sender_account_id = ? or receiver_account_id = ?) and time > ? order by time desc";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, account.getId());
			preparedStatement.setLong(2, account.getId());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(startDate.toString()));

			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				while(resultSet.next()) {
					
					User senderUser = new User();
					senderUser.setSurname(resultSet.getString("s_user_surname"));
					
					User receiverUser = new User();
					receiverUser.setSurname(resultSet.getString("r_user_surname"));
					
					Account senderAcc = new Account();
					senderAcc.setUser(senderUser);
					
					Account receiverAcc = new Account();
					receiverAcc.setUser(receiverUser);
					
					Transaction transaction = Transaction.builder()
							.id(resultSet.getLong("tr_id"))
							.time(resultSet.getTimestamp("time").toLocalDateTime())
							.senderAccount(senderAcc)
							.recieverAccount(receiverAcc)
							.amount(resultSet.getBigDecimal("amount"))
							.type(TransactionType.valueOf(resultSet.getString("transaction_type"))).build();
							
					transactions.add(transaction);
					
				}
			}
		}
		return transactions;
	}

	@Override
	public List<Transaction> findAllTransactions() {
		List<Transaction> transactions = new LinkedList<>();
		
		String sql = "select id, time, sender_account_id, receiver_account_id, amount, transaction_type "
				+ "from transactions";
		
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			try(ResultSet resultSet = statement.executeQuery()) {
				
				while(resultSet.next()) {
					Account senderAcc = new Account();
					Account receiverAcc = new Account();
					
					senderAcc.setId(resultSet.getLong("sender_account_id"));
					receiverAcc.setId(resultSet.getLong("receiver_account_id"));
	
					Transaction transaction = Transaction.builder()
							.id(resultSet.getLong("id"))
							.time(resultSet.getTimestamp("time").toLocalDateTime())
							.senderAccount(senderAcc)
							.recieverAccount(receiverAcc)
							.amount(resultSet.getBigDecimal("amount"))
							.type(TransactionType.valueOf(resultSet.getString("transaction_type"))).build();
					transactions.add(transaction);
									
				}
				return transactions;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createTransaction(Transaction transaction) {
		String sql = "insert into transactions(time, sender_account_id, receiver_account_id, amount, transaction_type) values (?, ?, ?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setTimestamp(1, Timestamp.valueOf(transaction.getTime()));
			preparedStatement.setLong(2, transaction.getSenderAccount().getId());
			preparedStatement.setLong(3, transaction.getRecieverAccount().getId());
			preparedStatement.setBigDecimal(4, transaction.getAmount());
			preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
			
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			
			if(resultSet.next()) {
				transaction.setId(resultSet.getLong(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		String sql = "update transactions set time = ?, sender_account_id = ?, receiver_account_id = ?, amount = ?, transaction_type = ?"
				+ " where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setTimestamp(1, Timestamp.valueOf(transaction.getTime()));
			preparedStatement.setLong(2, transaction.getSenderAccount().getId());
			preparedStatement.setLong(3, transaction.getRecieverAccount().getId());
			preparedStatement.setBigDecimal(4, transaction.getAmount());
			preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
			preparedStatement.setLong(6, transaction.getId());
			
			preparedStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteTransaction(long id) {
		String sql = "delete from transactions where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	

}
