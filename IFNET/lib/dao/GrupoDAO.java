package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.Model.Grupo.Grupo;

public class GrupoDAO {

    private static Conexao conexao = new Conexao();

    public static void inserir(Grupo grupo){

        try {
			// cria um preparedStatement
			String sql = "insert into grupo(nome_grupo, tipo_grupo, nome_professor, nome_disciplina) values (?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, grupo.getNomeGrupo());
			    stmt.setInt(2, grupo.getTipoGrupo());
			    stmt.setString(3, grupo.getNomeProfessor());
                stmt.setString(4, grupo.getDisciplinaRelacionada());
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

    public static ArrayList<String> getListGrupos(String nomeProfessor){
        ArrayList<String> listNomes = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome_grupo from grupo where nome_professor = '"+nomeProfessor+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomes.add(resultado.getString("nome_grupo"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listNomes;

    }

    public static boolean checarExistenciaGrupo(String nome){
        boolean valido = false;
        try{
            String sql = "select nome_grupo from grupo where nome_grupo = '"+nome+"'";
            PreparedStatement stmt = null;
            ResultSet resultado = null;
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            if(resultado.next())
                valido = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return valido;
    }

    public static Grupo recuperarGrupo(String nomeGrupo, String nomeProfessor){
        Grupo grupo = new Grupo();
		ResultSet resultado = null;
		PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement("select * from grupo where nome_grupo = '"+nomeGrupo+"' and nome_professor = '"+nomeProfessor+"'");
			resultado = stmt.executeQuery();
			if(resultado.next()){
                grupo.setNomeGrupo(nomeGrupo);
                grupo.setNomeProfessor(nomeProfessor);
                grupo.setTipoGrupo(resultado.getInt("tipo_grupo"));
                grupo.setDisciplinaRelacionada(resultado.getString("nome_disciplina"));
                return grupo;
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
	}

    public static ArrayList<String> getListAlunosSemGrupo(Grupo grupo){
		ArrayList<String> listNomeAlunos = new ArrayList<>();
		ArrayList<String> listAlunosNoGrupo = new ArrayList<>();
		ArrayList<String> listAlunoDisponiveis = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select aluno_nome from Disciplinas_Aluno where nome = '"+grupo.getDisciplinaRelacionada()+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomeAlunos.add(resultado.getString("aluno_nome"));
            }
			sql = "select nome_aluno from grupo_list_alunos where nome_grupo = '"+grupo.getNomeGrupo()+"' and nome_professor = '"+grupo.getNomeProfessor()+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listAlunosNoGrupo.add(resultado.getString("nome_aluno"));
            }
			for(String nome : listNomeAlunos){
				if(!listAlunosNoGrupo.contains(nome))
					listAlunoDisponiveis.add(nome);
			}
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listAlunoDisponiveis;
	}

    public static ArrayList<String> getListAlunosNoGrupo(Grupo grupo){
        ArrayList<String> listNomeAlunos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome_aluno from grupo_list_alunos where nome_grupo = '"+grupo.getNomeGrupo()+"' and nome_professor = '"+grupo.getNomeProfessor()+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomeAlunos.add(resultado.getString("nome_aluno"));
            }
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listNomeAlunos;
    }

    public static void inserirAluno(Grupo grupo, String nomeAluno){
        try {
			// cria um preparedStatement
			String sql = "insert into Grupo_list_alunos(nome_aluno, nome_grupo, nome_professor) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, nomeAluno);
			    stmt.setString(2, grupo.getNomeGrupo());
			    stmt.setString(3, grupo.getNomeProfessor());
			    stmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }finally{
                stmt.close();
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void removerAluno(String nomeAluno){
        PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement("delete from Grupo_list_alunos where nome_aluno = '"+nomeAluno+"'");
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static void excluirGrupo(Grupo grupo){
        PreparedStatement stmt = null;
		try{
			stmt = conexao.getConn().prepareStatement(
                "delete from Grupo where nome_grupo = '"+grupo.getNomeGrupo()+
                "' and nome_professor = '"+grupo.getNomeProfessor()+
                "' and tipo_grupo = "+grupo.getTipoGrupo()
            );
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}
