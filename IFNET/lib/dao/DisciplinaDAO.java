package lib.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
}
