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
  `categoria_confiabilidade` INT NULL,
  `relacionamento` VARCHAR(100) NULL,
  `area_estudo` VARCHAR(45) NULL,
  `tipo_usuario` VARCHAR(45) NULL,
  PRIMARY KEY (`prontuario`))
ENGINE = InnoDB;

ALTER TABLE Professor AUTO_INCREMENT = 500;

-- -----------------------------------------------------
-- Table `projetopoo`.`Grupo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Grupo` (
  `idGrupo` INT NOT NULL,
  `tipo_grupo` VARCHAR(45) NULL,
  `disciplinas_relacionadas` VARCHAR(255) NULL,
  `Professor_prontuario` int NULL,
  PRIMARY KEY (`idGrupo`),
  CONSTRAINT `fk_Grupo_Professor2`
    FOREIGN KEY (`Professor_prontuario`)
    REFERENCES `projetopoo`.`Professor` (`prontuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopoo`.`Curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Curso` (
  `codigoCurso` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  `nivel` VARCHAR(45) NULL,
  `numeroSemestre` INT NULL,
  `cargaHoraria` VARCHAR(45) NULL,
  `profCoordenador` INT NULL,
  `Disciplina_codigoDisciplina` INT NOT NULL,
  PRIMARY KEY (`codigoCurso`),
  CONSTRAINT `fk_Curso_Disciplina1`
    FOREIGN KEY (`Disciplina_codigoDisciplina`)
    REFERENCES `projetopoo`.`Disciplina` (`codigoDisciplina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopoo`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Aluno` (
  `prontuario` int auto_increment unique,
  `Nome` VARCHAR(100) NULL,
  `email` VARCHAR(45) NULL,
  `categoria_confiabilidade` INT NULL,
  `relacionamento` VARCHAR(100) NULL,
  `curso` VARCHAR(45) NULL,
  `tipo_usuario` VARCHAR(45) NULL,
  `Curso_codigoCurso` INT NULL,
  PRIMARY KEY (`prontuario`),
  CONSTRAINT `fk_Aluno_Curso2`
    FOREIGN KEY (`Curso_codigoCurso`)
    REFERENCES `projetopoo`.`Curso` (`codigoCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE Aluno AUTO_INCREMENT = 100;

-- -----------------------------------------------------
-- Table `projetopoo`.`Material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopoo`.`Material` (
  `idMaterial` INT auto_increment,
  `nome` VARCHAR(45) NULL,
  `area_conhecimento` VARCHAR(45) NULL,
  `tipo_material` VARCHAR(45) NULl,
  `Aluno_prontuario` int NULL,
  PRIMARY KEY (`idMaterial`),
  CONSTRAINT `fk_Material_Aluno2`
    FOREIGN KEY (`Aluno_prontuario`)
    REFERENCES `projetopoo`.`Aluno` (`prontuario`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `projetopoo`.`Disciplinas_Aluno`(
	`Aluno_prontuario` INT NOT NULL,
    `nome` VARCHAR(45) NULL
)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SELECT * FROM DISCIPLINA;
SELECT * FROM DISCIPLINAS_ALUNO;
SELECT * FROM ALUNO;
SELECT * FROM Material;
DELETE FROM ALUNO ;
DELETE FROM DISCIPLINAS_ALUNO;
