package by.prakapienka.at13java.web;

import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.service.OrderService;
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

public class OrderServlet extends HttpServlet {

    private ConfigurableApplicationContext applicationContext;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        orderService = applicationContext.getBean(OrderService.class);
    }

    @Override
    public void destroy() {
        applicationContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/users");
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Orders</title></head><body>");
            out.println("<h1><a href=\"" + req.getContextPath() + "\">Main</a></h1>");
            out.println("<p><a href=\"" + req.getContextPath() + "/orders?action=create&id=" + id + "\">Create</a></p>");
            if (action == null) {
                List<Order> orders = orderService.getAll(Integer.valueOf(id));
                if (orders != null && !orders.isEmpty()) {
                    out.println("<table><tr><th>ID</th><th>Name</th></tr>");
                    for (Order order : orders) {
                        out.println("<tr><td>");
                        out.println(order.getId());
                        out.println("</td><td><a href=\"");
                        out.println(req.getContextPath() + "/items?id=" + id);
                        out.println("&order=" + order.getId() + "\">");
                        out.println(order.getName());
                        out.println("</a></td><td><a href=\"");
                        out.println(req.getContextPath() + "/orders?action=update&id=");
                        out.println(id + "&order=" + order.getId());
                        out.println("\">Update</a></td><td><a href=\"");
                        out.println(req.getContextPath() + "/orders?action=delete&id=");
                        out.println(id + "&order=" + order.getId());
                        out.println("\">Delete</a></td></tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No orders found</p>");
                }
            } else if ("delete".equals(action)) {
                orderService.delete(
                        Integer.valueOf(req.getParameter("order")),
                        Integer.valueOf(req.getParameter("id")));
                resp.sendRedirect("orders?id=" + id);
            } else if ("create".equals(action) || "update".equals(action)) {
                final Order order = "create".equals(action) ?
                        new Order("") :
                        orderService.get(
                                Integer.valueOf(req.getParameter("order")),
                                Integer.valueOf(req.getParameter("id")));
                out.println("<form method=\"post\" action=\"orders\">");
                out.print("<input type=\"hidden\" name=\"id\" value=\"");
                out.print(id);
                out.print("\"/><input type=\"hidden\" name=\"order\" value=\"");
                out.print(order.getId());
                out.print("\"/><label>Name:<input name=\"name\" value=\"");
                out.print(order.getName());
                out.println("\"/></label><input type=\"submit\" value=\"Ok\"/>");
            }
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id").trim();
        String orderId = req.getParameter("order").trim();

        final Order order = new Order("null".equals(orderId) ? null : Integer.valueOf(orderId),
                req.getParameter("name").trim());

        if (id.isEmpty()) {
            orderService.insert(order, Integer.valueOf(id));
        } else {
            orderService.update(order, Integer.valueOf(id));
        }
        resp.sendRedirect("orders?id=" + id);
    }
}
