package _Bank_remaster.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import _Bank_remaster.exceptions.UserNotFoundException;
import _Bank_remaster.models.User;

public class UserRepositoryImpl implements UserRepository{
	
	private final Connection connection;
	
	

	public UserRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User findByNamePatronymicSurname(String name, String patronymic, String surname) throws UserNotFoundException {
		
		String sql = "SELECT user_id, surname, name, patronymic from users "
				+ "where surname = ? and name = ? and patronymic = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, surname);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, patronymic);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.next()) {
					User user = new User();
					// Set parameters for conditions
					user.setId(resultSet.getLong("user_id"));
					user.setSurname(resultSet.getString("surname"));
					user.setName(resultSet.getString("name"));
					user.setPatronymic(resultSet.getString("patronymic"));
					return user;
				}
			
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new UserNotFoundException();
	}

	@Override
	public User findById(long id) throws UserNotFoundException {
		String sql = "select id, name, patronymic, surname from users where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				User user = User.builder()
						.id(resultSet.getLong("id"))
						.name(resultSet.getString("name"))
						.patronymic(resultSet.getString("patronymic"))
						.surname(resultSet.getString("surname")).build();
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new UserNotFoundException();
	}

	@Override
	public List<User> findAllUsers() {
		String sql = "select id, name, patronymic, surname from users";
		List<User> users = new LinkedList<>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while(resultSet.next()) {
					User user = User.builder()
							.id(resultSet.getLong("id"))
							.name(resultSet.getString("name"))
							.patronymic(resultSet.getString("patronymic"))
							.surname(resultSet.getString("surname")).build();
					
					users.add(user);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void createUser(User user) {
		String sql = "insert into users(name, patronymic, surname) values(?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPatronymic());
			preparedStatement.setString(3, user.getSurname());
			
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				user.setId(resultSet.getLong(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateUser(User user) {
		String sql = "update users set name = ?, patronymic = ?, surname = ? where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPatronymic());
			preparedStatement.setString(3, user.getSurname());
			preparedStatement.setLong(4, user.getId());
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteUser(long id) {
		String sql = "delete from users where id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
