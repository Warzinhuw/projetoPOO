package IFNET.lib.Grupo;

import java.util.LinkedList;
import java.util.List;

public class Grupo {

    private final int GRUPO_TRABALHO = 0;
    private final int GRUPO_PESQUISA = 1;
    
    private int tipoGrupo;
    private List<String> disciplinasRelacionadas = new LinkedList<String>();


    public Grupo() { //
    }


    public int getTipoGrupo() {
        return this.tipoGrupo;
    }

    public void setTipoGrupo(int tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public List<String> getDisciplinasRelacionadas() {
        return this.disciplinasRelacionadas;
    }

    public void setDisciplinasRelacionadas(List<String> disciplinasRelacionadas) {
        this.disciplinasRelacionadas = disciplinasRelacionadas;
    }

    public void addIntegrante(){//

    }

    public void removerIntegrante(String nome){
        this.disciplinasRelacionadas.remove(nome);
    }

    public void excluirGrupo(){//

    }

    /*
    public Grupo getGrupoMaisIntegrantes(){

    }

    public Grupo[] getGruposByDisciplinas(String disciplina){

    }
    */


}
