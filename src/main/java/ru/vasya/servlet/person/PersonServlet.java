package ru.vasya.servlet.person;

import ru.vasya.model.staff.Person;
import ru.vasya.model.staff.Staff;
import ru.vasya.service.DerbyService;
import ru.vasya.service.PersonService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class PersonServlet extends HttpServlet{
    @EJB
    PersonService ps;
    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*ds.createTable(Person.class);
        List<Person> persons = ps.getPersonList();
        for (Person p : persons){
            ds.insertItem(p);
        }*/
        Set<Staff> personsFromDB= ds.getAll(Person.class);
        req.setAttribute("persons", personsFromDB);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
        rd.forward(req, resp);
    }
}
