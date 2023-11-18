package servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import exceptions.WrongDataException;
import validator.DataChecker;
import validator.Point;
import validator.PointsStorage;

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

    private PointsStorage getTableContent(HttpSession session) {
        return (session.getAttribute("tableContent") == null) ? new PointsStorage() : (PointsStorage) session.getAttribute("tableContent");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        startTime = System.currentTimeMillis();
        HttpSession session = req.getSession();

        PrintWriter writer = resp.getWriter();
        PointsStorage tableContent = getTableContent(session);


        List<Point> points = tableContent.getPoints();
        JsonNode requestData = mapper.readTree(req.getReader());
        Double x = null;
        List<Double> xValues = null;
        Double y = null;
        Double r = null;
        Boolean isForm = null;

        try {
            JsonNode xNode = requestData.get("x");
            xValues = getPointsList(xNode);
            y = requestData.get("y").asDouble();
             r = requestData.get("r").asDouble();
             isForm = requestData.get("isForm").asBoolean();
        } catch (Exception e) {
            resp.sendRedirect("./badRequest");
        }



        if (isForm == null) {
            resp.sendRedirect("./badRequest");
        }if(isForm){
            for (Double xV : xValues) {
                try {
                    if (dataChecker.checkXYR(xV, y, r)) {
                        addToTableContent(xV, y, r, tableContent, req);
                    }
                } catch (WrongDataException e) {
                    resp.sendRedirect("./badRequest");
                }

            }
            resp.sendRedirect("./result");



        } else {
            x = requestData.get("x").asDouble();

            if (dataChecker.checkAreaForGraph(x,y,r)) {

                Gson gson = new Gson();
                Map<String, Object> json = new HashMap<>();
                json.put("x", x);
                json.put("y", y);
                json.put("r", r);
                json.put("nowTime", dataFormatter(LocalDateTime.now()));
                json.put("script_time", System.currentTimeMillis() - startTime + " ms");
                json.put("result", dataChecker.checkKill(x, y, r) ? "kill" : "miss");
                String jsonString = gson.toJson(json);

                resp.setContentType("application/json");
                resp.getWriter().write(jsonString);

                addToTableContent(x, y, r, tableContent, req);
            }
        }


        writer.close();
        session.setAttribute("tableContent", tableContent);

    }




    private void addToTableContent(Double x, Double y, Double r, PointsStorage tableContent, HttpServletRequest req) {
        Point point = new Point(x, y, r, dataChecker.checkKill(x, y, r),
                System.currentTimeMillis() - startTime + " ms", dataFormatter(LocalDateTime.now()));
        tableContent.addPoint(point);

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
        }
        return xValues;
    }


}
