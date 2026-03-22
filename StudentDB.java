import java.sql.*;
import java.util.Scanner;

public class StudentDB {

    // SQLite connection
    static final String URL = "jdbc:sqlite:students.db";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLite Database!\n");

            while (true) {
                System.out.println("\n==== MENU ====");
                System.out.println("1. Create Table");
                System.out.println("2. Insert Data");
                System.out.println("3. View Data");
                System.out.println("4. Update Data");
                System.out.println("5. Delete Data");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        createTable(con);
                        break;

                    case 2:
                        insertData(con, sc);
                        break;

                    case 3:
                        viewData(con);
                        break;

                    case 4:
                        updateData(con, sc);
                        break;

                    case 5:
                        deleteData(con, sc);
                        break;

                    case 6:
                        con.close();
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // For Creating Table
    static void createTable(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, marks INTEGER)";
        stmt.executeUpdate(sql);
        System.out.println("Table created (if not exists).");
    }

    // For Adding Data
    static void insertData(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        String sql = "INSERT INTO students VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, marks);

        ps.executeUpdate();
        System.out.println("Data inserted.");
    }

    // For Viewing Data
    static void viewData(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

        System.out.println("\nID\tName\tMarks");
        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + "\t" +
                rs.getString("name") + "\t" +
                rs.getInt("marks")
            );
        }
    }

    // To Update Data
    static void updateData(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();

        System.out.print("Enter new marks: ");
        int marks = sc.nextInt();

        String sql = "UPDATE students SET marks=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, marks);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Data updated.");
        else
            System.out.println("ID not found.");
    }

    // Delete Data
    static void deleteData(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Data deleted.");
        else
            System.out.println("ID not found.");
    }
}
