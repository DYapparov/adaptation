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
import java.util.Date;

/**
 * Created by dyapparov on 06.07.2016.
 */
public class NewPersonServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewPersonServlet.class);

    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/new_person.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person p = new Person();
        p.setLastName(req.getParameter("lastName"));
        p.setFirstName(req.getParameter("firstName"));
        p.setMiddleName(req.getParameter("middleName"));
        p.setPost(req.getParameter("post"));
        try {
            p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
        } catch (ParseException e){
            LOGGER.error("Couldnot parse birthday date. Setting default value", e);
            p.setBirthday(new Date(0));
        }
        p.setPhotoURL("img/avatars/African_Male.png");
        ds.insertItem(p);

        resp.sendRedirect("persons");
    }
}
