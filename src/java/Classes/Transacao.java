/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Junior
 */
public class Transacao {
    private int id;
    private int idcontaDestino;
    private double valor;
    private String data;
    private String descricao;
    private int idconta;
    int idTrancasaoTipo;
    private String MinhaConta;
    private String ContaDestino;
    private String NomeTipo;

    public String getMinhaConta() {
        return MinhaConta;
    }

    public void setMinhaConta(String MinhaConta) {
        this.MinhaConta = MinhaConta;
    }

    public String getContaDestino() {
        return ContaDestino;
    }

    public void setContaDestino(String ContaDestino) {
        this.ContaDestino = ContaDestino;
    }

    public String getNomeTipo() {
        return NomeTipo;
    }

    public void setNomeTipo(String NomeTipo) {
        this.NomeTipo = NomeTipo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContaDestino() {
        return idcontaDestino;
    }

    public void setIdContaDestino(int contaDestino) {
        this.idcontaDestino = contaDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdconta() {
        return idconta;
    }

    public void setIdconta(int idconta) {
        this.idconta = idconta;
    }

    public int getIdTrancasaoTipo() {
        return idTrancasaoTipo;
    }

    public void setIdTrancasaoTipo(int idTrancasaoTipo) {
        this.idTrancasaoTipo = idTrancasaoTipo;
    }
    
    
}
