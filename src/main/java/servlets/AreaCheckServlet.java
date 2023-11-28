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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet(name = "AreaCheck", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataChecker dataChecker = new DataChecker();
        ObjectMapper mapper = new ObjectMapper();

        long startTime = System.currentTimeMillis();
        HttpSession session = req.getSession();

        PrintWriter writer = resp.getWriter();
        PointsStorage tableContent = getTableContent(session);
        List<Point> points = tableContent.getPoints();
        JsonNode requestData = mapper.readTree(req.getReader());
        Double x = null;
        List<Double> xValues = null;
        BigDecimal y = null;
        BigDecimal r = null;
        Boolean isForm = null;

        try {
            JsonNode xNode = requestData.get("x");
            xValues = getPointsList(xNode);
            y = new BigDecimal(String.valueOf((requestData.get("y"))).replace("\"",""));
             r = BigDecimal.valueOf(requestData.get("r").asDouble());
             isForm = requestData.get("isForm").asBoolean();
        } catch (Exception e) {
            resp.sendRedirect("./badRequest");
        }


    if (!resp.isCommitted()){
        if (isForm == null ) {
            resp.sendRedirect("./badRequest");
        }if(isForm){
            for (Double xV : xValues) {
                try {
                    if (dataChecker.checkXYR(BigDecimal.valueOf(xV), y, r)) {
                        addToTableContent(BigDecimal.valueOf(xV), y, r, startTime, tableContent, dataChecker);
                    }
                } catch (WrongDataException e) {
                    resp.sendRedirect("./badRequest");
                }

            }
            resp.sendRedirect("./result");



        } else {
            x = requestData.get("x").asDouble();

            if (dataChecker.checkAreaForGraph(BigDecimal.valueOf(x),y,r)) {

                Gson gson = new Gson();
                Map<String, Object> json = new HashMap<>();
                json.put("x", x);
                json.put("y", y);
                json.put("r", r);
                json.put("nowTime", dataFormatter(LocalDateTime.now()));
                json.put("script_time", System.currentTimeMillis() - startTime + " ms");
                json.put("result", dataChecker.checkKill(BigDecimal.valueOf(x), y, r) ? "kill" : "miss");
                String jsonString = gson.toJson(json);

                resp.setContentType("application/json");
                resp.getWriter().write(jsonString);

                addToTableContent(BigDecimal.valueOf(x), y, r, startTime, tableContent, dataChecker);
            }
        }}


        writer.close();
        session.setAttribute("tableContent", tableContent);

    }


    private PointsStorage getTableContent(HttpSession session) {
        return (session.getAttribute("tableContent") == null) ? new PointsStorage() : (PointsStorage) session.getAttribute("tableContent");
    }

    private void addToTableContent(BigDecimal x, BigDecimal y, BigDecimal r, long startTime, PointsStorage tableContent, DataChecker dataChecker) {
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
