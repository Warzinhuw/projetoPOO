package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Usuario;

public class RelacionamentoDAO {

    private static Conexao conexao = new Conexao();

    public static void criarRelacionamento(Usuario usuario, Usuario outroUsuario){

        try {
			// cria um preparedStatement
			String sql;
		    PreparedStatement stmt = null;
            if(outroUsuario.getClass() == Aluno.class)
                sql = "insert into Relacionamentos(usuario_prontuario,nome_outro_usuario_aluno, prontuario_outro_usuario_aluno) values (?,?,?)";
            else
                sql = "insert into Relacionamentos(usuario_prontuario,nome_outro_usuario_professor, prontuario_outro_usuario_professor) values (?,?,?)";
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, usuario.getProntuario());
			    stmt.setString(2, outroUsuario.getNome());
                stmt.setInt(3, Integer.parseInt(outroUsuario.getProntuario()));
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
