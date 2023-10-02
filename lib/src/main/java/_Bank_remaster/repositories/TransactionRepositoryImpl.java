package _Bank_remaster.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;

public class TransactionRepositoryImpl implements TransactionRepository{

	private final Connection connection;
	
	
	public TransactionRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Transaction findById(long id)  {
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
		return null;
	}

	@Override
	public List<Transaction> findAllTransactions() {
		List<Transaction> transactions = new LinkedList<>();
		
		String sql = "select id, sender_account_id, receiver_account_id, amount, transaction_type "
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
		String sql = "insert into transactions(id, sender_account_id, receiver_account_id, amount, transaction_type) values (?, ?, ?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, transaction.getId());
			preparedStatement.setLong(2, transaction.getSenderAccount().getId());
			preparedStatement.setLong(3, transaction.getRecieverAccount().getId());
			preparedStatement.setBigDecimal(4, transaction.getAmount());
			preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
			
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
		String sql = "update transactions set sender_account_id = ?, receiver_account_id = ?, amount = ?, transaction_type = ?"
				+ " where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, transaction.getSenderAccount().getId());
			preparedStatement.setLong(2, transaction.getRecieverAccount().getId());
			preparedStatement.setBigDecimal(3, transaction.getAmount());
			preparedStatement.setObject(4, transaction.getType(), Types.OTHER);
			
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
