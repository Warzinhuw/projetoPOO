package IFNET.lib.Usuario;

import java.util.LinkedList;
import java.util.List;
import IFNET.lib.Curso.Curso;

public class Aluno extends Usuario{

    private List<Curso> listCursos = new LinkedList<Curso>();
    

    public Aluno(String nome, int prontuario, String email, int categoriaConfiabilidade) {
        this.setNome(nome);
        this.setProntuario(prontuario);
        this.setEmail(email);
        this.setCategoriaConfiabilidade(categoriaConfiabilidade);
    }    

    public List<Curso> getListCursos() {
        return this.listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    @Override
    public void cadastrarUsuario() {
        // TODO Auto-generated method stub
        super.cadastrarUsuario();
    }

}
