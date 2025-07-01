package prova1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import prova1.model.Projeto;
import prova1.util.ConnectionFactory;

public class ProjetoDAO {
    public void inserir(Projeto projeto) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Regra 2: Projeto só pode ser criado se funcionário existe
            String sqlFunc = "SELECT * FROM funcionario WHERE id = ?";
            try (PreparedStatement stmtFunc = conn.prepareStatement(sqlFunc)) {
                stmtFunc.setInt(1, projeto.getIdFuncionario());
                ResultSet rs = stmtFunc.executeQuery();
                if (!rs.next()) {
                    System.err.println("Erro: Funcionário responsável não existe.");
                    return;
                }
            }
            String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, projeto.getNome());
                stmt.setString(2, projeto.getDescricao());
                stmt.setInt(3, projeto.getIdFuncionario());
                stmt.executeUpdate();
                System.out.println("Projeto cadastrado com sucesso.");
            }
        }
    }
 // Listar todos os projetos
    public List<Projeto> listar() throws SQLException {
        List<Projeto> projetos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM projeto";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Projeto p = new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                    );
                    projetos.add(p);
                }
            }
        }
        return projetos;
    }

    // Buscar projeto por ID
    public Projeto buscarPorId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM projeto WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                    );
                }
            }
        }
        return null; // Não encontrado
    }
    
    public void deletar(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM projeto WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("projeto excluído com sucesso.");
                } else {
                    System.out.println("Projeto não encontrado para exclusão.");
                }
            }
        }
    }
    
    // Métodos atualizar, deletar, listar, buscarPorId...
}