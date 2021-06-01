-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projetopoo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projetopoo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projetopoo` DEFAULT CHARACTER SET utf8 ;
USE `projetopoo` ;

-- -----------------------------------------------------
-- Table `projetopoo`.`Professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Professor` (
  `prontuario` int auto_increment unique,
  `Nome` VARCHAR(100) NULL,
  `email` VARCHAR(45) NULL,
  `area_estudo` VARCHAR(45) NULL,
  `disciplina` VARCHAR(45) NULL,
  `tipo_usuario` VARCHAR(45) NULL,
  PRIMARY KEY (`prontuario`))
ENGINE = InnoDB;

ALTER TABLE Professor AUTO_INCREMENT = 500;

-- -----------------------------------------------------
-- Table `projetopoo`.`Grupo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Grupo` (
  `id_grupo` INT auto_increment,
  `nome_grupo` VARCHAR(45) NOT NULL,
  `tipo_grupo` VARCHAR(45) NULL,
  `nome_professor` VARCHAR(45) NOT NULL,
  `nome_disciplina` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_grupo`)
  )
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `projetopoo`.`Grupo_list_alunos` (
  `id_grupo` INT,
  `nome_aluno` VARCHAR(45) NOT NULL,
  `prontuario_aluno`int NOT NULL,
  CONSTRAINT `fk_id_grupo`
    FOREIGN KEY (`id_grupo`)
    REFERENCES `projetopoo`.`Grupo` (`id_grupo`)
    ON DELETE cascade
    ON UPDATE cascade,
    FOREIGN KEY (`prontuario_aluno`) REFERENCES Aluno(`prontuario`)
    ON DELETE cascade
    ON UPDATE cascade
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `projetopoo`.`Disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Disciplina` (
  `codigoDisciplina` INT auto_increment,
  `nome` VARCHAR(45) NULL,
  `carga_horaria` VARCHAR(45) NULL,
  `Professor` VARCHAR(45) NULL,
  `Professor_prontuario` int NULL,
  PRIMARY KEY (`codigoDisciplina`),
  CONSTRAINT `fk_Disciplina_Professor2`
    FOREIGN KEY (`Professor_prontuario`)
    REFERENCES `projetopoo`.`Professor` (`prontuario`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;


-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `projetopoo`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Aluno` (
  `prontuario` int auto_increment unique,
  `Nome` VARCHAR(100) NULL,
  `email` VARCHAR(45) NULL,
  `curso` VARCHAR(45) NULL,
  `tipo_usuario` VARCHAR(45) NULL
  )
ENGINE = InnoDB;

ALTER TABLE Aluno AUTO_INCREMENT = 100;

CREATE TABLE IF NOT EXISTS `projetopoo`.`Disciplinas_Aluno`(
	`aluno_prontuario` INT NOT NULL,
    `aluno_nome` VARCHAR(45),
    `nome` VARCHAR(45) NULL
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `projetopoo`.`Material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Material` (
  `idMaterial` INT auto_increment,
  `nome` VARCHAR(45) NULL,
  `area_conhecimento` VARCHAR(45) NULL,
  `tipo_material` VARCHAR(45) NULL,
  `Aluno_prontuario` int NULL,
  PRIMARY KEY (`idMaterial`),
  CONSTRAINT `fk_Material_Aluno2`
    FOREIGN KEY (`Aluno_prontuario`)
    REFERENCES `projetopoo`.`Aluno` (`prontuario`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `projetopoo`.`Conteudos_disciplina`(
	`disciplina` VARCHAR(45) NOT NULL,
    `conteudo` VARCHAR(45) NOT NULL,
    `nome_professor` VARCHAR(45) NOT NULL
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `projetopoo`.`Relacionamentos`(
	`usuario_prontuario` INT NOT NULL,
    `nome_usuario` VARCHAR(45) NOT NULL,
    `nome_outro_usuario_aluno` VARCHAR(45) NULL,
    `prontuario_outro_usuario_aluno` INT NULL,
    `nome_outro_usuario_professor` VARCHAR(45) NULL,
    `prontuario_outro_usuario_professor` INT NULL,
    `grau_confiabilidade` VARCHAR(45) NOT NULL,
    FOREIGN KEY (`prontuario_outro_usuario_aluno`) REFERENCES Aluno(Prontuario)
    ON DELETE cascade
    ON UPDATE cascade,
    FOREIGN KEY (`prontuario_outro_usuario_professor`) REFERENCES Professor(Prontuario)
    ON DELETE cascade
    ON UPDATE cascade
)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SELECT * FROM DISCIPLINA;
SELECT * FROM DISCIPLINAS_ALUNO; #Lista de disciplinas em que um aluno se cadastrou
SELECT * FROM ALUNO;
SELECT * FROM PROFESSOR;
SELECT * FROM Material;
SELECT * FROM CONTEUDOS_DISCIPLINA;
SELECT * FROM Grupo_list_alunos; #Lista de alunos por grupo
SELECT * FROM GRUPO;
SELECT * FROM RELACIONAMENTOS;

#Usuário com mais relacionamentos
SELECT usuario_prontuario, (count(nome_outro_usuario_aluno)+count(nome_outro_usuario_professor)) as qtd  FROM Relacionamentos
group by usuario_prontuario
order by (select count(nome_outro_usuario_aluno) from Relacionamentos )+(select count(nome_outro_usuario_professor) from Relacionamentos) desc;

#Grupo com mais usuários
select Grupo.nome_grupo, count(Grupo_list_alunos.id_grupo) as 'qtd' from Grupo_list_alunos
INNER JOIN Grupo
ON Grupo_list_alunos.id_grupo = Grupo.id_grupo
group by Grupo_list_alunos.id_grupo
order by count(Grupo_list_alunos.id_grupo) desc;

DELETE FROM ALUNO ;
DELETE FROM DISCIPLINAS_ALUNO;
DELETE FROM PROFESSOR;