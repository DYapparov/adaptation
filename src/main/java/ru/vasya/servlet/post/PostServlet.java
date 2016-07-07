package ru.vasya.servlet.post;

import ru.vasya.model.staff.Post;
import ru.vasya.model.staff.Staff;
import ru.vasya.service.DerbyService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by dyapparov on 07.07.2016.
 */
public class PostServlet extends HttpServlet {

    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ds.dropTable(Post.class);
        //ds.createTable(Post.class);
        Set<Staff> posts = ds.getAll(Post.class);
        req.setAttribute("posts", posts);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/post/posts.jsp");
        rd.forward(req, resp);
    }
}
