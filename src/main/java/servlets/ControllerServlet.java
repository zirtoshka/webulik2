package servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", value="/app")
public class ControllerServlet extends HttpServlet {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        JsonNode requestData = mapper.readTree(req.getReader());
        String action = requestData.get("action").asText();

//        String action = req.getParameter("action");
        System.out.println("action: "+action);

        if (action==null) {

//            System.out.println(action);
            System.out.println("its clear");
            getServletContext().getRequestDispatcher("/clear").forward(req,resp );
        }else{
            System.out.println("its not clear");
            getServletContext().getRequestDispatcher("/area-check").forward(req,resp );
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/area-check").forward(req,resp );
    }
    @Override
    public String getServletInfo() {
        return "servlet.ControllerServlet - defines the type of request, " +
                "and, depending on whether the request contains information about the coordinates of the point and the radius, " +
                "delegating its processing to one of the components listed below. " +
                "All requests inside the application should be passed to this servlet, other servlets from web pages should not be called directly.";
    }
}
