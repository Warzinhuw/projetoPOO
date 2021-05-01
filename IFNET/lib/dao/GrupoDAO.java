package lib.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lib.Model.Grupo.Grupo;

public class GrupoDAO {

    private static Conexao conexao = new Conexao();

    public static void inserir(Grupo grupo){

        try {
			// cria um preparedStatement
			String sql = "insert into grupo(nome_grupo, tipo_grupo, nome_professor) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, grupo.getNomeGrupo());
			    stmt.setInt(2, grupo.getTipoGrupo());
			    stmt.setString(3, grupo.getNomeProfessor());
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
