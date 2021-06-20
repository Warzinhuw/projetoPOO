package lib.Model.Grupo;

import java.util.LinkedList;
import java.util.List;

public class GrupoAdapter extends Grupo{

    private List<String> listIntegrante = new LinkedList<>();
    public static final int GRUPO_TRABALHO = 0;
    public static final int GRUPO_PESQUISA = 1;
    
    private int tipoGrupo, idGrupo; 
    private String nomeGrupo, nomeProfessor, disciplinaRelacionada;

    public GrupoAdapter( int tipoGrupo, String nomeGrupo, String nomeProfessor, String disciplinaRelacionada) {
        this.nomeProfessor = nomeProfessor;
        this.nomeGrupo = nomeGrupo;
        this.tipoGrupo = tipoGrupo;
        this.disciplinaRelacionada = disciplinaRelacionada;
    }
    
    @Override
    public void addIntegrante(String nome){
        boolean valido = true;
        for(String nomeIntegrande : listIntegrante){
            if(nomeIntegrande.equals(nome)){
                System.out.println("Já existe um integrante com esse nome.");
                valido = false;
                break;
            }
        }
        if(valido)
            this.listIntegrante.add(nome);
    }
    
}
