package ru.otus.pyltsin.HW15;


import com.google.gson.Gson;
import ru.otus.pyltsin.HW15.app.DBService;
import ru.otus.pyltsin.HW15.service.HibernateDBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tully.
 * Update by Pyltsin on 29.06.2017.
 */
public class UserServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "out.html";
    private final DBService db;

    public UserServlet(HibernateDBService hibernateDBService) {
        db = hibernateDBService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println(new Gson().toJson(db.readAll().size()));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("iduser");
        int id = Integer.parseInt(idStr);
        response.getWriter().println(new Gson().toJson(db.read(id)));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
