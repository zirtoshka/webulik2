package servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import exceptions.WrongDataException;
import validator.DataChecker;
import validator.Point;
import validator.PointsStorage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet(name = "AreaCheck", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    long startTime;
    DataChecker dataChecker = new DataChecker();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        startTime = System.currentTimeMillis();
//        LocalDateTime start=LocalDateTime.now();
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();
        PointsStorage tableContent = (PointsStorage) session.getAttribute("tableContent");
        if (tableContent == null) tableContent = new PointsStorage();
        List<Point> points = tableContent.getPoints();
        LocalDateTime currentDateTime = LocalDateTime.now();
        JsonNode requestData = mapper.readTree(req.getReader());
        System.out.println("jopajopa");

        double x = 10;//todo: no initialization
        try {
            try {
                x = requestData.get("x").asDouble();
            } catch (Exception e) {
                //todo: to do smt
                System.out.println("fdjgkjdfgjkdgkjdgkjdgkjdgkjdgjkdgjkdgjkdfgkjdf");
            }
//            System.out.println(x);
            double y = requestData.get("y").asDouble();
            double r = requestData.get("r").asDouble();
            boolean isForm = requestData.get("isForm").asBoolean();
            System.out.println(x);
            System.out.println(y);
            System.out.println(r);
            System.out.println(isForm);

            if (isForm){
                req.setAttribute("x", x);
                req.setAttribute("y", y);
                req.setAttribute("r", r);
                req.setAttribute("result", dataChecker.checkKill(x, y, r)? "kill":"miss");
                req.setAttribute("now", dataFormatter(LocalDateTime.now()));
                req.setAttribute("script_time", System.currentTimeMillis()-startTime+" ms");

                resp.setContentType("text/html");

                ServletContext servletContext = getServletContext();
                System.out.println("Before forwarding to result.jsp");

                req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
                System.out.println("After forwarding to result.jsp");

                resp.getWriter().println("script_time: " + req.getAttribute("script_time"));
                resp.getWriter().flush();

        }else {

            if (dataChecker.checkXYR(x, y, r)) {
                Gson gson = new Gson();
                Map<String, Object> json = new HashMap<>();
                json.put("x", x);
                json.put("y", y);
                json.put("r", r);
                json.put("result", dataChecker.checkKill(x, y, r)? "kill":"miss");
                json.put("now", dataFormatter(LocalDateTime.now()));
                json.put("script_time", System.currentTimeMillis()-startTime+" ms");
                String jsonString = gson.toJson(json);
                PrintWriter out = resp.getWriter();
                out.print(jsonString);
                out.flush();
            }}


        } catch (WrongDataException e) {
            //todo
            System.out.println("eblanerror");
        }
        System.out.println("ebobo");
        if (points.size() > 0) {
            writer.println(convertToJSON(points));
        }
        writer.close();
        session.setAttribute("tableContent", tableContent);

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

    private String dataFormatter(LocalDateTime localeDataTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localeDataTime.format(formatter);

        return formattedDateTime;
    }
//    LocalDateTime currentDateTime = LocalDateTime.now();


}
