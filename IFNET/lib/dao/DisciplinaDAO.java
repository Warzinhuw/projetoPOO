package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import lib.Model.Disciplina.Disciplina;

public class DisciplinaDAO {

    private static Conexao conexao = new Conexao();

    public static void inserir(Disciplina disciplina) {	

        try {
			// cria um preparedStatement
			String sql = "insert into disciplina(nome, carga_horaria, professor) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
				stmt.setString(1, disciplina.getNome());
                stmt.setInt(2, disciplina.getCargaHoraria());
			    stmt.setString(3, disciplina.getNomeProfessor());
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

    public static ArrayList<String> getListDisciplinas(ArrayList<String> listNomes){
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

    public static ArrayList<String> getListDisciplinas(ArrayList<String> listNomes, String nomeProfessor){
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

}
