package ru.vasya.servlet.person;

import ru.vasya.dao.PersonDAO;
import ru.vasya.model.staff.Person;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class PersonsServlet extends HttpServlet{
    @EJB
    PersonDAO personDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<Person> personsFromDB= personDAO.getAll(Person.class);
        req.setAttribute("persons", personsFromDB);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/person/persons.jsp");
        rd.forward(req, resp);
    }
}
