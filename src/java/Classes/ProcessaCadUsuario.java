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
@WebServlet(name = "ProcessaCadUsuario", urlPatterns = {"/ProcessaCadUsuario"})
public class ProcessaCadUsuario extends HttpServlet {

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
            Logger.getLogger(ProcessaCadUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String telResidencial = request.getParameter("telFixo");
            String senha = request.getParameter("senha");
            String sobrenome = request.getParameter("sobrenome");
            String dtNascimento = request.getParameter("dtNascimento");
            String email = request.getParameter("email");
            String rg = request.getParameter("rg");
            String rendaMensal = request.getParameter("rendaMensal");
            String telCelular = request.getParameter("telCelular");
            
            String logradouro = request.getParameter("logradouro");
            String bairro = request.getParameter("bairro");
            String numero = request.getParameter("numero");
            String estado = request.getParameter("estado");
            String complemento = request.getParameter("complemento");
            String cidade = request.getParameter("cidade");
            
            
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente();
            
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelResidencial(telResidencial);
            cliente.setSenha(senha);
            cliente.setSobrenome(sobrenome);
            cliente.setDtNascimento(dtNascimento);
            cliente.setRg(rg);
            cliente.setRenda((rendaMensal.equals("") ? 0 : Double.parseDouble(rendaMensal)));
            cliente.setEmail(email);
            cliente.setTelCelular(telCelular);
            
            clienteDAO.insert(cliente);
            
            Endereco endereco = new Endereco();
            
            endereco.setLogradouro(logradouro);
            endereco.setBairro(bairro);
            endereco.setNumero(numero);
            endereco.setIdcidade(Integer.parseInt(cidade));
            endereco.setComplemento(complemento);
            
            cliente.setEndereco(endereco);
            
            clienteDAO.addEndereco(cliente);

            //out.println(nome + cpf +logradouro + bairro +TelResidencial + senha +sobrenome + dtNascimento +numero + estado +rg + rendaMensal + complemento + cidade +email);
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
