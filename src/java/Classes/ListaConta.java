/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Junior
 */
@WebServlet(name = "ListaConta", urlPatterns = {"/ListaConta"})
public class ListaConta extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            HttpSession sessionCli = request.getSession();
            Cliente cliente = (Cliente) sessionCli.getAttribute("Cliente");

            List<Conta> lstconta = new ArrayList();
            ContaDAO contaDAO = new ContaDAO();
            try {
                lstconta = contaDAO.getLista(cliente.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ListaConta.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sRet = "";
            for (Conta conta : lstconta) {
                sRet += "<tr>\n" +
"				<td>" + conta.getAgencia() +"</td>\n" +
"				<td>" + conta.getNumero() + "</td>\n" +
"				<td>" + conta.getCpfCnpj() + "</td>\n" +
"				<td><button type=\"button\" class=\"btn btn-warning\" onclick=\"mostramodal1(" + conta.getId() + ")\">Operacoes</button>&nbsp;&nbsp;&nbsp;<button type=\"button\" onclick=\"encerrarConta(" + conta.getId() + ")\" class=\"btn btn-danger\">Encerrar</button></td>\n" +
"			</tr>";
            }

            out.println(sRet);
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
