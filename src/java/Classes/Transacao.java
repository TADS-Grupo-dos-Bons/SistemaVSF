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
    private int contaDestino;
    private double valor;
    private String data;
    private String descricao;
    private int idconta;
    int idTrancasaoTipo;

    public int getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(int contaDestino) {
        this.contaDestino = contaDestino;
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
