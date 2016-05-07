/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class TransacaoDAO {
    String stmtInsertDeposito = "insert into transacao(contaDestino, valor, data, descricao, idconta, idtransacaoTipo) values(?,?,?,?,?,?);";
    
    public void insertDeposito(Transacao transacao){
        java.sql.Connection con=null;
        PreparedStatement stmt = null;

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInsertDeposito, Statement.RETURN_GENERATED_KEYS);
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date data;
            data = null;
            try {
                data = new java.sql.Date(format.parse(transacao.getData()).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmt.setInt(1, transacao.getContaDestino());
            stmt.setDouble(2, transacao.getValor()); 
            stmt.setDate(3, data); 
            stmt.setString(4, transacao.getDescricao()); 
            stmt.setInt(5, transacao.getIdconta()); 
            stmt.setDouble(6, transacao.getIdTrancasaoTipo()); 

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
