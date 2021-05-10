package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import lib.Model.Grupo.Grupo;
import lib.Model.Usuario.Aluno;

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

                sql = "select id_grupo from grupo order by id_grupo desc limit 1";
				stmt = conexao.getConn().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				grupo.setIdGrupo(rs.getInt("id_grupo"));
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
                grupo.setIdGrupo(resultado.getInt("id_grupo"));
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
			sql = "select nome_aluno from grupo_list_alunos where id_grupo = '"+grupo.getIdGrupo()+"'";
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
            String sql = " SELECT Grupo_list_alunos.nome_aluno from Grupo_list_alunos INNER JOIN Grupo ON Grupo_list_alunos.id_grupo = Grupo.id_grupo "+
            "where Grupo.nome_grupo = '"+grupo.getNomeGrupo()+
            "' and Grupo.nome_professor = '"+grupo.getNomeProfessor()+"'";
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

    public static ArrayList<String> getListGruposPorDisciplina(String disciplina){
        ArrayList<String> listDisciplinas = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome_grupo from Grupo where nome_disciplina = '"+disciplina+"' and tipo_grupo = '"+Grupo.GRUPO_PESQUISA+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listDisciplinas.add(resultado.getString("nome_grupo"));
            }
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listDisciplinas;
    }

    public static void inserirAluno(Grupo grupo, Aluno aluno){
        try {
			// cria um preparedStatement
			String sql = "insert into Grupo_list_alunos(id_grupo, nome_aluno, prontuario_aluno) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setInt(1, grupo.getIdGrupo());
                stmt.setString(2, aluno.getNome());
                stmt.setString(3, aluno.getProntuario());
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

    public static SortedMap<Integer, String> getMapGrupoMaisUsuarios(){
        SortedMap<Integer, String> mapGrupos = new TreeMap<>(Collections.reverseOrder());
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = 
            "select Grupo.nome_grupo, count(Grupo_list_alunos.id_grupo) from Grupo_list_alunos "+
            "INNER JOIN Grupo "+
            "ON Grupo_list_alunos.id_grupo = Grupo.id_grupo "+
            "group by Grupo_list_alunos.id_grupo "+
            "order by count(Grupo_list_alunos.id_grupo) DESC "+
            "limit 3";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                System.out.println("Map: "+resultado.getInt("count(Grupo_list_alunos.id_grupo)")+" "+resultado.getString("Grupo.nome_grupo"));
                mapGrupos.put(resultado.getInt("count(Grupo_list_alunos.id_grupo)"), resultado.getString("Grupo.nome_grupo"));
            }
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return mapGrupos;
    }
}
