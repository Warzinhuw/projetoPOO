package lib.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import lib.Model.Usuario.Professor;

public class ProfessorDAO {

    public static void inserir(Professor professor) {		
		Conexao conexao = new Conexao();	
		try {
			// cria um preparedStatement
			String sql = "insert into professores(nome,email,categoria_confiabilidade, tipo_usuario, area) values (?,?,?,?,?)";
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
    
}
