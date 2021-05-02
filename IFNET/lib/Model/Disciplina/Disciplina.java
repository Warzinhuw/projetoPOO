package lib.Model.Disciplina;

public class Disciplina {
    
    private int codigoDisciplina, cargaHoraria;
    private String nome;


    public Disciplina(int cargaHoraria, String nome) {
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
    
    }

    public int getCodigoDisciplina() {
        return this.codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
