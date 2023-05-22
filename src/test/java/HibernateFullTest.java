import org.example.entites.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateFullTest {

    //the session factory is hibernate
    private SessionFactory sessionFactory;

    @BeforeEach
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    @Test
    void save_myFirstObject_to_DB (){
        //Given
        Student student = new Student("Ahmed" , "Ahmed@mail.com");

        try(Session session = sessionFactory.openSession();){
            session.beginTransaction();

            //
            session.persist(student);
            session.getTransaction().commit();
        }}


    @Test
    void change_name_Of_student (){

        Session session = sessionFactory.openSession();
//        HibernateLifecycleUtil.getManagedEntities(session);
        List<Student> student = session.createQuery("from Student ").getResultList();
        System.out.println(student.get(0));
    }

    @AfterEach
    protected void tearDown() throws Exception {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

//    @SuppressWarnings("unchecked")
//    @Test
//     void testBasicUsage() {
//        // create a couple of events...
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        session.remove(new Student("Marco's Friend", LocalDate.now()));
//
//        session.getTransaction().commit();
//        session.close();
//
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        List<Student> result = session.createQuery( "select u from Student u" , Student.class).list();
//        for ( Student user : result) {
//            System.out.println( "Student (" + user.getName() + ") : " + user.getBirthDate() );
//        }
//
//
//        session.getTransaction().commit();
//        session.close();
//    }

    @Test
     void marco_is_in_the_house() {
        assertThat( 1).isGreaterThanOrEqualTo(0);
    }



}