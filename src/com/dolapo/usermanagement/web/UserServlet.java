package com.dolapo.usermanagement.web;

import com.dolapo.usermanagement.dao.UserDAO;
import com.dolapo.usermanagement.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name="UserServlet", urlPatterns = "/")
public class UserServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private UserDAO userDAO;

  public void init(){
    userDAO = new UserDAO();
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
    doGet(req, res);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
    String action = req.getServletPath();
    switch(action){
      case "/new":
        showNewForm(req, res);
        break;

      case "/insert":
        insertUser(req, res);
        break;

      case "/update":
        updateUser(req, res);
        break;

      case "/delete":
        deleteUser(req, res);
        break;

      case "/edit":
        showEditForm(req, res);
        break;

      default:
        listUser(req, res);
        break;
    }
  }

  private void listUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    List<User> users = new ArrayList<>();
    users = userDAO.selectAllUsers();
    req.setAttribute("listUSer", users);
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-list.jsp");
    dispatcher.forward(req, res);
  }

  // display user-form
  private void showNewForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-form.jsp");
    dispatcher.forward(req, res);
  }

  //handle create user request

  private void insertUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String name = req.getParameter("name");
    String email = req.getParameter("email");
    String country = req.getParameter("country");
    userDAO.insertUser(new User(name, email, country));
    res.sendRedirect("list");
  }

  private void showEditForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    User existingUser = userDAO.selectUser(id);
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-form.jsp");
    req.setAttribute("user", existingUser);
    dispatcher.forward(req, res);
  }

  // handle update user request

  private void updateUser(HttpServletRequest req, HttpServletResponse res) throws  IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    String name = req.getParameter("name");
    String email = req.getParameter("email");
    String country = req.getParameter("country");
    userDAO.updateUser(new User(id, name, email, country));
    res.sendRedirect("list");
  }

  private void deleteUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    userDAO.deleteUser(id);
    res.sendRedirect("list");
  }
}
