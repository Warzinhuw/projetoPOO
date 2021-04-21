package lib.dao;

import java.sql.PreparedStatement;
import lib.Model.Usuario.Aluno;
import java.sql.SQLException;

public class AlunoDAO{

	AlunoDAO(){//

	}

    public static void inserir(Aluno aluno) {		
		Conexao conexao = new Conexao();	
		try {
			// cria um preparedStatement
			String sql = "insert into alunos(nome,email,categoria_confiabilidade, tipo_usuario) values (?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, aluno.getNome());
			    stmt.setString(2, aluno.getEmail());
			    stmt.setInt(3, aluno.getCategoriaConfiabilidade());
                stmt.setInt(4, aluno.getTipoUsuario());
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
    
}
