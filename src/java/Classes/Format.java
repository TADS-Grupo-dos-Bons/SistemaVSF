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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.catalina.filters.RequestFilter;

/**
 *
 * @author Junior
 */
public class Format {
    
    public int getNumeroNovaConta(int idCliente){
        String stmtIdUltimaConta = "select id from conta ORDER BY id DESC LIMIT 1";
        int idConta = 0;
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtIdUltimaConta);
            rs = stmt.executeQuery();
            while (rs.next()) {
               idConta = rs.getInt("id");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
        
        return idConta + 1;
    }
    
    public String getDateTime() { 
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = new Date(); 
        return dateFormat.format(date); 
    }
    
    public String convertDateToString(Date data) { 
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
 
        return dateFormat.format(data); 
    }
}
