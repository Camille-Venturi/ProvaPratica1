package prova1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import prova1.model.Funcionario;
import prova1.util.ConnectionFactory;

public class FuncionarioDAO {
    public void inserir(Funcionario funcionario) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Regra 1: Verificar se id_pessoa existe na tabela pessoa
            String sqlPessoa = "SELECT * FROM pessoa WHERE id = ?";
            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                stmtPessoa.setInt(1, funcionario.getId());
                ResultSet rs = stmtPessoa.executeQuery();
                if (!rs.next()) {
                    System.err.println("Erro: Pessoa com ID " + funcionario.getId() + " não existe.");
                    return;
                }
            }

            String sql = "INSERT INTO funcionario (id, matricula, departamento) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, funcionario.getId());
                stmt.setString(2, funcionario.getMatricula());
                stmt.setString(3, funcionario.getDepartamento());
                stmt.executeUpdate();
                System.out.println("Funcionário cadastrado com sucesso.");
            }
        }
    }

    public void deletar(int idFuncionario) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sqlCheck = "SELECT * FROM projeto WHERE id_funcionario = ?";
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
                stmtCheck.setInt(1, idFuncionario);
                ResultSet rs = stmtCheck.executeQuery();
                if (rs.next()) {
                    System.err.println("Erro: Funcionário vinculado a projeto não pode ser excluído.");
                    return;
                }
            }
            String sql = "DELETE FROM funcionario WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idFuncionario);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Funcionário excluído com sucesso.");
                } else {
                    System.err.println("Funcionário não encontrado.");
                }
            }
        }
    }
    
 // Listar todos os funcionários
    public List<Funcionario> listar() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                         "FROM funcionario f JOIN pessoa p ON f.id = p.id";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                    );
                    funcionarios.add(f);
                }
            }
        }
        return funcionarios;
    }

    // Buscar funcionário por ID
    public Funcionario buscarIdPorEmail(String email) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                         "FROM funcionario f JOIN pessoa p ON f.id = p.id WHERE p.email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                    );
                }
            }
        }
        return null; // Não encontrado
    }
    // Métodos atualizar, listar, buscarPorId...
}
