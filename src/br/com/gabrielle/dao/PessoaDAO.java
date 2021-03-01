package br.com.gabrielle.dao;

import br.com.gabrielle.conection.FactoryConnection;
import br.com.gabrielle.entities.Pessoa;

import java.sql.*;
   import java.util.List;

public class PessoaDAO {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    //Metodo Inserir.

    public void insert(Pessoa pessoa) {

        String sql = "INSERT INTO pessoa (nome, endereco, data_nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";


        try {
            connection = new FactoryConnection().createConnectionMySQL();
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
            FactoryConnection.closeResult(resultSet);
            FactoryConnection.closeStatement(preparedStatement);
        }


    }

    //Buscar todos
    public List<Pessoa> buscarPessoas() {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        String sql = "SELECT * FROM pessoa";
        try {
            connection = new FactoryConnection().createConnectionMySQL();

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
            FactoryConnection.closeResult(resultSet);
            FactoryConnection.closeStatement(preparedStatement);
        }

        return pessoas;
    }
    //Metodo Alterar

    public boolean update(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome =?, telefone = ?, endereco = ?, email = ?";

        try {
            connection = new FactoryConnection().createConnectionMySQL();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.setString(3, pessoa.getEndereco());
            preparedStatement.setString(4, pessoa.getEmail());
            var rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) return true;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            FactoryConnection.closeResult(resultSet);
            FactoryConnection.closeStatement(preparedStatement);
        }
        return false;
    }
    // Comando delete.

    public boolean delete(Pessoa pessoa) {
        try {
            String sql = "DELETE FROM pessoa WHERE id = ?";
            connection = new FactoryConnection().createConnectionMySQL();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pessoa.getId());

            var rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) return true;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        } finally {
            FactoryConnection.closeResult(resultSet);
            FactoryConnection.closeStatement(preparedStatement);
        }

        return false;

    }


}







