package lib.Model.Disciplina;

public class Disciplina {
    
    private int codigoDisciplina, cargaHoraria;
    private String nome, nomeProfessor;


    public Disciplina(int cargaHoraria, String nome, String nomeProfessor) {
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.nomeProfessor = nomeProfessor;
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

    public String getNomeProfessor() {
        return this.nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    // Precisa do banco para exclusão
    public void excluirDisciplina(int codigo){//
    
    }

    // Precisa do banco para adicionar
    public void addDisciplina(){//
        
    }

}
