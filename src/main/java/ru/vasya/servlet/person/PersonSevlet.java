package ru.vasya.servlet.person;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.dao.PersonDAO;
import ru.vasya.dao.PostDAO;
import ru.vasya.model.staff.Post;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyapparov on 04.07.2016.
 */
public class PersonSevlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonSevlet.class);

    @EJB
    PersonDAO personDAO;
    @EJB
    PostDAO postDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("person", personDAO.getByID(ru.vasya.model.staff.Person.class, id));
        req.setAttribute("posts", postDAO.getAll(Post.class));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/person/edit_person.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        if ("Delete".equals(req.getParameter("action"))){
            ru.vasya.model.staff.Person p = new ru.vasya.model.staff.Person();
            p.setId(id);
            personDAO.delete(p);
        } else {
            List<String> wrongFields = new ArrayList<String>();
            ru.vasya.model.staff.Person p = (ru.vasya.model.staff.Person) personDAO.getByID(ru.vasya.model.staff.Person.class, id);
            String lastName = req.getParameter("lastName").trim();
            String firstName = req.getParameter("firstName").trim();
            String middleName = req.getParameter("middleName").trim();

            if(!StringUtils.isAlpha(lastName) || lastName.isEmpty()){
                wrongFields.add("lastName");
            }
            if(!StringUtils.isAlpha(firstName) || firstName.isEmpty()){
                wrongFields.add("firstName");
            }
            if(!StringUtils.isAlpha(middleName) || middleName.isEmpty()){
                wrongFields.add("middleName");
            }

            if (wrongFields.size()==0) {
                p.setLastName(lastName);
                p.setFirstName(firstName);
                p.setMiddleName(middleName);
                int postId = Integer.parseInt(req.getParameter("post"));
                Post post = new Post();
                post.setId(postId);
                p.setPost(post);
            } else {
                String errorMessage = "Please, provide valid infomation for fields " + StringUtils.join(wrongFields, ", ");
                resp.setHeader("error", errorMessage);
                resp.setStatus(520);
                return;
            }
            try {
                p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
            } catch (ParseException e) {
                LOGGER.error("Could not parse birthday date", e);
            }
            personDAO.update(p);
            resp.sendRedirect("edit_person?id=" + id);
        }
    }
}
