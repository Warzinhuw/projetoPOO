package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lib.Model.Grupo.Grupo;
import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoDAO{

	private static Conexao conexao = new Conexao();

    public static void inserir(Aluno aluno) {			
		try {
			// cria um preparedStatement
			String sql = "insert into aluno(nome,email,categoria_confiabilidade, tipo_usuario, curso) values (?,?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, aluno.getNome());
			    stmt.setString(2, aluno.getEmail());
			    stmt.setInt(3, aluno.getCategoriaConfiabilidade());
                stmt.setInt(4, aluno.getTipoUsuario());
				stmt.setString(5, aluno.getNomeCurso());
			    stmt.execute();

				sql = "select prontuario from aluno order by prontuario desc limit 1";
				stmt = conexao.getConn().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				aluno.setProntuario(rs.getString("prontuario"));
				

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
					resultado.getString("email"), 
					resultado.getInt("categoria_confiabilidade")
					);
				aluno.setProntuario(prontuario);
				valido = true;
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return valido;
	}

	public static boolean adicionarDisciplina(Aluno aluno, String nomeDisciplina){
		try {
			// cria um preparedStatement
            PreparedStatement stmt = null;
			if(DisciplinaDAO.checarExistenciaDisciplina(nomeDisciplina, aluno.getProntuario())){ //checa se já tem a disciplina add pra esse aluno
				return false;
			}

			String sql = "insert into disciplinas_aluno(aluno_prontuario, aluno_nome, nome) values (?,?,?)";
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, aluno.getProntuario());
			    stmt.setString(2, aluno.getNome());
				stmt.setString(3, nomeDisciplina);
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
		return true;
	}

	public static Aluno recuperarAluno(String nomeAluno){
		Aluno aluno = new Aluno();
		ResultSet resultado = null;
		PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement("select * from Aluno where nome = '"+nomeAluno+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
                aluno.setNome(resultado.getString("Nome"));
                aluno.setProntuario(resultado.getString("Prontuario"));
                aluno.setEmail(resultado.getString("Email"));
				aluno.setTipoUsuario(Usuario.TIPO_ALUNO);
                return aluno;
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
	}
    

	public static void deletarAluno(String nome){
		PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement("delete from aluno where nome ='"+nome+"'");
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
