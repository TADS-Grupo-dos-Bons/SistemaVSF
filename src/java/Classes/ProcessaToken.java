/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 *
 * @author Junior
 */
@WebServlet(name = "ProcessaToken", urlPatterns = {"/ProcessaToken"})
public class ProcessaToken extends HttpServlet {

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
            int randNum;
            String pergunta = "";
            String resposta = "";
            
            Random gerador = new Random();
            randNum = gerador.nextInt(4);
            HttpSession sessionCli = request.getSession();
            Cliente cliente = (Cliente) sessionCli.getAttribute("Cliente");
            
            switch (randNum) {
                case 0:
                    pergunta = "Confirme seu CPF (somente números):";
                    resposta = cliente.getCpf().replace(".", "").replace("-", "");
                    break;
                case 1:
                    pergunta = "Confirme seu sobrenome:";
                    resposta = cliente.getSobrenome();
                    break;
                case 2:
                    pergunta = "Confirme seu e-mail:";
                    resposta = cliente.getEmail();
                    break;
                case 3:
                    pergunta = "Confirme sua data de nascimento: (dd/mm/aaaa)";
                    resposta = cliente.getDtNascimento();
                    break;
                default:
                    break;
            }
            
            out.println(pergunta+"|"+resposta);
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
