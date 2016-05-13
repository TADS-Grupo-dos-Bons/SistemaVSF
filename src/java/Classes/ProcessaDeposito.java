/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Junior
 */
@WebServlet(name = "ProcessaDeposito", urlPatterns = {"/ProcessaDeposito"})
public class ProcessaDeposito extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Double valor = Double.parseDouble((request.getParameter("valor")));
            String tipo = request.getParameter("tipo");

            if (tipo.equals("deposito")) {
                int id = Integer.parseInt((request.getParameter("idConta")));
                ContaDAO contaDAO = new ContaDAO();
                Conta conta = new Conta();

                try {
                    conta = contaDAO.getById(id);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ProcessaDeposito.class.getName()).log(Level.SEVERE, null, ex);
                }

                conta.depositar(valor);
                contaDAO.efetuarDeposito(conta, valor);

                out.println("Deposito realizado com sucesso.");
            } else if (tipo.equals("saque")) {
                int id = Integer.parseInt((request.getParameter("idConta")));
                ContaDAO contaDAO = new ContaDAO();
                Conta conta = new Conta();

                try {
                    conta = contaDAO.getById(id);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ProcessaDeposito.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (conta.sacar(valor)) {
                    contaDAO.efetuarSaque(conta, valor);
                    out.println("Saque realizado com sucesso.");
                } else {
                    out.println("Saldo insuficiente.");
                }

            } else if (tipo.equals("checarConta")) {
                String nConta = request.getParameter("numero");
                String nAgencia = request.getParameter("agencia");

                ContaDAO contaDAO = new ContaDAO();
                Conta contaDestino = new Conta();

                try {

                    contaDestino = contaDAO.getByAgenciaConta(nAgencia, nConta);

                    if (contaDestino.getId() != 0) {

                        Cliente cliente = new Cliente();
                        ClienteDAO clienteDAO = new ClienteDAO();

                        cliente = clienteDAO.getById(contaDestino.getIdcliente());

                        out.println("1,Confirme os dados: Nome: " + cliente.getNome() + " CPF: " + cliente.getCpf());
                    } else {
                        out.println("0,Não existe uma conta com esses dados.");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ProcessaDeposito.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (tipo.equals("transferencia")) {
                String nConta = request.getParameter("numero");
                String nAgencia = request.getParameter("agencia");
                int id = Integer.parseInt((request.getParameter("idConta")));
                ContaDAO contaDAO = new ContaDAO();
                Conta conta = new Conta();
                Conta contaDestino = new Conta();

                try {
                    conta = contaDAO.getById(id);
                    contaDestino = contaDAO.getByAgenciaConta(nAgencia, nConta);
                    if (conta.sacar(valor)) {
                        contaDestino.depositar(valor);
                        contaDAO.efetuarTransferencia(conta, contaDestino, valor);
                        out.println("Transferência executada com sucesso.");
                    } else {
                        out.println("Saldo insuficiente.");
                    }
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ProcessaDeposito.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
