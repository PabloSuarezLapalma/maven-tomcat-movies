package com.ar.apimovies.dao;
import com.ar.apimovies.database.DatabaseConnection;
import com.ar.apimovies.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MovieDAO {
    public Long insertMovie(Movie movie) {
        String insertMovie = "INSERT INTO peliculas (titulo, duracion, genero, imagen) VALUES (?, ?, ?, ?)";
        DatabaseConnection connection = new DatabaseConnection();

        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Long movieId = null;

        Connection cn = connection.conectar();
        try {
            pstm = cn.prepareStatement(insertMovie);

            pstm.setString(1, movie.getTitulo());
            pstm.setInt(2, movie.getDuracion());
            pstm.setString(3, movie.getGenero());
            pstm.setString(4, movie.getImagen());

            int result = pstm.executeUpdate();
            if (result>0) {
                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    movieId = rs.getLong(1);
                }
            }
            else {
                movieId = 0L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movieId;
    }
    public List<Movie> getAllMovies() {
        String query = "SELECT id, titulo, duracion, genero, imagen FROM peliculas";
        DatabaseConnection connection = new DatabaseConnection();
        Statement stm = null;
        ResultSet rs = null;
        Connection cn = null;
        List<Movie> movies = new ArrayList<>();
        try {
            cn = connection.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDuracion(rs.getInt("duracion"));
                movie.setGenero(rs.getString("genero"));
                movie.setImagen(rs.getString("imagen"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
        finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
    public int deleteMovie(int movieId) {
        String deleteMovie = "DELETE FROM peliculas WHERE id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement pstm = null;
        Connection cn = null;
        int result = 0;
        try {
            cn = connection.conectar();
            pstm = cn.prepareStatement(deleteMovie);
            pstm.setInt(1, movieId);
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    public int updateMovie(Movie movie) {
        String updateMovie = "UPDATE peliculas SET titulo = ?, duracion = ?, genero = ?, imagen = ? WHERE id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement pstm = null;
        Connection cn = null;
        int result = 0;
        try {
            cn = connection.conectar();
            pstm = cn.prepareStatement(updateMovie);
            pstm.setString(1, movie.getTitulo());
            pstm.setInt(2, movie.getDuracion());
            pstm.setString(3, movie.getGenero());
            pstm.setString(4, movie.getImagen());
            pstm.setInt(5, movie.getId());
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            } finally {
            try {
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
