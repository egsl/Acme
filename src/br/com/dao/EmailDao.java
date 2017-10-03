package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.connection.ConnectionFactory;
import br.com.model.Email;

public class EmailDao {
	
	Connection connection = null;
	
	public EmailDao() {
		
		this.connection = ConnectionFactory.getConnection();
	}
	
	public boolean create(Integer idFuncionario, Email email) {
		
		String SQL = "INSERT INTO email(id_funcionario, descricao) VALUES(?, ?)";
		
		PreparedStatement statement = null;
		
		try {
			
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, idFuncionario);
			statement.setString(2, email.getDescricao());
			statement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Email Cadastrado com Sucesso!");
			return true;
			
		} catch (SQLException e) {
			
			System.err.println("Erro ao Cadastrar E-mail!" + e);
			return false;
			
		}finally {
			
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public List<Email> read(){
		
		String SQL = "SELECT * FROM email";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		List<Email> emails = new ArrayList<>();
		
		try {
			
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Email email = new Email();
				email.setId(resultSet.getInt("id_funcionario"));
				email.setDescricao(resultSet.getString("descricao"));
				emails.add(email);
			}
			
		} catch (SQLException e) {
		
			System.err.println("Erro ao Consultar E-mails Cadastrados!" + e);
			
		}finally {
			
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		
		return emails;
	}
	
	public boolean update(Email email) {
		
		String SQL = "UPDATE email SET descricao = ? WHERE id_funcionario = ?";
		
		PreparedStatement statement = null;
		
		try {
			
			statement = connection.prepareStatement(SQL);
			
			statement.setString(1, email.getDescricao());
			statement.setInt(2, email.getId());
			
			statement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Email Atualizado com Sucesso!");
			return true;
			
		} catch (SQLException e) {
			
			System.err.println("Erro ao Atualizar E-mail!" + e);
			return false;
			
		}finally {
			
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public boolean delete(Email email) {
	
	String SQL = "DELETE FROM email WHERE id_funcionario = ?";
	
	PreparedStatement statement = null;
	
	try {
		
		statement = connection.prepareStatement(SQL);
		
		statement.setInt(1, email.getId());
		statement.executeUpdate();
		
		JOptionPane.showMessageDialog(null, "Email Deletado com Sucesso!");
		return true;
		
	} catch (SQLException e) {
		
		System.err.println("Erro ao Deletar E-mail!" + e);
		return false;
		
	}finally {
		
		ConnectionFactory.closeConnection(connection, statement);
	}
}
}

