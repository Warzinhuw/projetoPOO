package lib.dao.RelacionamentoDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Usuario;
import lib.dao.Conexao;

public class RelacionamentoV2 extends RelacionamentoDAO{
    private static Conexao conexao = new Conexao();

    
    public static void criarRelacionamento(Usuario usuario, List<Usuario> listaUsuario, String grauConfiabilidade){

        for (Usuario outroUsuario : listaUsuario) {
            try {
		    	// cria um preparedStatement
		    	String sql;
		        PreparedStatement stmt = null;
                if(outroUsuario.getClass() == Aluno.class)
                    sql = "insert into Relacionamentos(usuario_prontuario, nome_usuario, nome_outro_usuario_aluno, prontuario_outro_usuario_aluno, grau_confiabilidade) values (?,?,?,?,?)";
                else
                    sql = "insert into Relacionamentos(usuario_prontuario, nome_usuario, nome_outro_usuario_professor, prontuario_outro_usuario_professor, grau_confiabilidade) values (?,?,?,?,?)";
                try{
		    	    stmt = conexao.getConn().prepareStatement(sql);
                    stmt.setString(1, usuario.getProntuario());
                    stmt.setString(2, usuario.getNome());
		    	    stmt.setString(3, outroUsuario.getNome());
                    stmt.setInt(4, Integer.parseInt(outroUsuario.getProntuario()));
                    stmt.setString(5, grauConfiabilidade);
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









}
