package projeto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import prova1.dao.FuncionarioDAO;
import prova1.dao.PessoaDAO;
import prova1.dao.ProjetoDAO;
import prova1.model.Funcionario;
import prova1.model.Pessoa;
import prova1.model.Projeto;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PessoaDAO pessoaDAO = new PessoaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ProjetoDAO projetoDAO = new ProjetoDAO();

        while (true) {
            System.out.println("===== MENU =====");
            System.out.println("1. Cadastrar pessoa (vira funcionário)");
            System.out.println("2. Listar funcionários");
            System.out.println("3. Listar projetos cadastrados");
            System.out.println("4. Excluir funcionário");
            System.out.println("5. Excluir projeto");
            System.out.println("6. Listar pessoas");
            System.out.println("7. Excluir pessoa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = input.nextInt();
            input.nextLine(); // Limpa buffer

            try {
                switch (opcao) {
                    case 1:
                        // Cadastro de Pessoa e Funcionário
                        System.out.print("Nome da pessoa: ");
                        String nome = input.nextLine();
                        System.out.print("Email da pessoa: ");
                        String email = input.nextLine();
                        Pessoa p = new Pessoa(0, nome, email);
                        pessoaDAO.inserir(p);
                        int idPessoa = p.getId();
                        if (idPessoa == 0) {
                            idPessoa = pessoaDAO.buscarIdPorEmail(email); // Implemente este método se quiser
                        }
                        System.out.print("Matrícula (formato F123): ");
                        String matricula = input.nextLine();
                        System.out.print("Departamento: ");
                        String departamento = input.nextLine();
                        Funcionario f = new Funcionario(idPessoa, nome, email, matricula, departamento);
                        funcionarioDAO.inserir(f);
                        break;
                    case 2:
                        // Listar funcionários
                        List<Funcionario> funcionarios = funcionarioDAO.listar();
                        for (Funcionario func : funcionarios) {
                            System.out.println("ID: " + func.getId() +
                                    ", Nome: " + func.getNome() +
                                    ", Email: " + func.getEmail() +
                                    ", Matrícula: " + func.getMatricula() +
                                    ", Departamento: " + func.getDepartamento());
                        }
                        break;
                        /*
                    case 3:
                    	//Buscar funcionario por id
                        System.out.print("Digite o email da pessoa: ");
                        String emailBusca = input.nextLine();
                        int idEncontrado = pessoaDAO.buscarIdPorEmail(emailBusca);
                        if (idEncontrado > 0) {
                            System.out.println("ID encontrado: " + idEncontrado);
                        } else {
                            System.out.println("Pessoa não encontrada com esse email.");
                        }
                        break;
                        */
                    case 3:
                        // Listar projetos
                        List<Projeto> projetos = projetoDAO.listar();
                        for (Projeto proj : projetos) {
                            System.out.println("ID: " + proj.getId() +
                                    ", Nome: " + proj.getNome() +
                                    ", Descrição: " + proj.getDescricao() +
                                    ", ID Funcionário: " + proj.getIdFuncionario());
                        }
                        break;
                        /*
                    case 5:
                        // Buscar projeto por ID
                        System.out.print("Digite o ID do projeto: ");
                        int idProj = input.nextInt();
                        Projeto proj = projetoDAO.buscarPorId(idProj);
                        if (proj != null) {
                            System.out.println("ID: " + proj.getId() +
                                    ", Nome: " + proj.getNome() +
                                    ", Descrição: " + proj.getDescricao() +
                                    ", ID Funcionário: " + proj.getIdFuncionario());
                        } else {
                            System.out.println("Projeto não encontrado.");
                        }
                        break;
                        */
                    case 4:
                        // Excluir funcionário
                        System.out.print("ID do funcionário a excluir: ");
                        int idFuncionario = input.nextInt();
                        funcionarioDAO.deletar(idFuncionario);
                        break;
                       
                    case 5:
                        // Excluir projeto
                        System.out.print("ID do projeto a excluir: ");
                        int idProjeto = input.nextInt();
                        projetoDAO.deletar(idProjeto);
                        break;
                        
                    case 6:
                        // Listar pessoas
                        List<Pessoa> pessoas = pessoaDAO.listar();
                        for (Pessoa pessoa : pessoas) {
                            System.out.println("ID: " + pessoa.getId() +
                                    ", Nome: " + pessoa.getNome() +
                                    ", Email: " + pessoa.getEmail());
                        }
                        break;
                    /*
                    case 9:
                        // Buscar pessoa por ID
                        System.out.print("Digite o ID da pessoa: ");
                        int idPessoaBusca = input.nextInt();
                        Pessoa pessoa = pessoaDAO.buscarIdPorEmail(idPessoaBusca);
                        if (pessoa != null) {
                            System.out.println("ID: " + pessoa.getId() +
                                    ", Nome: " + pessoa.getNome() +
                                    ", Email: " + pessoa.getEmail());
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                        break;
                        */
                    case 7:
                        // Excluir pessoa
                        System.out.print("ID da pessoa a excluir: ");
                        int idPessoaExcluir = input.nextInt();
                        pessoaDAO.deletar(idPessoaExcluir);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        input.close();
                        return;
                        
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar operação: " + e.getMessage());
            }
        }
    }
}