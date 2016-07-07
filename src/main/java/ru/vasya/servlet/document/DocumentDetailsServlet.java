package ru.vasya.servlet.document;

import ru.vasya.model.document.Document;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DocService;

import javax.ejb.EJB;
import javax.print.Doc;
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
public class DocumentDetailsServlet extends HttpServlet {

    @EJB
    DocService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Map<Person, TreeSet<Document>> docs = ds.getDocuments();
        for(Person p : docs.keySet()){
            for(Document d: docs.get(p)){
                if(d.getId()==id){
                    req.setAttribute("document", d);
                    req.setAttribute("docType", d.getClass().getSimpleName());
                }
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/document/document_details.jsp");
        rd.forward(req, resp);
    }
}
