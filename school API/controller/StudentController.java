package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.studentservice;
import model.student;
import java.io.*;
import java.util.List;

public class studentcontroller implements HttpHandler {

    private studentservice service;

    public studentcontroller(studentservice service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            if (method.equals("GET")) {
                List<student> students = service.getStudents();
                String response = "";

                for (student s : students) {
                    response += s.getId() + "," + s.getName() + "," + s.getMarks() + "\n";
                }

                sendResponse(exchange, response);

            } else if (method.equals("POST")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String body = br.readLine(); // id,name,marks

                String[] data = body.split(",");
                service.addStudent(
                        Integer.parseInt(data[0]), data[1],
                        Integer.parseInt(data[2])
                );

                sendResponse(exchange, "Student Added");

            } else if (method.equals("PUT")) {
                String[] parts = path.split("/");
                int id = Integer.parseInt(parts[2]);

                BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                int marks = Integer.parseInt(br.readLine());

                boolean updated = service.updateStudent(id, marks);
                sendResponse(exchange, updated ? "Updated" : "Not Found");

            } else if (method.equals("DELETE")) {
                String[] parts = path.split("/");
                int id = Integer.parseInt(parts[2]);

                boolean deleted = service.deleteStudent(id);
                sendResponse(exchange, deleted ? "Deleted" : "Not Found");
            }

        } catch (Exception e) {
            sendResponse(exchange, "Error: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}