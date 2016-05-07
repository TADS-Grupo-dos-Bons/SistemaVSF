/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Junior
 */
public class Combo {
    
    public static List<Estado> getEstado(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
        }
        String stmtSelectEstado = "select * from estado";
        
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectEstado);
            rs = stmt.executeQuery();
            List<Estado> lstestado = new ArrayList();
            while (rs.next()) {
                // criando o objeto Sistema
                Estado Estado = new Estado();
                Estado.setId(rs.getInt("id"));
                Estado.setNome(rs.getString("nome"));
                // adicionando o objeto à lista
                lstestado.add(Estado);
            }
            
            return lstestado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public static List<Cidade> getCidade(int idEstado){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Combo.class.getName()).log(Level.SEVERE, null, ex);
        }
        String stmtSelectCidade = "select * from cidade where idestado = ?";
        
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectCidade);
            stmt.setInt(1, idEstado);
            rs = stmt.executeQuery();
            List<Cidade> lstcidade = new ArrayList();
            while (rs.next()) {
                // criando o objeto Sistema
                Cidade Cidade = new Cidade();
                Cidade.setId(rs.getInt("id"));
                Cidade.setNome(rs.getString("nome"));
                // adicionando o objeto à lista
                lstcidade.add(Cidade);
            }
            
            return lstcidade;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
}
