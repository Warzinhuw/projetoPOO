package lib.Model.Grupo;

import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.ExtendedSSLSession;

public class Grupo {

    public static final int GRUPO_TRABALHO = 0;
    public static final int GRUPO_PESQUISA = 1;
    
    private int tipoGrupo; 
    private String nomeGrupo, nomeProfessor, disciplinaRelacionada;


    private List<String> listIntegrante = new LinkedList<>();

    public Grupo(){//

    };

    public Grupo( int tipoGrupo, String nomeGrupo, String nomeProfessor, String disciplinaRelacionada) {
        this.nomeProfessor = nomeProfessor;
        this.nomeGrupo = nomeGrupo;
        this.tipoGrupo = tipoGrupo;
        this.disciplinaRelacionada = disciplinaRelacionada;
    }


    public String getDisciplinaRelacionada() {
        return this.disciplinaRelacionada;
    }

    public void setDisciplinaRelacionada(String disciplinaRelacionada) {
        this.disciplinaRelacionada = disciplinaRelacionada;
    }

    public int getTipoGrupo() {
        return this.tipoGrupo;
    }

    public void setTipoGrupo(int tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public void addIntegrante(String nome){
        this.listIntegrante.add(nome);
    }

    public void removerIntegrante(String nome){
        this.listIntegrante.remove(nome);
    }


    public String getNomeGrupo() {
        return this.nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public String getNomeProfessor() {
        return this.nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public List<String> getListIntegrante() {
        return this.listIntegrante;
    }

    public void setListIntegrante(List<String> listIntegrante) {
        this.listIntegrante = listIntegrante;
    }


}
