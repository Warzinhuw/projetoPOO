package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lib.Model.Usuario.Professor;

public class ProfessorDAO {

	private static Conexao conexao = new Conexao();	

    public static void inserir(Professor professor) {		
		try {
			// cria um preparedStatement
			String sql = "insert into professor(nome,email,categoria_confiabilidade, tipo_usuario, area_estudo) values (?,?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, professor.getNome());
			    stmt.setString(2, professor.getEmail());
			    stmt.setInt(3, professor.getCategoriaConfiabilidade());
                stmt.setInt(4, professor.getTipoUsuario());
                stmt.setString(5, professor.getArea());
			    stmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }finally{
                if(stmt!=null)
                    stmt.close();
            }
			// preenche os valores
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean logarUsuario(String prontuario, Professor professor){
		ResultSet resultado = null;
		PreparedStatement stmt = null;
		boolean valido = false;
		try{
			stmt = conexao.getConn().prepareStatement("select * from professor where prontuario like '"+prontuario+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
				professor.cadastrarUsuario(
					resultado.getString("nome"),  
					resultado.getString("email"), 
					resultado.getInt("categoria_confiabilidade")
					);
					professor.setProntuario(prontuario);
				valido = true;
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return valido;
	}

		public static void deletarProfessor(String prontt){
			
			PreparedStatement stmt = null;
			
			try{
				stmt = conexao.getConn().prepareStatement("delete from professor where prontuario ='"+prontt+"'");
				stmt.execute();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
    

}
