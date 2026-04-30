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

    private final static Map<Long, StudentV2> students = new HashMap<>() {
        {
            put(1L, new StudentV2() {{
                setId(1L);
                setName("John Doe");
                setEmail("john@example.com");
            }});
        }
    };

    public Collection<StudentV2> getStudents() {
        LOGGER.info("Getting students");
        return students.values();
    }

    public StudentV2 getStudentById(Long id) {
        LOGGER.info("Getting one student by Id {}", id);
        return students.get(id);
    }

    public StudentV2 addStudent(StudentV2 studentV2) {
        LOGGER.info("Adding student {}", studentV2);
        if (students.get(studentV2.getId()) != null) {
            LOGGER.error("Student with ID {} already exists", studentV2.getId());
            return null;
        }

        students.put(studentV2.getId(), studentV2);
        LOGGER.info("Student added with id {}", studentV2.getId());
        return studentV2;
    }

    public List<StudentV2> getStudentByName(String name) {
        LOGGER.info("Getting list of students by name {}", name);
        List<StudentV2> studentsWithProvidedName = students.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name)).toList();

        LOGGER.info("Found {} students by name {}", studentsWithProvidedName.size(), name);
        return studentsWithProvidedName;
    }

        public StudentV2 updateStudent(StudentV2 studentV2) {
            LOGGER.info("Updating student {}", studentV2.getName());
            if (students.get(studentV2.getId()) == null) {
                LOGGER.error("Student with ID {} does not exist", studentV2.getId());
                return null;
            }

            students.put(studentV2.getId(), studentV2);
            LOGGER.info("Student updated with id {}", studentV2.getId());
            return studentV2;
        }

        public boolean deleteStudent(Long id) {
        LOGGER.info("Deleting student with id {}", id);
        return students.remove(id) != null;
        }
}
