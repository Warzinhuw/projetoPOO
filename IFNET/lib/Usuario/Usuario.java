package IFNET.lib.Usuario;

public abstract class Usuario {

    private String nome, email;
    private int prontuario, categoriaConfiabilidade;


    public Usuario() {
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProntuario() {
        return this.prontuario;
    }

    public void setProntuario(int prontuario) {
        this.prontuario = prontuario;
    }

    public int getCategoriaConfiabilidade() {
        return this.categoriaConfiabilidade;
    }

    public void setCategoriaConfiabilidade(int categoriaConfiabilidade) {
        this.categoriaConfiabilidade = categoriaConfiabilidade;
    }

    public void cadastrarUsuario(){

    }

    public void excluirUsuario(){
        
    }

    
}
