import baseTest.BaseTestHibernate;
import org.example.entites.Student;
import org.junit.Test;

import static org.junit.Assert.*;


public class HibernateFullTest extends BaseTestHibernate {

    @Test
    public void student_persistence() {
        // Create a Student entity
        Student student = new Student("Ahmed", "Ahmed@mail.com");

        // Persist the student entity to the Db (ORM)
        session.persist(student);
        session.flush();

        // retrieve the student from db
        Student retriveStudent = session.get(Student.class, student.getId());

        assertNotNull(retriveStudent);
        assertEquals("Ahmed", retriveStudent.getName());
        assertEquals("Ahmed@mail.com", retriveStudent.getEmail());
    }


    @Test
    public void student_Update() {
        // Create a Student Entity
        Student student = new Student("Ali", "ALi@outlook.com");

        // Persist to BD
        session.persist(student);
        session.flush();

        // update the entity in Db
        student.setName("Mohammed");
        student.setEmail("Mohammedh@mail.com");
        session.merge(student);
        session.flush();


        //testing the update result
        Student retriveStudent = session.get(Student.class, student.getId());
        assertNotNull(retriveStudent);
        assertEquals("Mohammed", retriveStudent.getName());
        assertEquals("Mohammedh@mail.com", retriveStudent.getEmail());

    }

    @Test
    public void student_Delete() {
        // Create a Student Entity
        Student student = new Student("Ali", "ALi@outlook.com");

        // Persist to BD
        session.persist(student);
        session.flush();

        // remove the entity in Db
        session.remove(student);
        session.flush();


        //testing the update result
        Student retriveStudent = session.get(Student.class, student.getId());
        assertNull(retriveStudent);
    }

}