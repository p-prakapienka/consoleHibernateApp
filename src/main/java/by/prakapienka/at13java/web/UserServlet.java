package by.prakapienka.at13java.web;

import by.prakapienka.at13java.model.User;
import by.prakapienka.at13java.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {

    private ConfigurableApplicationContext applicationContext;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    public void destroy() {
        applicationContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (true) {}
        if (true) {}
        if (true) {}
        if (true) {}

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Users</title></head><body>");
            out.println("<h1><a href=\"" + req.getContextPath() + "\">Main</a></h1>");
            out.println("<p><a href=\"" + req.getContextPath() + "/users?action=create\">Create</a></p>");
            if (action == null) {
                List<User> users = userService.getAll();
                if (users != null && !users.isEmpty()) {
                    out.println("<table><tr><th>ID</th><th>Name</th></tr>");
                    for (User user : users) {
                        out.println("<tr><td>");
                        out.println(user.getId());
                        out.println("</td><td><a href=\"");
                        out.println(req.getContextPath() + "/orders?id=" + user.getId());
                        out.println("\">");
                        out.println(user.getName());
                        out.println("</a></td><td><a href=\"");
                        out.println(req.getContextPath() + "/users?action=update&id=");
                        out.println(user.getId());
                        out.println("\">Update</a></td><td><a href=\"");
                        out.println(req.getContextPath() + "/users?action=delete&id=");
                        out.println(user.getId());
                        out.println("\">Delete</a></td></tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No users found</p>");
                }
            } else if ("delete".equals(action)) {
                userService.delete(Integer.valueOf(req.getParameter("id")));
                resp.sendRedirect("users");
            } else if ("create".equals(action) || "update".equals(action)) {
                final User user = "create".equals(action) ?
                        new User("") :
                        userService.get(Integer.valueOf(req.getParameter("id")));
                out.println("<form method=\"post\" action=\"users\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"");
                out.println(user.getId());
                out.println("\"/><label>Name:<input name=\"name\" value=\"");
                out.println(user.getName());
                out.println("\"/></label><input type=\"submit\" value=\"Ok\"/>");
                out.print("<p><a href=\"" + req.getContextPath());
                out.print("/users\">Back</a></p>");
            }
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id").trim();

        final User user = new User("null".equals(id) ? null : Integer.valueOf(id),
                                   req.getParameter("name").trim());

        if (id.isEmpty()) {
            userService.insert(user);
        } else {
            userService.update(user);
        }
        resp.sendRedirect("users");
    }
}
