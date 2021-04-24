package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lib.Model.Usuario.Aluno;
import java.sql.SQLException;

public class AlunoDAO{

	private static Conexao conexao = new Conexao();

    public static void inserir(Aluno aluno) {			
		try {
			// cria um preparedStatement
			String sql = "insert into aluno(prontuario, nome,email,categoria_confiabilidade, tipo_usuario) values (?,?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
				stmt.setString(1, "BP3008347");
                stmt.setString(2, aluno.getNome());
			    stmt.setString(3, aluno.getEmail());
			    stmt.setInt(4, aluno.getCategoriaConfiabilidade());
                stmt.setInt(5, aluno.getTipoUsuario());
			    stmt.execute();
            }catch (SQLException e) {
                // TODO Bloco catch gerado automaticamente
                e.printStackTrace();
            }finally{
                stmt.close();
            }
			// preenche os valores
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean logarUsuario(String prontuario, Aluno aluno){
		ResultSet resultado = null;
		PreparedStatement stmt = null;
		boolean valido = false;
		try{
			stmt = conexao.getConn().prepareStatement("select * from aluno where prontuario like '"+prontuario+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
				aluno.cadastrarUsuario(
					resultado.getString("nome"), 
					resultado.getString("prontuario"), 
					resultado.getString("email"), 
					resultado.getInt("categoria_confiabilidade")
					);
				valido = true;
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return valido;
	}
    
}
