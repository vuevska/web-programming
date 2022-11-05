package mk.finki.ukim.mk.lab.web.servlet;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.exceptions.CanNotPlaceOrderException;
import mk.finki.ukim.mk.lab.service.impl.OrderServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "balloon-order-servlet", urlPatterns = "/BalloonOrder")
public class BalloonOrderServlet extends HttpServlet {

    private final SpringTemplateEngine templateEngine;
    private final OrderServiceImpl orderService;
    private static int numberOfOrders = 0;

    public BalloonOrderServlet(SpringTemplateEngine templateEngine, OrderServiceImpl orderService) {
        this.templateEngine = templateEngine;
        this.orderService = orderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        this.templateEngine.process("deliveryInfo.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientName = req.getParameter("clientName");
        req.getSession().setAttribute("clientName", clientName);
        String clientAddress = req.getParameter("clientAddress");
        req.getSession().setAttribute("clientAddress", clientAddress);
        String color = (String) req.getSession().getAttribute("color");
        String size = (String) req.getSession().getAttribute("size");

        Order order = null;
        try {
            order = orderService.placeOrder(size, color, clientName, clientAddress);
        } catch (CanNotPlaceOrderException ex) {
            WebContext webContext = new WebContext(req, resp, req.getServletContext());
            webContext.setVariable("hasOrderError", true);
            webContext.setVariable("hasOrderErrorText", ex.getMessage());
            templateEngine.process("deliveryInfo.html", webContext, resp.getWriter());
        }

       /* WebContext orderContext = new WebContext(req, resp, req.getServletContext());
        orderContext.setVariable("numberOfOrders", ++numberOfOrders);*/

        getServletContext().setAttribute("numberOfOrders", ++numberOfOrders);

        req.getSession().setAttribute("order", order);
        resp.sendRedirect("/ConfirmationInfo");
    }
}
