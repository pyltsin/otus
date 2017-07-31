package ru.otus.pyltsin.HW16;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.common.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tully.
 * Update by Pyltsin on 29.06.2017.
 */
public class UserServlet extends HttpServlet {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final FrontendService fe;

    public UserServlet(FrontendService fe) {
        this.fe = fe;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doget");
        BlockingQueue<Integer> out = new LinkedBlockingDeque<>();
        fe.getSize(out);
        try {
            response.getWriter().println(new Gson().toJson(out.take()));
        } catch (InterruptedException e) {
            response.getWriter().println("");
        }
        log.debug("doget out");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("iduser");
        int id = Integer.parseInt(idStr);
        BlockingQueue<UserDataSet> userDataSets = new LinkedBlockingDeque<>();
        fe.getUser(userDataSets, id);

        try {
            response.getWriter().println(new Gson().toJson(userDataSets.take()));
        } catch (InterruptedException e) {
            response.getWriter().println("");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
