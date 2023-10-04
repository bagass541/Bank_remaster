package _Bank_remaster.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import _Bank_remaster.exceptions.BankNotFoundException;
import _Bank_remaster.models.Bank;

public class BankRepositoryImpl implements BankRepository{

	private final Connection connection;
	
	public BankRepositoryImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Bank findById(long id) throws BankNotFoundException {
		String sql = "select id, name from banks where id = ? ";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setLong(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Bank bank = Bank.builder()
							.id(id)
							.name(resultSet.getString("name")).build();
					return bank;
				}
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new BankNotFoundException();
	}

	@Override
	public List<Bank> findAllBanks() {
		String sql = "select id, name from bank";
		List<Bank> banks = new LinkedList<>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				Bank bank = Bank.builder()
						.id(resultSet.getLong("id"))
						.name(resultSet.getString("name")).build();
				banks.add(bank);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return banks;
	}

	@Override
	public void createBank(Bank bank) {
		String sql = "insert into banks(name) values (?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, bank.getName());
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				bank.setId(resultSet.getLong(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateBank(Bank bank) {
		String sql = "update banks set name = ? where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, bank.getName());
			preparedStatement.setLong(2, bank.getId());
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteBank(long id) {
		String sql = "delete from banks where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
