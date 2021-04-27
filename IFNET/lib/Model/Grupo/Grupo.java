package lib.Model.Grupo;

import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.ExtendedSSLSession;

public class Grupo {

    private final int GRUPO_TRABALHO = 0;
    private final int GRUPO_PESQUISA = 1;
    
    private int tipoGrupo; 
    private List<String> disciplinasRelacionadas = new LinkedList<String>();


    private List<String> listIntegrante = new LinkedList<>();

    public Grupo( int tipoGrupo, List<String> disciplinasRelacionadas) {
        this.tipoGrupo = tipoGrupo;
        this.disciplinasRelacionadas = disciplinasRelacionadas;
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

    public void addIntegrante(String nome){
        this.listIntegrante.add(nome);
    }

    public void removerIntegrante(String nome){
        this.listIntegrante.remove(nome);
    }

}
