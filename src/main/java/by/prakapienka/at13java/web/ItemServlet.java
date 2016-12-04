package by.prakapienka.at13java.web;

import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.service.OrderService;
import by.prakapienka.at13java.service.ProductService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

public class ItemServlet extends HttpServlet {

    private ConfigurableApplicationContext applicationContext;
    private OrderService orderService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        orderService = applicationContext.getBean(OrderService.class);
        productService = applicationContext.getBean(ProductService.class);
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
        String orderId = req.getParameter("order");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/users");
        }
        if (orderId == null || orderId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/orders?id=" + id);
        }


        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Order</title></head><body>");
            out.println("<h1><a href=\"" + req.getContextPath() + "\">Main</a></h1>");
            out.println("<h2>Order " + orderId + "</h1>");
            out.println("<p><a href=\"" + req.getContextPath() + "/items?action=add&id=" +
                    id + "&order=" + orderId + "\">Add</a></p>");
            if (action == null) {
                Set<OrderItem> items = orderService.getWithItems(
                        Integer.valueOf(orderId),
                        Integer.valueOf(id)).getOrderItems();
                if (items != null && !items.isEmpty()) {
                    out.println("<table><tr><th>ID</th><th>Name</th></tr>");
                    for (OrderItem item : items) {
                        out.println("<tr><td>");
                        out.println(item.getId());
                        out.println("</td><td>");
                        out.println(item.getName());
                        out.println("</td><td><a href=\"");
                        out.println(req.getContextPath() + "/items?action=delete&id=");
                        out.println(id + "&order=" + orderId + "&item=" + item.getId());
                        out.println("\">Delete</a></td></tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No items found</p>");
                }
            } else if ("delete".equals(action)) {
                orderService.deleteItem(
                        Integer.valueOf(req.getParameter("order")),
                        Integer.valueOf(req.getParameter("item")),
                        Integer.valueOf(req.getParameter("id")));
                resp.sendRedirect("items?id=" + id + "&order=" + orderId);
            } else if ("add".equals(action)) {
                List<OrderItem> products = productService.getAll();
                if (products != null && !products.isEmpty()) {
                    out.println("<table><tr><th>ID</th><th>Name</th></tr>");
                    for (OrderItem product : products) {
                        out.println("<tr><td>");
                        out.println(product.getId());
                        out.println("</td><td>");
                        out.println(product.getName());
                        out.println("</td><td><a href=\"");
                        out.println(req.getContextPath() + "/items?action=doAdd&id=");
                        out.println(id + "&order=" + orderId + "&item=" + product.getId());
                        out.println("\">Add</a></td></tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No products found</p>");
                }
            } else if ("doAdd".equals(action)) {
                orderService.insertItem(
                        Integer.valueOf(orderId),
                        Integer.valueOf(req.getParameter("item")),
                        Integer.valueOf(id)
                );
                resp.sendRedirect("items?id=" + id + "&order=" + orderId);
            }
            out.println("</body></html>");
        }
    }
}
