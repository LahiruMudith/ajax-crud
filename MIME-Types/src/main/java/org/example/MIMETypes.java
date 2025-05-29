package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@WebServlet("/mime")
@MultipartConfig
public class MIMETypes extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //read text/plain data form http request body
//        String body = new BufferedReader(new InputStreamReader(
//                req.getInputStream())
//        ).lines().collect(Collectors.joining("\n"));
//
//        resp.setContentType("text/plain");
//        resp.getWriter().print(body);
//    }

//    //read x-www-form-urlencoded data form http request body
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//
//        resp.setContentType("text/plain");
//        resp.getWriter().println("Name: " + name);
//        resp.getWriter().println("Address: " + address);
//    }

    //read multipart/form data form http request body
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String name = req.getParameter("name");
//        Part image = req.getPart("image");
//        String submittedFileName = image.getSubmittedFileName();
//
//        resp.setContentType("text/plain");
//        resp.getWriter().println("Name: " + name);
//        resp.getWriter().println("Image: " + submittedFileName);
//    }

    //read JSON data form http request body
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        JsonNode jsonNode = objectMapper.readTree(req.getReader());
//        String name = jsonNode.get("name").asText();
//        String address = jsonNode.get("address").asText();
//
//        resp.setContentType("text/plain");
//        resp.getWriter().write(name +" " + address);
//    }

    //read JSON Array data form http request body
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<JsonNode> jsonNodes = objectMapper.readValue(req.getReader(), new TypeReference<List<JsonNode>>() {
        });



        jsonNodes.forEach(node -> {
            String name = node.get("name").asText();
            String address = node.get("address").asText();
            System.out.println(name + " " + address);
        });


        resp.setContentType("text/plain");
        resp.getWriter().write(jsonNodes.size() + " records received successfully");
    }
}