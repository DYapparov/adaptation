package ru.vasya.servlet.person;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.staff.Post;
import ru.vasya.service.DerbyService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by dyapparov on 04.07.2016.
 */
public class PersonSevlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonSevlet.class);

    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("person", ds.getById(ru.vasya.model.staff.Person.class, id));
        req.setAttribute("posts", ds.getAll(Post.class));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/person/edit_person.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        if ("Delete".equals(req.getParameter("action"))){
            ru.vasya.model.staff.Person p = new ru.vasya.model.staff.Person();
            p.setId(id);
            ds.deleteItem(p);
        } else {
            ru.vasya.model.staff.Person p = (ru.vasya.model.staff.Person) ds.getById(ru.vasya.model.staff.Person.class, id);
            String lastName = req.getParameter("lastName").trim();
            String firstName = req.getParameter("firstName").trim();
            String middleName = req.getParameter("middleName").trim();
            if(StringUtils.isAlpha(lastName)
                    &&StringUtils.isAlpha(firstName)
                    &&StringUtils.isAlpha(middleName)
                    &&!lastName.isEmpty()
                    &&!firstName.isEmpty()
                    &&!middleName.isEmpty()){
                p.setLastName(lastName);
                p.setFirstName(firstName);
                p.setMiddleName(middleName);
                p.setPost(req.getParameter("post"));
            } else {
                resp.sendRedirect("edit_person?id=" + id);
                return;
            }
            try {
                p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
            } catch (ParseException e) {
                LOGGER.error("Could not parse birthday date", e);
            }
            ds.updateItem(p);
        }
        resp.sendRedirect("persons");
    }
}
