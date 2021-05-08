package lib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lib.Excecao.NaoEncontrado;
import lib.Model.Material.Material;

public class MaterialDAO {
    
    private static Conexao conexao = new Conexao();	

    public static void inserir(Material material, String prontuario) {		
		try {
			// cria um preparedStatement
			String sql = "insert into material(nome, area_conhecimento, tipo_material, Aluno_prontuario ) values (?,?,?,?)";
            PreparedStatement stmt = null;
            try{
			    stmt = conexao.getConn().prepareStatement(sql);
                stmt.setString(1, material.getNomeMaterial());
			    stmt.setString(2, material.getAreaConhecimento());
                stmt.setString(3, material.getTipoMaterial());
                stmt.setString(4, prontuario);
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

    public static void deletarMaterial(String nomeMaterial){
			
        PreparedStatement stmt = null;
        
        try{
            stmt = conexao.getConn().prepareStatement("delete from material where nome ='"+nomeMaterial+"'");
            stmt.execute();
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checarExistenciaMaterial(String nome) throws NaoEncontrado{
        boolean valido = false;
        try{
            String sql = "select nome from material where nome = '"+nome+"'";
            PreparedStatement stmt = null;
            ResultSet resultado = null;
            stmt = conexao.getConn().prepareStatement(sql);
            resultado = stmt.executeQuery();
            if(resultado.next())
                valido = true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        if (valido)
            return true;
            
        throw new NaoEncontrado(new Material());
    }
}
