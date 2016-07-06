package ru.vasya.servlet.person;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.vasya.model.staff.Person;
import ru.vasya.service.DerbyService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dyapparov on 04.07.2016.
 */
public class PhotoServlet extends HttpServlet {

    @EJB
    DerbyService ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String fileName = null;
        boolean isCorrectFormat = false;
        PrintWriter out = resp.getWriter();
        String contextRoot = req.getServletContext().getRealPath("/");

        if(!ServletFileUpload.isMultipartContent(req)){
            out.println("Nothing to upload");
            return;
        }

        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item: items){
                if (item.getFieldName().equals("photo")){
                    if(!"image/png".equals(item.getContentType())){
                        out.println("Only png format is supported");
                        continue;
                    }
                    File uploadDir = new File(contextRoot + "img/avatars");
                    File file = File.createTempFile("avatar_", ".png", uploadDir);
                    item.write(file);
                    fileName = file.getName();
                    isCorrectFormat = true;
                } else if(item.isFormField()){;
                    if (item.getFieldName().equals("id")){
                        id = Integer.parseInt(item.getString());
                    }
                }
            }
        } catch (FileUploadException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(isCorrectFormat){
            Person p = (Person)ds.getById(Person.class, id);
            p.setPhotoURL("img/avatars/" + fileName);
            ds.updateItem(p);
            resp.sendRedirect("edit_person?id=" + id);
        }
    }
}
