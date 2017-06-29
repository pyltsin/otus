package ru.otus.pyltsin.HW12;

import ru.otus.pyltsin.HW12.myCache.CacheEngine;
import ru.otus.pyltsin.HW12.service.Helper.ConnectionHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tully.
 * Update by Pyltsin on 29.06.2017.
 */
public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private CacheEngine cacheEngine;

    public AdminServlet(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());

        pageVariables.put("hit", cacheEngine.getHitCount());
        pageVariables.put("miss", cacheEngine.getMissCount());


        return pageVariables;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        if (!checkLogin(request)) {
            response.sendRedirect("/");
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
