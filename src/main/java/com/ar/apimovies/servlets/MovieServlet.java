package com.ar.apimovies.servlets;
import com.ar.apimovies.dao.MovieDAO;
import com.ar.apimovies.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = movieDAO.getAllMovies();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(movies);

        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String titulo = req.getParameter("titulo");
        Integer duracion = Integer.parseInt(req.getParameter("duracion"));
        String genero = req.getParameter("genero");
        String imagen = req.getParameter("imagen");
        Movie movie = new Movie(titulo, duracion, genero, imagen);
        MovieDAO movieDAO = new MovieDAO();
        Long movieId = movieDAO.insertMovie(movie);

        resp.getWriter().write("Película agregada exitosamente");
        resp.setStatus(HttpServletResponse.SC_CREATED);

    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        MovieDAO movieDAO = new MovieDAO();
        if (id == null || id.isEmpty()) {
            resp.getWriter().write("ID de película no proporcionada");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return;
        }
        int result =  movieDAO.deleteMovie(Integer.parseInt(id));
        if (result == 0) {
            resp.getWriter().write("No se pudo eliminar la película");
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);

        }
        else {
            resp.getWriter().write("Película eliminada exitosamente");
            resp.setStatus(HttpServletResponse.SC_OK);

        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            resp.getWriter().write("ID de película no proporcionada");
            return;
        }
        String titulo = req.getParameter("titulo");
        Integer duracion = Integer.parseInt(req.getParameter("duracion"));
        String genero = req.getParameter("genero");
        String imagen = req.getParameter("imagen");
        Movie movie = new Movie(titulo, duracion, genero, imagen);
        movie.setId(Integer.parseInt(id));
        MovieDAO movieDAO = new MovieDAO();
        int result =  movieDAO.updateMovie(movie);
        if (result == 0) {
            resp.getWriter().write("No se pudo actualizar la película");
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);

        }
        else {
            resp.getWriter().write("Película actualizada exitosamente");
            resp.setStatus(HttpServletResponse.SC_OK);

        }
    }
}
