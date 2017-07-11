package ru.otus.pyltsin.HW13;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import ru.otus.pyltsin.HW13.common.AddressDataSet;
import ru.otus.pyltsin.HW13.common.PhoneDataSet;
import ru.otus.pyltsin.HW13.common.UserDataSet;
import ru.otus.pyltsin.HW13.myCache.CacheEngine;
import ru.otus.pyltsin.HW13.service.DBService;
import ru.otus.pyltsin.HW13.service.Helper.ConnectionHelper;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by tully.
 * Update by Pyltsin on 29.06.2017.
 */
public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";


    private DBService dbService;


    public void init() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

        System.out.println(applicationContext);
        this.dbService = (DBService) applicationContext.getBean("dbService");


    }

    private void cache() throws IOException {
        //check Cache
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        Set<PhoneDataSet> list1 = new HashSet<>();
        list1.add(new PhoneDataSet(12, "12345"));
        list1.add(new PhoneDataSet(22, "22345"));

        dbService.save(new UserDataSet("slon", 12, new AddressDataSet("lik", 12),
                list1));


        UserDataSet userDataSet = (UserDataSet) dbService.read(1);
        userDataSet = (UserDataSet) dbService.read(1);
        userDataSet = (UserDataSet) dbService.read(1);
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());

        pageVariables.put("hit", dbService.getCache().getHitCount());
        pageVariables.put("miss", dbService.getCache().getMissCount());


        return pageVariables;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        try {
            cache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!checkLogin(request)) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean checkLogin(HttpServletRequest request) throws IOException {
        Properties properties = ConnectionHelper.getProperties("login.properties");
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");

        return request.getParameter("login") != null && request.getParameter("password") != null &&
                request.getParameter("login").equals(login) && request.getParameter("password").equals(password);
    }
}
