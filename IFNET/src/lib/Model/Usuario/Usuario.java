package lib.Model.Usuario;

public abstract class Usuario {

    public static final int TIPO_ALUNO = 0;
    public static final int TIPO_PROFESSOR = 1;

    private String nome, email, prontuario;
    private int tipoUsuario;


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

    public String getProntuario() {
        return this.prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public void cadastrarUsuario(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public void excluirUsuario(){
        //excluir do banco
    }


    public int getTipoUsuario() {
        return this.tipoUsuario;
    }


    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


    
}
