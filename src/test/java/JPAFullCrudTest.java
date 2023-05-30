import BaseTest.BaseTestJPA;
import org.example.entites.Student;
import org.junit.Test;

import static org.junit.Assert.*;

public class JPAFullCrudTest extends BaseTestJPA {


    @Test
    public void test_Student_Persistence() {

        // Create an Entity Student
        Student student = new Student();
        student.setName("Ilyas");
        student.setEmail("ilyas@outlook.com");

        // Persiste the  student to the DB
        entityManager.persist(student);
        entityManager.flush();

        // Retrieve the student from the db using its ID
        Long studentId = student.getId();
        Student retriveStudent = entityManager.find(Student.class, studentId);

        // testing
        assertNotNull(retriveStudent);
        assertEquals("Ilyas", retriveStudent.getName());
        assertEquals("ilyas@outlook.com", retriveStudent.getEmail());

    }

    @Test
    public void test_Student_update() {
        //  Create a new book
        Student student = new Student();
        student.setName("ilyass");

        //  persiste into db
        entityManager.persist(student);
        entityManager.flush();

        // update the specific entity
        student.setName("Abdelhamid");
        entityManager.merge(student);
        entityManager.flush();

        // reteive the update student
        Long studentId = student.getId();
        Student retrivedStudent = entityManager.find(Student.class, studentId);

        assertEquals("Abdelhamid", retrivedStudent.getName());

    }

    @Test
    public void test_student_Delete() {
        //  Create a new book
        Student student = new Student();
        student.setName("ilyass");

        //  persiste into db
        entityManager.persist(student);
        // use flush to persiste into db without ending the transaction
        entityManager.flush();

        // Delete the Specific Student
        entityManager.remove(student);
        entityManager.flush();

        // Attempt to retrive the deleted student
        Long idDeletedStudent = student.getId();
        Student deletedStudent = entityManager.find(Student.class, idDeletedStudent);

        // assert that the entity is deleted
        assertNull(deletedStudent);

    }


}
