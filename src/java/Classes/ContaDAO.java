/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.mysql.jdbc.Connection;
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
public class ContaDAO {
    String stmtSelect = "select * from conta where idcliente=?";
    String stmtInsert = "insert into conta(numero,agencia,idcontaTipo,idcliente,saldo,limite,cpfCnpj) values(?,?,?,?,?,?,?);";
    String stmtSelectById = "select * from conta where id=?";
    String stmtUpdate = "update conta set saldo=? where id=?";
    String stmtSelectByAgenciaConta = "select * from conta where agencia=? and numero=?";
    
    public List<Conta> getLista(int idcliente) throws SQLException{
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelect);
            stmt.setInt(1, idcliente);
            rs = stmt.executeQuery();
            List<Conta> lstconta = new ArrayList();
            while (rs.next()) {
                // criando o objeto Diretor
                Conta conta = new Conta();
                conta.setId(rs.getInt("id"));
                conta.setNumero(rs.getString("numero"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setIdcontaTipo(rs.getInt("idcontaTipo"));
                conta.setIdcliente(rs.getInt("idcliente"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setCpfCnpj(rs.getString("cpfCnpj"));
                
                // adicionando o objeto à lista
                lstconta.add(conta);
            }
            
            return lstconta;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }

    }
    
    public Conta getById(int id) throws ClassNotFoundException{
        Conta conta = new Conta();
        java.sql.Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectById, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setNumero(rs.getString("numero"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setIdcontaTipo(rs.getInt("idcontaTipo"));
                conta.setIdcliente(rs.getInt("idcliente"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setCpfCnpj(rs.getString("cpfCnpj"));
            }
            return conta;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
  
    }
    
    public Conta getByAgenciaConta(String nAgencia, String nConta) throws ClassNotFoundException{
        Conta conta = new Conta();
        java.sql.Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectByAgenciaConta, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nAgencia);
            stmt.setString(2, nConta);
            rs = stmt.executeQuery();
            while (rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setNumero(rs.getString("numero"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setIdcontaTipo(rs.getInt("idcontaTipo"));
                conta.setIdcliente(rs.getInt("idcliente"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                conta.setCpfCnpj(rs.getString("cpfCnpj"));
            }
            return conta;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
  
    }
    
    public void insert(Conta conta) {
        java.sql.Connection con=null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInsert, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, conta.getNumero());
            stmt.setString(2, conta.getAgencia()); 
            stmt.setInt(3, conta.getIdcontaTipo()); 
            stmt.setInt(4, conta.getIdcliente()); 
            stmt.setDouble(5, conta.getSaldo()); 
            stmt.setDouble(6, conta.getLimite()); 
            stmt.setString(7, conta.getCpfCnpj()); 

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
    
    public void efetuarDeposito(Conta conta, Double valor){
       Connection con=null;
        PreparedStatement stmt = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtUpdate);
            stmt.setDouble(1, conta.getSaldo());    
            stmt.setInt(2, conta.getId());
            stmt.executeUpdate();
            
            Transacao transacao = new Transacao();
            TransacaoDAO transacaoDAO = new TransacaoDAO();
            Format frm = new Format();
            
            transacao.setIdconta(conta.getId());
            transacao.setIdTrancasaoTipo(2);
            transacao.setValor(valor);
            transacao.setData(frm.getDateTime());
            transacao.setIdContaDestino(conta.getId());
            
            transacaoDAO.insert(transacao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void efetuarSaque(Conta conta, Double valor){
       Connection con=null;
        PreparedStatement stmt = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtUpdate);
            stmt.setDouble(1, conta.getSaldo());    
            stmt.setInt(2, conta.getId());
            stmt.executeUpdate();
            
            Transacao transacao = new Transacao();
            TransacaoDAO transacaoDAO = new TransacaoDAO();
            Format frm = new Format();
            
            transacao.setIdconta(conta.getId());
            transacao.setIdTrancasaoTipo(3);
            transacao.setValor(valor);
            transacao.setData(frm.getDateTime());
            transacao.setIdContaDestino(conta.getId());
            
            transacaoDAO.insert(transacao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void efetuarTransferencia(Conta conta, Conta contaDestino, Double valor){
       Connection con=null;
        PreparedStatement stmt = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtUpdate);
            
            stmt.setDouble(1, conta.getSaldo());    
            stmt.setInt(2, conta.getId());
            stmt.executeUpdate();
            
            stmt.setDouble(1, contaDestino.getSaldo());    
            stmt.setInt(2, contaDestino.getId());
            stmt.executeUpdate();
            
            Transacao transacao = new Transacao();
            TransacaoDAO transacaoDAO = new TransacaoDAO();
            Format frm = new Format();
            
            transacao.setIdconta(conta.getId());
            transacao.setIdTrancasaoTipo(1);
            transacao.setValor(valor);
            transacao.setData(frm.getDateTime());
            transacao.setIdContaDestino(contaDestino.getId());
            
            transacaoDAO.insert(transacao);
            
            transacao.setIdTrancasaoTipo(4);
            transacao.setIdconta(contaDestino.getId());
            transacao.setIdContaDestino(conta.getId());
            transacaoDAO.insert(transacao);
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
}
