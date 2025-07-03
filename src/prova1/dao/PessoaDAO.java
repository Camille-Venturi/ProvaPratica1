package prova1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import prova1.model.Pessoa;
import prova1.util.ConnectionFactory;

public class PessoaDAO {
    public void inserir(Pessoa pessoa) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pessoa.getNome());
                stmt.setString(2, pessoa.getEmail());
                stmt.executeUpdate();
                System.out.println("Pessoa cadastrada com sucesso."); 
            }
        }
    }
    // Métodos atualizar, deletar, listar, buscarPorId...
 // Listar todas as pessoas
    public List<Pessoa> listar() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM pessoa";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Pessoa p = new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                    );
                    pessoas.add(p);
                }
            }
        }
        return pessoas;
    }

    public int buscarIdPorEmail(String email) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT id FROM pessoa WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return 0; // ou -1, se preferir indicar não encontrado
    }
    
    public void deletar(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Primeiro, verifique se a pessoa é um funcionário
            String verificaFuncionario = "SELECT 1 FROM funcionario WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(verificaFuncionario)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Não é possível excluir esta pessoa porque ela está cadastrada como funcionário.");
                    System.out.println("Remova o vínculo de funcionário antes de excluir a pessoa.");
                    return;
                }
            }

            // Se não for funcionário, pode excluir normalmente
            String sql = "DELETE FROM pessoa WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Pessoa excluída com sucesso.");
                } else {
                    System.out.println("Pessoa não encontrada para exclusão.");
                }
            }
        }
    }
    
    public Pessoa buscarPorId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT id, nome, email FROM pessoa WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null; // Não encontrado
    }
    
}