package ru.vasya;

import ru.vasya.model.staff.Person;
import ru.vasya.service.PersonService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class PersonServlet extends HttpServlet{
    @EJB
    PersonService ps;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> persons = ps.getPersonList();
        req.setAttribute("persons", persons);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
        rd.forward(req, resp);
    }
}
