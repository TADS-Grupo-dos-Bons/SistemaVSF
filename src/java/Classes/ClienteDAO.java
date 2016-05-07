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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class ClienteDAO {
    private String stmtInsert = "insert into cliente(nome,sobrenome,rg,cpf,senha,telfResidencial,telfCelular,TelfComercial,email,dtNascimento,renda)\n" +
"values(?,?,?,?,?,?,?,?,?,?,?);";
    private String smttInsertEndereco = "insert into endereco(logradouro,numero, complemento, bairro, idcidade, idcliente) values(?,?,?,?,?,?);";
    private String stmtCheckLogin = "select * from cliente where cpf =? and senha=?;";
    private String stmtGetEndereco = "select * from endereco where idcliente=?";
    private String stmtSelectById = "select * from cliente where id =?";
    
    public void insert(Cliente cliente) {
        Connection con=null;
        PreparedStatement stmt = null;
        int idObjeto = 0;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInsert, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome()); 
            stmt.setString(3, cliente.getRg()); 
            stmt.setString(4, cliente.getCpf()); 
            stmt.setString(5, cliente.getSenha()); 
            stmt.setString(6, cliente.getTelResidencial()); 
            stmt.setString(7, cliente.getTelCelular()); 
            stmt.setString(8, cliente.getTelResidencial()); 
            stmt.setString(9, cliente.getEmail());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date data;
            data = null;
            try {
                data = new java.sql.Date(format.parse(cliente.getDtNascimento()).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            stmt.setDate(10, data); 
            stmt.setDouble(11, cliente.getRenda()); 
            stmt.execute();
            //Seta o id do sistema
            ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()){
                idObjeto = rs.getInt(1);
            }

            cliente.setId(idObjeto);
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public Cliente getById(int id) throws ClassNotFoundException{
        Cliente cliente = new Cliente();
        Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtSelectById, Statement.RETURN_GENERATED_KEYS);
             stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSobrenome(rs.getString("sobrenome"));
                cliente.setRg(rs.getString("rg"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setTelResidencial(rs.getString("telfResidencial"));
                cliente.setTelCelular(rs.getString("telfCelular"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDtNascimento(rs.getString("dtNascimento"));
                cliente.setRenda(rs.getDouble("renda"));
                cliente.setEndereco(getEndereco(cliente.getId()));

            }
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
  
    }
    
    public void addEndereco(Cliente cliente) {
        Connection con=null;
        PreparedStatement stmt = null;
        Endereco endereco = cliente.getEndereco();
        endereco.setIdcliente(cliente.getId());
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(smttInsertEndereco, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero()); 
            stmt.setString(3, endereco.getComplemento()); 
            stmt.setString(4, endereco.getBairro()); 
            stmt.setInt(5, endereco.getIdcidade()); 
            stmt.setInt(6, endereco.getIdcliente()); 
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            /*long i = rs.getLong(1);
            diretor.setId(i);
            */
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
        
    public Cliente verificaLogin(String cpf, String senha) throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Cliente cliente = new Cliente();
        com.mysql.jdbc.Connection con=null;
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        try {
            con = (com.mysql.jdbc.Connection) ConnectionFactory.getConnection();
            
            stmt = con.prepareStatement(stmtCheckLogin, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSobrenome(rs.getString("sobrenome"));
                cliente.setRg(rs.getString("rg"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setTelResidencial(rs.getString("telfResidencial"));
                cliente.setTelCelular(rs.getString("telfCelular"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDtNascimento(rs.getString("dtNascimento"));
                cliente.setRenda(rs.getDouble("renda"));
                cliente.setEndereco(getEndereco(cliente.getId()));

            }
            
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }  
    }
    
    public Endereco getEndereco(int idCliente) throws ClassNotFoundException{
       Class.forName("com.mysql.jdbc.Driver");
       Endereco endereco = new Endereco();
        Connection con=null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtGetEndereco, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                endereco.setId(rs.getInt("id"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setIdcidade(rs.getInt("idcidade"));
                endereco.setIdcliente(rs.getInt("idcliente"));
            }
            return endereco;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        } 
    }
    
}
