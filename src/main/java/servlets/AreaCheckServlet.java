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
import java.util.stream.Collectors;

@WebServlet(name = "AreaCheck", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    long startTime;
    DataChecker dataChecker = new DataChecker();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        startTime = System.currentTimeMillis();
        HttpSession session = req.getSession();

        PrintWriter writer = resp.getWriter();
        PointsStorage tableContent = (PointsStorage) session.getAttribute("tableContent");

        if (tableContent == null) tableContent = new PointsStorage();

        List<Point> points = tableContent.getPoints();
        JsonNode requestData = mapper.readTree(req.getReader());
        Double x = null;
        List<Double> xValues = null;
        try {
            try {
                JsonNode xNode = requestData.get("x");
                xValues=getPointsList(xNode);
            } catch (Exception e) {
                //todo: to do smt
                System.out.println("fdjgkjdfgjkdgkjdgkjdgkjdgkjdgjkdgjkdgjkdfgkjdf");
            }
            double y = requestData.get("y").asDouble();
            double r = requestData.get("r").asDouble();
            boolean isForm = requestData.get("isForm").asBoolean();


            if (isForm) {
                for (Double xV : xValues) {
                    try {
                        if (dataChecker.checkXYR(xV, y, r)) {
                            Point point = new Point(xV, y, r, dataChecker.checkKill(xV, y, r),
                                    System.currentTimeMillis() - startTime + " ms", dataFormatter(LocalDateTime.now()));
                            tableContent.addPoint(point);
                        }
                    } catch (WrongDataException e) {
                        //todo norm catch
                        throw new RuntimeException(e);
                    }

                }
                req.setAttribute("tableContent", tableContent);
                resp.sendRedirect("./result");
                System.out.println("dddddddddd");


            } else {

                if (dataChecker.checkXYR(x, y, r)) {
                    Gson gson = new Gson();
                    Map<String, Object> json = new HashMap<>();
                    json.put("x", x);
                    json.put("y", y);
                    json.put("r", r);
                    json.put("result", dataChecker.checkKill(x, y, r) ? "kill" : "miss");
                    json.put("now", dataFormatter(LocalDateTime.now()));
                    json.put("script_time", System.currentTimeMillis() - startTime + " ms");
                    String jsonString = gson.toJson(json);
                    PrintWriter out = resp.getWriter();
                    out.print(jsonString);
                    out.flush();
                }
            }


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

    private List<Double> getPointsList(JsonNode xNode) {
        List<Double> xValues = new ArrayList<>();

        if (xNode.isArray()) {
            for (JsonNode element : xNode) {
                xValues.add(element.doubleValue());
            }
        } return xValues;
    }
//    LocalDateTime currentDateTime = LocalDateTime.now();


}
