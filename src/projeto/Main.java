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
	public static void main(String[] args) throws SQLException {
		Scanner input = new Scanner(System.in);
		PessoaDAO pessoaDAO = new PessoaDAO();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		List<Funcionario> funcionarios = funcionarioDAO.listar();

		while (true) {
			try {
				switch (menu(input)) {
				case 1:
					// Cadastro de Pessoa e Funcionário
					System.out.print("Nome da pessoa: ");
					String nome = input.nextLine();
					System.out.print("Email da pessoa: ");
					String email = input.nextLine();
					Pessoa p = new Pessoa(0, nome, email);
					pessoaDAO.inserir(p);
				case 2:
					// Listar funcionários
					
					for (Funcionario func : funcionarios) {
						System.out.println("ID: " + func.getId() + ", Nome: " + func.getNome() + ", Email: "
								+ func.getEmail() + ", Matrícula: " + func.getMatricula() + ", Departamento: "
								+ func.getDepartamento());
					}
					break;
					
				case 3:
					
					System.out.println("Pessoas disponíveis para adicionar");
					List<Pessoa> pessoas = pessoaDAO.listar();
					for (Pessoa pessoa : pessoas) {
						System.out.println("ID: " + pessoa.getId() + ", Nome: " + pessoa.getNome() + ", Email: "
								+ pessoa.getEmail());
					}
					
					System.out.println("Insira o id da pessoa:");
					int idTemp = input.nextInt();
					
					Pessoa pessoa = pessoaDAO.buscarPorId(idTemp);

					if (pessoa == null) {
					    System.out.println("Pessoa não encontrada. Cadastre-a primeiro como pessoa.");
					} else {
					    System.out.println("Pessoa encontrada: " + pessoa.getNome() + " (ID: " + pessoa.getId() + ")");
					    System.out.print("Digite o departamento do funcionário: ");
					    String departamento = input.next();

					    String matricula = "F" + pessoa.getId();

					    Funcionario funcionario = new Funcionario(
					        pessoa.getId(),
					        pessoa.getNome(),
					        pessoa.getEmail(),
					        matricula,
					        departamento
					    );
					    funcionarioDAO.inserir(funcionario);

					    System.out.println("Funcionário cadastrado com sucesso! Matrícula: " + matricula);
					}
					break;
				case 4:
					// Listar projetos
					List<Projeto> projetos = projetoDAO.listar();
					for (Projeto proj : projetos) {
						System.out.println("ID: " + proj.getId() + ", Nome: " + proj.getNome() + ", Descrição: "
								+ proj.getDescricao() + ", ID Funcionário: " + proj.getIdFuncionario());
					}
					break;
				case 5:
					// Excluir funcionário
					List<Funcionario> funcionarioL = funcionarioDAO.listar();
					for (Funcionario func : funcionarioL) {
						System.out.println("ID: " + func.getId() + ", Nome: " + func.getNome() + ", Email: "
								+ func.getEmail() + ", Matrícula: " + func.getMatricula() + ", Departamento: "
								+ func.getDepartamento());
					}
					System.out.print("ID do funcionário a excluir: ");
					int idFuncionario = input.nextInt();
					funcionarioDAO.deletar(idFuncionario);
					break;

				case 6:
					// Excluir projeto
					System.out.print("ID do projeto a excluir: ");
					int idProjeto = input.nextInt();
					projetoDAO.deletar(idProjeto);
					break;
					
				case 7:
					//Adicionar projeto
					System.out.print("Nome do projeto: ");
					String nomeProjeto = input.nextLine();
					System.out.print("Descrição do projeto: ");
					String descricaoProjeto = input.nextLine();
					for (Funcionario func : funcionarios) {
						System.out.println("ID: " + func.getId() + ", Nome: " + func.getNome() + ", Email: "
								+ func.getEmail() + ", Matrícula: " + func.getMatricula() + ", Departamento: "
								+ func.getDepartamento());
					}
					System.out.print("ID da pessoa responsável: ");
					int idPessoaResponsavel = Integer.parseInt(input.nextLine());

					// Você pode validar se a pessoa existe antes:
					Pessoa responsavel = pessoaDAO.buscarPorId(idPessoaResponsavel);
					if (responsavel == null) {
					    System.out.println("Pessoa não encontrada. Cadastre a pessoa primeiro.");
					} else {
					    Projeto projeto = new Projeto(0, nomeProjeto, descricaoProjeto, idPessoaResponsavel);
					    projetoDAO.inserir(projeto);
					    System.out.println("Projeto cadastrado com sucesso!");
					}
				    break;

					
				case 8:
					// Excluir pessoa
                    List<Pessoa> pessoasL = pessoaDAO.listar();
                    for (Pessoa pessoaI : pessoasL) {
                        System.out.println("ID: " + pessoaI.getId() +
                                ", Nome: " + pessoaI.getNome() +
                                ", Email: " + pessoaI.getEmail());
                    }
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
	
	public static int menu(Scanner input) {
		System.out.println("\n.............MENU..............");
		System.out.println("1. Cadastrar pessoa");
		System.out.println("2. Listar funcionários");
		System.out.println("3. Cadastrar funcionário");
		System.out.println("4. Listar projetos cadastrados");
		System.out.println("5. Excluir funcionário");
		System.out.println("6. Excluir projeto");
		System.out.println("7. Cadastrar projeto");
		System.out.println("8. Excluir pessoa (só para teste)");
		System.out.println("0. Sair");
		System.out.print("Escolha uma opção: ");
		int opcao = input.nextInt();
		input.nextLine(); 
		return opcao;
	}
	
}