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
import java.util.*;

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
        List<String> wrongFields = new ArrayList<String>();
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
            try {
                p.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
            } catch (ParseException e) {
                LOGGER.error("Couldnot parse birthday date. Setting default value", e);
                p.setBirthday(new Date(0));
            }
            p.setPhotoURL("img/avatars/African_Male.png");
            if(!isDuplicate(p)) {
                ds.insertItem(p);
            } else {
                resp.setHeader("error", "Such employee already exists");
                resp.setStatus(520);
            }
        } else {
            String errorMessage = "Please, provide valid infomation for fields " + StringUtils.join(wrongFields, ", ");
            resp.setHeader("error", errorMessage);
            resp.setStatus(520);
        }
    }

    private boolean isDuplicate(Person item){
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("lastName", item.getLastName());
        values.put("firstName", item.getFirstName());
        values.put("middleName", item.getMiddleName());
        return ds.contains(item.getClass(), values);
    }
}
