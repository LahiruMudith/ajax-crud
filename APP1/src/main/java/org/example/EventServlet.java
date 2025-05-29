package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@WebServlet("/event")
public class EventServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            ResultSet resultSet = connection.prepareStatement("SELECT * FROM event").executeQuery();

            List<Map<String, String>> eventList = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, String> eList = new HashMap<>();
                eList.put("eid", resultSet.getString("id"));
                eList.put("ename", resultSet.getString("name"));
                eList.put("edescription", resultSet.getString("description"));
                eList.put("edate", resultSet.getString("date"));
                eList.put("eplace", resultSet.getString("place"));

                eventList.add(eList);
            }

            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(resp.getWriter(), eventList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(req.getReader());

            PreparedStatement resultSet = connection.prepareStatement("INSERT INTO event VALUES (?,?,?,?,?)");
            resultSet.setObject(1, node.get("eid").asText());
            resultSet.setObject(2, node.get("ename").asText());
            resultSet.setObject(3, node.get("edescription").asText());
            resultSet.setObject(4, node.get("edate").asText());
            resultSet.setObject(5, node.get("eplace").asText());

            int i = resultSet.executeUpdate();

            resp.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
            resp.setContentType("text/plain");
            if (i > 0) {
                resp.setContentType("text/plain");
                resp.getWriter().write("success");
            }else{
                resp.setContentType("text/plain");
                resp.getWriter().write("Fail");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(req.getReader());

            PreparedStatement resultSet = connection.prepareStatement("UPDATE event SET name=?, description=?, date=?, place=? WHERE id=?");
            resultSet.setObject(1, node.get("ename").asText());
            resultSet.setObject(2, node.get("edescription").asText());
            resultSet.setObject(3, node.get("edate").asText());
            resultSet.setObject(4, node.get("eplace").asText());
            resultSet.setObject(5, node.get("eid").asText());

            int i = resultSet.executeUpdate();
            if (i > 0) {
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setContentType("text/plain");
                resp.getWriter().write("success");
            }else{
                resp.setContentType("text/plain");
                resp.getWriter().write("Fail");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(req.getReader());

            PreparedStatement resultSet = connection.prepareStatement("DELETE FROM event WHERE id=?");
            resultSet.setObject(1, node.get("eid").asText());

            int i = resultSet.executeUpdate();
            if (i > 0) {
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setContentType("text/plain");
                resp.getWriter().write("success");
            }else{
                resp.setContentType("text/plain");
                resp.getWriter().write("Fail");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}