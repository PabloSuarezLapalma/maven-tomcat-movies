package com.ar.apimovies;
import com.ar.apimovies.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class UserDAO {
    public Long insertUser(User user) {
        String insertarUsuario= "INSERT INTO usuarios (nombre, email, apellido, password , pais, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        DatabaseConnection conexion = new DatabaseConnection();

        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Long userId = null;

        Connection cn = conexion.conectar();
        try{
            pstm=cn.prepareStatement(insertarUsuario);

            pstm.setString(1, user.getNombre());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getApellido());
            pstm.setString(4, user.getPassword());
            pstm.setString(5, user.getPais());
            pstm.setDate(6, new java.sql.Date(user.getFechaNacimiento().getTime()));

           int response= pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userId;
    }
    public void printUsers() {
        String query = "SELECT id, nombre, clave FROM usuarios";
        DatabaseConnection conexion = new DatabaseConnection();
        Statement stm = null;
        ResultSet rs = null;
        Connection cn = null;

        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery(query);

            System.out.println("id - nombre - contraseña");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                String pass = rs.getString("password");

                System.out.println(id + " - " + name + " - " + pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
