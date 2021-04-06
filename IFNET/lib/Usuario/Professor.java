package IFNET.lib.Usuario;

import java.util.LinkedList;
import java.util.List;

import IFNET.lib.Disciplina.Disciplina;

public class Professor extends Usuario{

    private String area;
    private List<Disciplina> listDisciplinas = new LinkedList<Disciplina>();


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

    public void addToDisciplinas(){ //

    }

    public void removeFromDisciplinas(){ //

    }

    @Override
    public void cadastrarUsuario() {
        // TODO Auto-generated method stub
        super.cadastrarUsuario();
    }
    
}
