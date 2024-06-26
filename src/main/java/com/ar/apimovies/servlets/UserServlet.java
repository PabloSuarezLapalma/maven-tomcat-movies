package com.ar.apimovies.servlets;

import com.ar.apimovies.dao.UserDAO;
import com.ar.apimovies.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        Gson gson = new Gson();
        String usersJson = gson.toJson(users);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(usersJson);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String apellido = request.getParameter("apellido");
        String password = request.getParameter("password");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        if (fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {
            response.getWriter().write("Fecha de nacimiento no proporcionada");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(fechaNacimientoStr, formato);
        Date fechaNacimiento = Date.valueOf(localDate); //
        String pais = request.getParameter("pais");
        User user = new User(nombre, email, apellido, password, fechaNacimiento, pais);
        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(user);
        response.getWriter().write("Usuario agregado exitosamente");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.getWriter().write("ID de usuario no proporcionado");
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        UserDAO userDAO = new UserDAO();
        int result= userDAO.deleteUser(userId);
        if (result == 0) {
            response.getWriter().write("No se pudo eliminar el usuario");
        }
        else {
            response.getWriter().write("Usuario eliminado exitosamente");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.getWriter().write("ID de usuario no proporcionado");
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String apellido = request.getParameter("apellido");
        String password = request.getParameter("password");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        if (fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {
            response.getWriter().write("Fecha de nacimiento no proporcionada");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(fechaNacimientoStr, formato);
        Date fechaNacimiento = Date.valueOf(localDate);
        String pais = request.getParameter("pais");
        User user = new User(nombre, email, apellido, password, fechaNacimiento, pais);
        user.setId(userId);
        UserDAO userDAO = new UserDAO();
        int result = userDAO.updateUser(user);
        if (result == 0) {
            response.getWriter().write("No se pudo actualizar el usuario");
        }
        else {
            response.getWriter().write("Usuario actualizado exitosamente");
        }
    }
}
