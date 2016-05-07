package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Junior
 */
public class Conta {
    private int id;
    private String numero;
    private String agencia;
    private int idcontaTipo;
    private int idcliente;
    private double saldo;
    private double limite;
    private String cpfCnpj;
    
    public void depositar(Double valor){
        this.saldo += valor;
    }
    
    public boolean sacar(Double valor){
        if(valor < this.saldo){
          this.saldo -= valor;
          return true;
        }else{
            return false;
        } 
    }
    
    public void calculaLimite(){
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {
            cliente = clienteDAO.getById(this.idcliente);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(cliente.getRenda() > 1000){
            this.limite = (cliente.getRenda() * 1.5);
        }else{
            this.limite = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public int getIdcontaTipo() {
        return idcontaTipo;
    }

    public void setIdcontaTipo(int idcontaTipo) {
        this.idcontaTipo = idcontaTipo;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    
    
}
