package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final static Map<Long, Student> students = new HashMap<>() {
        {
            put(1L, new Student() {{
                setId(1L);
                setName("John Doe");
                setEmail("john@example.com");
            }});
        }
    };

    public Collection<Student> getStudents() {
        LOGGER.info("Getting students");
        return students.values();
    }

    public Student getStudentById(Long id) {
        LOGGER.info("Getting one student by Id {}", id);
        return students.get(id);
    }

    public Student addStudent(Student student) {
        LOGGER.info("Adding student {}", student);
        if (students.get(student.getId()) != null) {
            LOGGER.error("Student with ID {} already exists", student.getId());
            return null;
        }

        students.put(student.getId(), student);
        LOGGER.info("Student added with id {}", student.getId());
        return student;
    }

    public List<Student> getStudentByName(String name) {
        LOGGER.info("Getting list of students by name {}", name);
        List<Student> studentsWithProvidedName = students.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name)).toList();

        LOGGER.info("Found {} students by name {}", studentsWithProvidedName.size(), name);
        return studentsWithProvidedName;
    }

        public Student updateStudent(Student student) {
            LOGGER.info("Updating student {}", student.getName());
            if (students.get(student.getId()) == null) {
                LOGGER.error("Student with ID {} does not exist", student.getId());
                return null;
            }

            students.put(student.getId(), student);
            LOGGER.info("Student updated with id {}", student.getId());
            return student;
        }

        public boolean deleteStudent(Long id) {
        LOGGER.info("Deleting student with id {}", id);
        return students.remove(id) != null;
        }
}
