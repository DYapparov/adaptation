package ru.vasya.servlet;

import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by dyapparov on 01.07.2016.
 */
public class DocumentServlet extends HttpServlet {

    @EJB
    DocService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        for(Person p : docs.keySet()){
            if (p.getId()==id){
                req.setAttribute("person", p);
                req.setAttribute("docs", docs.get(p));
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/documents.jsp");
        rd.forward(req, resp);
    }
}
