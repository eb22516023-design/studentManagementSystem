package com.example.demo;

import lombok.extern.slf4j.Slf4j
        ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<?> CreateStudent( @RequestBody Student student){
        Student s = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);

    }
    @GetMapping
    public ResponseEntity<?> getStudents(){
        log.info("This is my student");
        return ResponseEntity.ok(studentService.getStudents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id ){
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateStudent(@RequestBody Student data, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.updateStudent(data, id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "Student deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
