package service;

import model.student;
import repository.studentrepository;
import java.util.*;

public class studentservice {
    
    private studentrepository repo;

    public studentservice(studentrepository repo) {
        this.repo = repo;
    }

    public void createTable() throws Exception {
        repo.createTable();
    }

    public void addStudent(int id, String name, int marks) throws Exception {
        repo.insert(new student(id, name, marks));
    }

    public List<student> getStudents() throws Exception {
        return repo.getAll();
    }

    public boolean updateStudent(int id, int marks) throws Exception {
        return repo.update(id, marks);
    }

    public boolean deleteStudent(int id) throws Exception {
        return repo.delete(id);
    }
}