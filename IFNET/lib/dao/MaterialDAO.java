package lib.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lib.Model.Material.Material;

public class MaterialDAO {
    
    private static Conexao conexao = new Conexao();	

    public static void inserir(Material material) {		
		try {
			// cria um preparedStatement
			String sql = "insert into material(nome, area_conhecimento, tipo_material ) values (?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, material.getNomeMaterial());
			    stmt.setString(2, material.getAreaConhecimento());
                stmt.setString(3, material.getTipoMaterial());
			    stmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }finally{
                if(stmt!=null)
                    stmt.close();
            }
			// preenche os valores
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
