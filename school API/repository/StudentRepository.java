package repository;

import model.student;
import java.sql.*;
import java.util.*;

public class studentrepository {

    private Connection con;

    public studentrepository(Connection con) {
        this.con = con;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, marks INTEGER)";
        con.createStatement().executeUpdate(sql);
    }

    public void insert(student s) throws SQLException {
        String sql = "INSERT INTO students VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, s.getId());
        ps.setString(2, s.getName());
        ps.setInt(3, s.getMarks());
        ps.executeUpdate();
    }

    public List<student> getAll() throws SQLException {
        List<student> list = new ArrayList<>();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");

        while (rs.next()) {
            list.add(new student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("marks")
            ));
        }
        return list;
    }

    public boolean update(int id, int marks) throws SQLException {
        String sql = "UPDATE students SET marks=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, marks);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}