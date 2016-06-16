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
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class CheckEspecialDAO {
    private String stmtInsert = "insert into cheque_especial(data, idcliente, idconta) values(?,?,?)";
    private String stmtSelectCliente = "select * from cheque_especial where idcliente = ? and idconta = ?";
    private String stmtDelete = "delete from cheque_especial where idcliente = ? and idconta = ?";
    private String stmtSelectNegativado = "select * from cheque_especial where data <= ? ";
    private String stmtSelectClienteAll = "select * from cheque_especial where idcliente = ?";
    
    public Boolean verificaChequeAll(int idcliente) throws ClassNotFoundException{
        Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectClienteAll, Statement.RETURN_GENERATED_KEYS);
             stmt.setInt(1, idcliente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
  
    }
    
    public List<Cliente> getListaNegativado(String sData) throws SQLException{
        com.mysql.jdbc.Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        try {
            con = (com.mysql.jdbc.Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectNegativado);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date data;
            data = null;
            try {
                data = new java.sql.Date(format.parse(sData).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            stmt.setDate(1, data);
            rs = stmt.executeQuery();
            List<ChequeEspecial> lstChequeEspecial = new ArrayList();
            while (rs.next()) {
                // criando o objeto Diretor
                ChequeEspecial ChequeEspecial = new ChequeEspecial();
                ChequeEspecial.setIdcliente(rs.getInt("idcliente"));

                lstChequeEspecial.add(ChequeEspecial);
            }
            
            List<Cliente> lstCliente = new ArrayList();
            ClienteDAO clienteDAO = new ClienteDAO();
            
            for(ChequeEspecial ChequeEspecial : lstChequeEspecial ){
                try {
                    lstCliente.add(clienteDAO.getById(ChequeEspecial.getIdcliente()));
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CheckEspecialDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return lstCliente;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }

    }
    
    public void delete(int id, int idconta) throws SQLException {
        Connection con=null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtDelete);

            stmt.setInt(1, id);
            stmt.setInt(2, idconta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }

    }
    
    public Boolean verificaCheque(int idcliente, int idconta) throws ClassNotFoundException{
        Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectCliente, Statement.RETURN_GENERATED_KEYS);
             stmt.setInt(1, idcliente);
             stmt.setInt(2, idconta);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
  
    }
    
    public void insert(ChequeEspecial ChequeEspecial) {
        Connection con=null;
        PreparedStatement stmt = null;

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInsert, Statement.RETURN_GENERATED_KEYS);
            
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date data;
            data = null;
            try {
                data = new java.sql.Date(format.parse(ChequeEspecial.getData()).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            stmt.setDate(1, data); 
            stmt.setInt(2, ChequeEspecial.getIdcliente());
            stmt.setInt(3, ChequeEspecial.getIdconta());
            stmt.execute();
            //Seta o id do sistema
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
}
