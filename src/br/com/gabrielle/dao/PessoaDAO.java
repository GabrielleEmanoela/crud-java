package br.com.gabrielle.dao;

import br.com.gabrielle.factory.DbFactory;
import br.com.gabrielle.entities.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private Connection connection = null;


    public PessoaDAO() {
        connection = DbFactory.createConnectionMySQL();
    }

    public void insert(Pessoa pessoa) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO pessoa (nome, endereco, data_nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getEndereco());
            preparedStatement.setDate(3, new Date(pessoa.getDataNascimento().getTime()));
            preparedStatement.setString(4, pessoa.getTelefone());
            preparedStatement.setString(5, pessoa.getEmail());

            var rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    pessoa.setId(id);
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        } finally {
            DbFactory.closeResult(resultSet);
            DbFactory.closeStatement(preparedStatement);
        }


    }

    public List<Pessoa> findAll() {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT * FROM pessoa";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getInt("id"));
                pessoa.setEmail(resultSet.getString("email"));
                pessoa.setTelefone(resultSet.getString("telefone"));
                pessoa.setEndereco(resultSet.getString("endereco"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setDataNascimento(resultSet.getDate("data_nascimento"));
                pessoas.add(pessoa);
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            DbFactory.closeResult(resultSet);
            DbFactory.closeStatement(preparedStatement);
        }

        return pessoas;
    }

    public boolean update(Pessoa pessoa) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE pessoa SET nome = ?, telefone = ?, endereco = ? WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.setString(3, pessoa.getEmail());
            preparedStatement.setString(4, String.valueOf(pessoa.getId()));

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) return true;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            DbFactory.closeStatement(preparedStatement);
        }
        return false;
    }

    public boolean delete(Pessoa pessoa) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM pessoa WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pessoa.getId());

            var rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) return true;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        } finally {
            DbFactory.closeStatement(preparedStatement);
        }
        return false;
    }


}







