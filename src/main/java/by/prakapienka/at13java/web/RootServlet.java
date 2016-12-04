package by.prakapienka.at13java.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RootServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>ServletHibernateApp</title></head><body>");
            out.println("<a href=\"" + req.getContextPath() + "/users\">Show users</a><br/>");
            out.println("<a href=\"" + req.getContextPath() + "/products\">Show products</a><br/>");
            out.println("</body></html>");
        }

    }
}
