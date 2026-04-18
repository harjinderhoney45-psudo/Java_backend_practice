import com.sun.net.httpserver.HttpServer;
import db.DBConnection;
import repository.StudentRepository;
import service.StudentService;
import controller.StudentController;

import java.net.InetSocketAddress;

public class main {
    public static void main(String[] args) {

        try {
            var con = DBConnection.getConnection();

            studentrepository repo = new studentrepository(con);
            studentservice service = new studentservice(repo);

            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/students", new studentcontroller(service));

            server.start();

            System.out.println("Server running at http://localhost:8080/students");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}