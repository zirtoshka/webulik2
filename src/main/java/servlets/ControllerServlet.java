package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", value="/app", urlPatterns = "/app")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.sendRedirect("https://www.google.com");
//        getServletContext().getRequestDispatcher("/area-check").forward(req,resp );
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("https://www.google.com");
//        getServletContext().getRequestDispatcher("/area-check").forward(req,resp );
    }
    @Override
    public String getServletInfo() {
        return "servlet.ControllerServlet - defines the type of request, " +
                "and, depending on whether the request contains information about the coordinates of the point and the radius, " +
                "delegating its processing to one of the components listed below. " +
                "All requests inside the application should be passed to this servlet, other servlets from web pages should not be called directly.";
    }
}
