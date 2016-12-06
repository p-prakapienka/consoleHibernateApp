package by.prakapienka.at13java.web;

import by.prakapienka.at13java.model.OrderItem;
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

public class ProductServlet extends HttpServlet {

    private ConfigurableApplicationContext applicationContext;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
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

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Products</title></head><body>");
            out.println("<h1><a href=\"" + req.getContextPath() + "\">Main</a></h1>");
            out.println("<p><a href=\"" + req.getContextPath() + "/products?action=create\">Create</a></p>");
            if (action == null) {
                List<OrderItem> products = productService.getAll();
                if (products != null && !products.isEmpty()) {
                    out.println("<table><tr><th>ID</th><th>Name</th></tr>");
                    for (OrderItem product : products) {
                        out.println("<tr><td>");
                        out.println(product.getId());
                        out.println("</td><td>");
                        out.println(product.getName());
                        out.println("</td><td><a href=\"");
                        out.println(req.getContextPath() + "/products?action=update&id=");
                        out.println(product.getId());
                        out.println("\">Update</a></td><td><a href=\"");
                        out.println(req.getContextPath() + "/products?action=delete&id=");
                        out.println(product.getId());
                        out.println("\">Delete</a></td></tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No products found</p>");
                }
            } else if ("delete".equals(action)) {
                productService.delete(Integer.valueOf(req.getParameter("id")));
                resp.sendRedirect("products");
            } else if ("create".equals(action) || "update".equals(action)) {
                final OrderItem product = "create".equals(action) ?
                        new OrderItem("") :
                        productService.get(Integer.valueOf(req.getParameter("id")));
                out.println("<form method=\"post\" action=\"products\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"");
                out.println(product.getId());
                out.println("\"/><label>Name:<input name=\"name\" value=\"");
                out.println(product.getName());
                out.println("\"/></label><input type=\"submit\" value=\"Ok\"/>");
                out.print("<p><a href=\"" + req.getContextPath());
                out.print("/products\">Back</a></p>");
            }
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id").trim();

        final OrderItem product = new OrderItem("null".equals(id) ? null : Integer.valueOf(id),
                req.getParameter("name").trim());

        if (id.isEmpty()) {
            productService.insert(product);
        } else {
            productService.update(product);
        }
        resp.sendRedirect("products");
    }
}
