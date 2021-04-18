package lib.Usuario;

import java.util.LinkedList;
import java.util.List;
import lib.Curso.Curso;

public class Aluno extends Usuario{

    private List<Curso> listCursos = new LinkedList<Curso>();
    

    public Aluno() {
    }    

    public List<Curso> getListCursos() {
        return this.listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    @Override
    public void cadastrarUsuario(String nome, String prontuario, String email, int categoriaConfiabilidade) {
        // TODO vai salvar no banco
        super.cadastrarUsuario(nome, prontuario, email, categoriaConfiabilidade);
        this.setTipoUsuario(Usuario.TIPO_ALUNO);
    }

}
