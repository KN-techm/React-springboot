package com.ltts.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltts.exceptions.StudentNotFoundException;
import com.ltts.models.Student;
import com.ltts.repository.StudentRepository;
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;
	
	//get all students
	@GetMapping("/students")
public List<Student> getAllStudent(){
	return studentRepository.findAll();
}
	// create student 
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
		
	}
	//get student by student id
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Student student=studentRepository.findById(id)
				.orElseThrow(()->new StudentNotFoundException("Student does not exit with id"+id));
		return ResponseEntity.ok(student);
		
	}
	//update student record
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student studentDetails) {
		Student student=studentRepository.findById(id)
				.orElseThrow(()->new StudentNotFoundException("Student does not exit with id"+id));
		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setEmailId(studentDetails.getEmailId());
		student.setCity(studentDetails.getCity());
		
		Student updatedStudent=studentRepository.save(student);
		return ResponseEntity.ok(updatedStudent);
	}
	
	// delete student
	@DeleteMapping("/students/{id}")
	public ResponseEntity< Map < String, Boolean >> deleteStudent(@PathVariable Long id){
		Student student=studentRepository.findById(id)
				.orElseThrow(()->new StudentNotFoundException("Student does not exit with id"+id));
		studentRepository.delete(student);
		Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
	}
}

