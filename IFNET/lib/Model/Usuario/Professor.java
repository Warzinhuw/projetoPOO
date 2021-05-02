package lib.Model.Usuario;

import java.util.LinkedList;
import java.util.List;
import lib.Model.Disciplina.Disciplina;

public class Professor extends Usuario{

    public Professor() {
       
        //TODO Auto-generated constructor stub
    }

    private String area;
    private List<Disciplina> listDisciplinas = new LinkedList<Disciplina>();

    public Professor(String area){
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Disciplina> getListDisciplinas() {
        return this.listDisciplinas;
    }

    public void setListDisciplinas(List<Disciplina> listDisciplinas) {
        this.listDisciplinas = listDisciplinas;
    }

    @Override
    public void cadastrarUsuario(String nome, String email, int categoriaConfiabilidade) {
        // TODO vai salvar no banco
        super.cadastrarUsuario(nome, email, categoriaConfiabilidade);
        this.setTipoUsuario(Usuario.TIPO_PROFESSOR);
    }
    
}
