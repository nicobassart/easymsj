SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `instmessenger` DEFAULT CHARACTER SET latin1 ;
USE `instmessenger` ;

-- -----------------------------------------------------
-- Table `instmessenger`.`agenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`agenda` (
  `id_agenda` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(30) NOT NULL,
  `descripcion` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id_agenda`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'latin1_swedish_ci';


-- -----------------------------------------------------
-- Table `instmessenger`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`perfil` (
  `nombre` VARCHAR(45) NULL,
  `id_perfil` INT(11) NOT NULL,
  PRIMARY KEY (`id_perfil`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instmessenger`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`usuario` (
  `nombre` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `id_perfil` INT(11) NULL,
  `id_persona` INT(11) NULL,
  `id_usuario` INT(11) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  CONSTRAINT `fk_usuario_perfil1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `instmessenger`.`perfil` (`id_perfil`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instmessenger`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`persona` (
  `id_persona` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NOT NULL,
  `apellido` VARCHAR(20) NOT NULL,
  `mail` VARCHAR(50) NULL,
  `celular` VARCHAR(20) NULL,
  `tipoDocumento` VARCHAR(5) NULL,
  `numeroDocumento` VARCHAR(20) NULL,
  `estado` VARCHAR(20) NULL,
  `usuario_id_usuario` INT(11) NULL,
  PRIMARY KEY (`id_persona`),
  INDEX `numeroDocumento` (`numeroDocumento` ASC),
  INDEX `fk_persona_usuario1_idx` (`usuario_id_usuario` ASC),
  CONSTRAINT `fk_persona_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `instmessenger`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 32
DEFAULT CHARACTER SET = latin1
COMMENT = 'latin1_swedish_ci';


-- -----------------------------------------------------
-- Table `instmessenger`.`mensaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`mensaje` (
  `id_mensaje` INT(11) NOT NULL,
  `id_aux_agenda_persona` INT(11) NULL,
  `texto` VARCHAR(250) NULL,
  `sentido` VARCHAR(3) NULL,
  PRIMARY KEY (`id_mensaje`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instmessenger`.`aux_agenda_persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instmessenger`.`aux_agenda_persona` (
  `id_agenda` INT(11) NOT NULL,
  `id_persona` INT(11) NOT NULL,
  `campo1` VARCHAR(45) NULL,
  `campo2` VARCHAR(45) NULL,
  `campo3` VARCHAR(45) NULL,
  `campo4` VARCHAR(45) NULL,
  `id_aux_agenda_persona` INT(11) NOT NULL AUTO_INCREMENT,
  `mensaje_id_mensaje` INT(11) NULL,
  PRIMARY KEY (`id_aux_agenda_persona`),
  INDEX `fk_aux_agenda_persona_mensaje1_idx` (`mensaje_id_mensaje` ASC),
  CONSTRAINT `agenda_persona_ibfk_1`
    FOREIGN KEY (`id_agenda`)
    REFERENCES `instmessenger`.`agenda` (`id_agenda`),
  CONSTRAINT `agenda_persona_ibfk_2`
    FOREIGN KEY (`id_persona`)
    REFERENCES `instmessenger`.`persona` (`id_persona`),
  CONSTRAINT `fk_aux_agenda_persona_mensaje1`
    FOREIGN KEY (`mensaje_id_mensaje`)
    REFERENCES `instmessenger`.`mensaje` (`id_mensaje`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'latin1_swedish_ci';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
