package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Usuario;

public class RelacionamentoDAO {

    private static Conexao conexao = new Conexao();

    public static void criarRelacionamento(Usuario usuario, Usuario outroUsuario, String grauConfiabilidade){

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

    public static void excluirRelacionamento(Usuario usuario, Usuario outroUsuario){
        PreparedStatement stmt = null;
		try{
            String sql;
            sql = "delete from Relacionamentos where usuario_prontuario = '"+
            usuario.getProntuario()+(outroUsuario.getClass() == Aluno.class ? "' and nome_outro_usuario_aluno = '" : "' and nome_outro_usuario_professor = '")
            +outroUsuario.getNome()+"'";
			stmt = conexao.getConn().prepareStatement(sql);
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static SortedMap<Integer, String> getMapGrupoMaisUsuarios(){
        SortedMap<Integer, String> mapRelacionamentos = new TreeMap<>(Collections.reverseOrder());
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = 
            "SELECT nome_usuario, (select count(nome_outro_usuario_aluno) from Relacionamentos )+(select count(nome_outro_usuario_professor) from Relacionamentos) as qtd FROM Relacionamentos "+
            "group by usuario_prontuario "+
            "order by (select count(nome_outro_usuario_aluno) from Relacionamentos )+(select count(nome_outro_usuario_professor) from Relacionamentos) desc";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                mapRelacionamentos.put(resultado.getInt("qtd"), resultado.getString("nome_usuario"));
            }
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return mapRelacionamentos;
    }
    
}
