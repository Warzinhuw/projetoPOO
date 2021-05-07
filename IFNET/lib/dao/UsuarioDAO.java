package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Professor;
import lib.Model.Usuario.Usuario;

public class UsuarioDAO {

    private static Conexao conexao = new Conexao();

    public static Usuario checarExistenciaUsuario(String nome){

        try{
            String sql = "select nome from Aluno where nome = '"+nome+"'";
            PreparedStatement stmt = null;
            ResultSet resultado = null;
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            if(resultado.next())
                return recuperarUsuario(resultado.getString("Nome"));

            sql = "select nome from Professor where nome = '"+nome+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            if(resultado.next())
                return recuperarUsuario(resultado.getString("Nome"));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Usuario recuperarUsuario(String nomeUsuario){
		Aluno aluno = new Aluno();
        Professor professor = new Professor();
		ResultSet resultado = null;
		PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement("select * from Aluno where nome = '"+nomeUsuario+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
                aluno.setNome(resultado.getString("Nome"));
                aluno.setProntuario(resultado.getString("Prontuario"));
                aluno.setEmail(resultado.getString("Email"));
				aluno.setTipoUsuario(Usuario.TIPO_ALUNO);
                return aluno;
			}

            stmt = conexao.getConn().prepareStatement("select * from Professor where nome = '"+nomeUsuario+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
                professor.setNome(resultado.getString("Nome"));
                professor.setProntuario(resultado.getString("Prontuario"));
                professor.setEmail(resultado.getString("Email"));
				professor.setTipoUsuario(Usuario.TIPO_PROFESSOR);
                return professor;
			}

			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
	}
    
}
