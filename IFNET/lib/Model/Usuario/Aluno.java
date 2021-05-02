package lib.Model.Usuario;

import java.util.LinkedList;
import java.util.List;
import lib.Model.Curso.Curso;

public class Aluno extends Usuario{

    String nomeCurso;

    public String getNomeCurso() {
        return this.nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
    
    public Aluno() {//
    }    

    @Override
    public void cadastrarUsuario(String nome, String email, int categoriaConfiabilidade) {
        // TODO vai salvar no banco
        super.cadastrarUsuario(nome, email, categoriaConfiabilidade);
        this.setTipoUsuario(Usuario.TIPO_ALUNO);
    }

}