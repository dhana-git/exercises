package com.ajs.exercise.servlet.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/task", name = "longRunningTask")
public class LongRunningTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int counter = 0;

            while (counter < 20) {
                manipulateSession(req, resp);
                counter++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resp.getWriter().println("Completed successfully.");
        } catch (Exception anyEx) {
            anyEx.printStackTrace();
            resp.getWriter().println("Failure in completing a task.");
            resp.getWriter().println(anyEx);
        }

    }

    private void manipulateSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        session.getAttribute("XYZ");
        System.out.println("isNew:" + session.isNew());
    }
}
