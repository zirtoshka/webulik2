package servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.WrongDataException;
import validator.DataChecker;
import validator.Point;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AreaCheck", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    long startTime;
    DataChecker dataChecker = new DataChecker();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        startTime = System.currentTimeMillis();
        JsonNode requestData = mapper.readTree(req.getReader());

        double x = 10;//todo: no initialization
        try {
            try {
                x = requestData.get("x").asDouble();
            } catch (Exception e) {
                //todo: to do smt
                System.out.println("fdjgkjdfgjkdgkjdgkjdgkjdgkjdgjkdgjkdgjkdfgkjdf");
            }
            System.out.println(x);
            double y = requestData.get("y").asDouble();
            double r = requestData.get("r").asDouble();
            //todo: time
//            int timeReq=Integer.parseInt(req.getParameter("time"))
            if (dataChecker.checkXYR(x, y, r)) {
                //todo пошел нахуй
            }
            if (dataChecker.checkXYR(x, y, r)) {
                //todo kill
            }
            // TODO: miss
            //todo: make resul
        } catch (WrongDataException e) {
        }
    }

    private String convertToJSON(List<Point> points) {
        if (points.isEmpty()) {
            return "[]";
        }
        StringBuilder output = new StringBuilder("[");
        for (Point point : points) {
            output.append(point.toString()).append(",\n");
        }
        output.delete(output.length() - 2, output.length()); // Delete the last comma and the line feed
        output.append("]");
        return output.toString();
    }

}
