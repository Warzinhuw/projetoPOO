package IFNET.lib.Curso;

public class Curso {
    
    private int codigoCurso, numeroSemestre, cargaHoraria;
    private String nome, nivel, profCoordenador;

    public Curso() {//
    }

    public int getCodigoCurso() {
        return this.codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public int getNumeroSemestre() {
        return this.numeroSemestre;
    }

    public void setNumeroSemestre(int numeroSemestre) {
        this.numeroSemestre = numeroSemestre;
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

    public String getNivel() {
        return this.nivel;
    }

    public String getProfCoordenador() {
        return this.profCoordenador;
    }

    public void setProfCoordenador(String profCoordenador) {
        this.profCoordenador = profCoordenador;
    }


}