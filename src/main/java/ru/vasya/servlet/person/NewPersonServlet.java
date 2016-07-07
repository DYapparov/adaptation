package ru.vasya.servlet.person;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vasya.model.staff.Person;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dyapparov on 06.07.2016.
 */
public class NewPersonServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewPersonServlet.class);

    @EJB
    DerbyService ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", ds.getAll(Post.class));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/person/new_person.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person p = new Person();
        String lastName = req.getParameter("lastName").trim();
        String firstName = req.getParameter("firstName").trim();
        String middleName = req.getParameter("middleName").trim();
        if (StringUtils.isAlpha(lastName)
                && StringUtils.isAlpha(firstName)
                && StringUtils.isAlpha(middleName)
                && !lastName.isEmpty()
                && !firstName.isEmpty()
                && !middleName.isEmpty()) {
            p.setLastName(lastName);
            p.setFirstName(firstName);
            p.setMiddleName(middleName);
            p.setPost(req.getParameter("post"));
            try {
                p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
            } catch (ParseException e) {
                LOGGER.error("Couldnot parse birthday date. Setting default value", e);
                p.setBirthday(new Date(0));
            }
            p.setPhotoURL("img/avatars/African_Male.png");
            if(!isDuplicate(p)) {
                ds.insertItem(p);
                resp.sendRedirect("persons");
                return;
            }
        }
        resp.sendRedirect("new_person");
    }

    private boolean isDuplicate(Person item){
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("lastName", item.getLastName());
        values.put("firstName", item.getFirstName());
        values.put("middleName", item.getMiddleName());
        return ds.contains(item.getClass(), values);
    }
}
