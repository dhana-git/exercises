package com.dhana.exercise.servlet.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/session", name = "sessionManager")
public class SessionManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isCreate = Boolean.valueOf(req.getParameter("create"));
        Boolean isInvalidate = Boolean.valueOf(req.getParameter("invalidate"));
        Boolean isTest = Boolean.valueOf(req.getParameter("test"));
        HttpSession session = req.getSession(false);

        if (session != null) {
            if (isInvalidate) {
                session.invalidate();
                resp.getWriter().println("Your session has expired. Good bye!");
            } else if (isTest || isCreate) {
                resp.getWriter().println("Your session is still alive. Keep enjoying!");
            }
        } else {
            if (isInvalidate || isTest) {
                resp.getWriter().println("Oh, you don't have live session with us. Sorry!");
            } else if (isCreate) {
                req.getSession();
                resp.getWriter().println("Great, your session has been created. Keep enjoying!");
            }
        }
    }
}
