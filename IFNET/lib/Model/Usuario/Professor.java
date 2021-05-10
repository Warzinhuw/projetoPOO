package lib.Model.Usuario;

import java.util.LinkedList;
import java.util.List;
import lib.Model.Disciplina.Disciplina;

public class Professor extends Usuario{

    public Professor() {
       
        //TODO Auto-generated constructor stub
    }

    private String area, disciplina;

    public String getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    private List<Disciplina> listDisciplinas = new LinkedList<Disciplina>();

    public Professor(String area, String disciplina){
        this.area = area;
        this.disciplina = disciplina;
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
    public void cadastrarUsuario(String nome, String email) {
        // TODO vai salvar no banco
        super.cadastrarUsuario(nome, email);
        this.setTipoUsuario(Usuario.TIPO_PROFESSOR);
    }
    
}
