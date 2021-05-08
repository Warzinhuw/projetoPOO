package lib.Excecao;

import java.io.FileInputStream;
import java.io.IOException;

import lib.Model.Disciplina.Disciplina;
import lib.Model.Material.Material;
import lib.dao.AlunoDAO;


public class NaoEncontrado extends Exception {

    public NaoEncontrado() {
        super("\n Usuario não foi encontrado!");
    }

    public NaoEncontrado(Disciplina disciplina){
        super("\nDisciplina não encontrada!");
    }

    public NaoEncontrado(Material material){
        super("\nMaterial não encontrado!");
    }
}



