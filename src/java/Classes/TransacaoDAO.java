package Classes;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class TransacaoDAO {

    String stmtInsertDeposito = "insert into transacao(contaDestino, valor, data, descricao, idconta, idtransacaoTipo) values(?,?,?,?,?,?);";
    String stmtSelect = "select minha.id, minha.numero as 'MinhaConta', contaDestino.numero as 'ContaDestino', transacao.valor, transacao.data, transacaoTipo.nome from conta as minha\n" +
                        " inner join transacao on transacao.idconta = minha.id\n" +
                        " inner join conta as contaDestino on contaDestino.id = transacao.contaDestino\n" +
                        " inner join transacaoTipo on transacaoTipo.id = transacao.idTransacaoTipo\n" +
                        " where minha.id = ?";
    

    public void insert(Transacao transacao) {
        java.sql.Connection con = null;
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

            stmt.setInt(1, transacao.getIdContaDestino());
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
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
                System.out.println("Erro ao fechar stmt. Ex=" + ex.getMessage());
            };
            try {
                con.close();
            } catch (Exception ex) {
                System.out.println("Erro ao fechar conexão. Ex=" + ex.getMessage());
            };
        }
    }

    public List<Transacao> getLista(int idConta, int periodo) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = (Connection) ConnectionFactory.getConnection();
            Format frm = new Format();
            java.sql.Date data;
            data = null;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if(periodo > 0){
                try {
                    //data = frm.removeDays(periodo);
                    data = new java.sql.Date(format.parse(frm.removeDays(periodo)).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(TransacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                stmtSelect += " and transacao.data >= ?";
            }
            stmtSelect += " order by transacao.data desc";
            stmt = con.prepareStatement(stmtSelect);
            stmt.setInt(1, idConta);
            if(periodo > 0){
               stmt.setDate(2, (java.sql.Date) data); 
            }
            rs = stmt.executeQuery();
            List<Transacao> lsttransacao = new ArrayList();
            while (rs.next()) {
                // criando o objeto Diretor
                Transacao transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setMinhaConta(rs.getString("MinhaConta"));
                transacao.setContaDestino(rs.getString("ContaDestino"));
                transacao.setValor(rs.getDouble("valor"));
                transacao.setData(frm.convertDateToString(rs.getDate("data")));
                transacao.setNomeTipo(rs.getString("nome"));

                // adicionando o objeto à lista
                lsttransacao.add(transacao);
            }

            return lsttransacao;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
                System.out.println("Erro ao fechar result set. Ex=" + ex.getMessage());
            };
            try {
                stmt.close();
            } catch (Exception ex) {
                System.out.println("Erro ao fechar stmt. Ex=" + ex.getMessage());
            };
            try {
                con.close();
            } catch (Exception ex) {
                System.out.println("Erro ao fechar conexão. Ex=" + ex.getMessage());
            };
        }

    }

}