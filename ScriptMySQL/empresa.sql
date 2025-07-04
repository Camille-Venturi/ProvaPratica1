Create database empresa;

USE empresa;

-- Tabela Pessoa
CREATE TABLE pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela Funcionario
CREATE TABLE funcionario (
    id INT PRIMARY KEY, -- mesmo id da pessoa
    matricula VARCHAR(4) NOT NULL UNIQUE, -- formato "F" + 3 números
    departamento VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES pessoa(id) ON DELETE RESTRICT
);

-- Tabela Projeto
CREATE TABLE projeto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    id_funcionario INT NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id) ON DELETE RESTRICT
);
