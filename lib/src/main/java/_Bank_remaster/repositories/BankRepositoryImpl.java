package _Bank_remaster.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import _Bank_remaster.exceptions.BankNotFoundException;
import _Bank_remaster.models.Bank;

public class BankRepositoryImpl implements BankRepository{

	@Override
	public Bank findById(long id) {
		String sql = "select id, name from banks where id = ? ";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)){
			preparedStatement.setLong(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Bank bank = Bank.builder()
							.id(id)
							.name(resultSet.getString("name")).build();
					return bank;
				}
				
				
			}
		}
		throw new BankNotFoundException();
	}

	@Override
	public List<Bank> findAllBanks() {
		String sql = "select id, name from bank";
		List<Bank> banks = new LinkedList<>();
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				Bank bank = Bank.builder()
						.id(resultSet.getLong("id"))
						.name(resultSet.getString("name")).build();
				banks.add(bank);
			}
		}
		return banks;
	}

	@Override
	public void createBank(Bank bank) {
		String sql = "insert into banks(name) values (?, ?)";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, bank.getName());
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				bank.setId(resultSet.getLong(1));
			}
		}
		
	}

	@Override
	public void updateBank(Bank bank) {
		String sql = "update banks set name = ? where id = ?";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, bank.getName());
			preparedStatement.setLong(2, bank.getId());
			
			preparedStatement.execute();
		}
		
	}

	@Override
	public void deleteBank(long id) {
		String sql = "delete from banks where id = ?";
		
		try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)){
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		}
		
	}

}
