package ru.vasya.servlet.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.staff.Person;
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
public class PersonDetails extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDetails.class);

    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("person", ds.getById(Person.class, id));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/edit_person.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        if ("Delete".equals(req.getParameter("action"))){
            Person p = new Person();
            p.setId(id);
            ds.deleteItem(p);
        } else {
            try {
                Person p = (Person) ds.getById(Person.class, id);
                p.setLastName(req.getParameter("lastName"));
                p.setFirstName(req.getParameter("firstName"));
                p.setMiddleName(req.getParameter("middleName"));
                p.setPost(req.getParameter("post"));
                p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
                ds.updateItem(p);
            } catch (ParseException e) {
                LOGGER.error("Couldnot parse birthday date", e);
            }
        }
        resp.sendRedirect("persons");
    }
}
