package com.certuit.saml2.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.certuit.saml2.entity.User;
import com.certuit.saml2.logic.UserManager;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpSession;
import mx.avanti.siract.entity.Usuario;
import mx.avanti.siract.helper.LoginBeanHelper;
import mx.avanti.siract.ui.LoginBean;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private static final long serialVersionUID = 1L;
    LoginBeanHelper loginBeanHelp = new LoginBeanHelper();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dispatcher() {
        super();
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        this.doPost(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_name = request.getParameter("subject");
        String password = request.getParameter("password");
        String user_type = request.getParameter("userType");

        User user = null;

        if (user_type.toLowerCase().compareTo("student") == 0 || user_type.toLowerCase().compareTo("employee") == 0) {
            user = (User) new UserManager().getUser(user_name, password);
            System.out.println(user.getUserName() + "dispatcher");
            request.getSession().setAttribute("user", user_name);
        }

        if (user != null) {
            Usuario usuario = new Usuario();
            HttpSession session = request.getSession(true);
            String username = (String) session.getAttribute("user");
            usuario.setUSUusuario(username);            
            usuario = loginBeanHelp.getUsuario(usuario);

            if (usuario == null) {
                request.getSession().setAttribute("user", null);
                request.getSession(false).invalidate();
                response.sendRedirect("AccesoDenegado.html");
            } else {
                response.sendRedirect("index.xhtml");

            }
        }
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
