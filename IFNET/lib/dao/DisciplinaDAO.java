package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import lib.Model.Disciplina.Disciplina;
import lib.Model.Usuario.Professor;

public class DisciplinaDAO {

    private static Conexao conexao = new Conexao();

    public static void inserir(Disciplina disciplina) {	

        try {
			// cria um preparedStatement
			String sql = "insert into disciplina(nome, carga_horaria) values (?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
				stmt.setString(1, disciplina.getNome());
                stmt.setInt(2, disciplina.getCargaHoraria());
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

    public static ArrayList<String> getListDisciplinas(){
        ArrayList<String> listNomes = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome from disciplina";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomes.add(resultado.getString("nome"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listNomes;
    }

    public static ArrayList<String> getListDisciplinas(String nomeProfessor){
        ArrayList<String> listNomes = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome from disciplina where professor = '"+nomeProfessor+"'";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomes.add(resultado.getString("nome"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listNomes;
    }

    public static ArrayList<String> getListDisciplinasSemProf(){
        ArrayList<String> listNomes = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try{
            String sql = "select nome from disciplina where ISNULL(professor) ";
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            while(resultado.next()){
                listNomes.add(resultado.getString("nome"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listNomes;
    }
       

    public static void deletarDisciplina(String nomeDisciplina){
		ResultSet resultado = null;
		PreparedStatement stmt = null;

		try{
			stmt = conexao.getConn().prepareStatement("delete from disciplina where nome ='"+nomeDisciplina+"'");
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public static boolean checarExistenciaDisciplina(String nome){
        boolean valido = false;
        try{
            String sql = "select nome from disciplina where nome = '"+nome+"'";
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

    public static boolean checarExistenciaDisciplina(String nome, String prontuario){
        boolean valido = false;
        try{
            String sql = "select nome from disciplinas_aluno where aluno_prontuario = '"+prontuario+"' and nome = '"+nome+"'";
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

    public static void inserirConteudo(String disciplina, String conteudo, String nomeProfessor){

        try {
			String sql = "insert into conteudos_disciplina(disciplina, conteudo, nome_professor) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
				stmt.setString(1, disciplina);
                stmt.setString(2, conteudo);
                stmt.setString(3, nomeProfessor);
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


    public static boolean adicionarDisciplinaProfessor(Professor professor, String nomeDisciplina){
        try {
            // cria um preparedStatement
            PreparedStatement stmt = null;
            if(DisciplinaDAO.checarExistenciaDisciplina(nomeDisciplina, professor.getProntuario())){ //checa se já tem a disciplina add pra esse aluno
                return false;
            }

            String sql = "update disciplina SET Professor = '"+ professor.getNome() +"' , Professor_prontuario = '"+professor.getProntuario()+"'where nome ='"+nomeDisciplina+"' ";
            try{
                stmt = conexao.getConn().prepareStatement(sql);
                stmt.execute();

                
                sql = "select prontuario from professor order by prontuario desc limit 1";
				stmt = conexao.getConn().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				professor.setProntuario(rs.getString("prontuario"));
				
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
}
