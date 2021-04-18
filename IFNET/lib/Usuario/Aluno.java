package lib.Usuario;

import java.util.LinkedList;
import java.util.List;
import lib.Curso.Curso;

public class Aluno extends Usuario{

    private List<Curso> listCursos = new LinkedList<Curso>();
    

    public Aluno(){
    
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
