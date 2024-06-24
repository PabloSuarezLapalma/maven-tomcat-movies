package com.ar.apimovies;

import com.ar.apimovies.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class UserServlet extends HttpServlet {
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
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formato);
        String pais = request.getParameter("pais");

        User user = new User(nombre, email, apellido, password, fechaNacimiento, pais);
        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(user);

        response.getWriter().write("Usuario agregado exitosamente");
    }
}
