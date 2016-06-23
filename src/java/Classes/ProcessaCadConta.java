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
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Junior
 */
@WebServlet(name = "ProcessaCadConta", urlPatterns = {"/ProcessaCadConta"})
public class ProcessaCadConta extends HttpServlet {

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
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcessaCadConta.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PrintWriter out = response.getWriter()) {
            HttpSession sessionCli = request.getSession();
            Cliente cliente = (Cliente) sessionCli.getAttribute("Cliente");

            Client client = ClientBuilder.newClient();
            Response resp =  client.target("http://localhost:8084/SistemaDOR/webresources/verifica").request(MediaType.APPLICATION_JSON).post(Entity.json(cliente));

            Cliente clienteDOR = resp.readEntity(Cliente.class);
            if(("A").equals(clienteDOR.getStatus())){
                
                out.println("Voccê está ativo no DOR, e não poderá abrir conta.");
            }else{
                int tipo = Integer.parseInt(request.getParameter("tipo"));
                String agencia = request.getParameter("agencia");
                String numero = request.getParameter("conta");
                String cpfCnpj = request.getParameter("cpfCnpj");

                Conta conta = new Conta();
                ContaDAO contaDAO = new ContaDAO();

                conta.setAgencia(agencia);
                conta.setNumero(numero);
                conta.setCpfCnpj(cpfCnpj);
                conta.setIdcliente(cliente.getId());
                conta.setIdcontaTipo(tipo);
                conta.setSaldo(0);
                conta.calculaLimite();

                contaDAO.insert(conta);
                out.println("Conta cadastrada com sucesso.");
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
