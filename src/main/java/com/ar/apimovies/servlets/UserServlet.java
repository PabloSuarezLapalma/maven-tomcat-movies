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
        // Implementación del método GET para manejar la solicitud de usuario
        // Crear una instancia de UserDAO
        UserDAO userDAO = new UserDAO();

        // Obtener todos los usuarios
        List<User> users = userDAO.getAllUsers();

        // Convertir la lista de usuarios a formato JSON
        Gson gson = new Gson();
        String usersJson = gson.toJson(users);

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir la respuesta
        response.getWriter().write(usersJson);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementación del método POST para manejar la inserción de usuario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String apellido = request.getParameter("apellido");
        String password = request.getParameter("password");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        if (fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {
            // Manejar el caso cuando fechaNacimiento no está presente
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
        // Implementación del método DELETE para manejar la eliminación de usuario
        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            // Manejar el caso cuando el ID del usuario no está presente
            response.getWriter().write("ID de usuario no proporcionado");
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        UserDAO userDAO = new UserDAO();
        int result= userDAO.deleteUser(userId);
        if (result == 0) {
            // Manejar el caso cuando el usuario no se pudo eliminar
            response.getWriter().write("No se pudo eliminar el usuario");
            return;
        }
        else {
            // Manejar el caso cuando el usuario se eliminó exitosamente
            response.getWriter().write("Usuario eliminado exitosamente");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementación del método PUT para manejar la actualización de usuario
        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            // Manejar el caso cuando el ID del usuario no está presente
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
            // Manejar el caso cuando fechaNacimiento no está presente
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
            // Manejar el caso cuando el usuario no se pudo actualizar
            response.getWriter().write("No se pudo actualizar el usuario");
            return;
        }
        else {
            // Manejar el caso cuando el usuario se actualizó exitosamente
            response.getWriter().write("Usuario actualizado exitosamente");
        }
    }
}
