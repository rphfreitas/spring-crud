package com.courses.controller;

import com.courses.model.Course;
import com.courses.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public @ResponseBody List<Course> listar() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Course> buscaPorId(@PathVariable Long id) {
        return courseRepository.findById(id).map(course -> ResponseEntity.ok().body(course)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Course> editar(@PathVariable Long id, @RequestBody Course course) {
        return courseRepository.findById(id).map(record -> {
            record.setName(course.getName());
            record.setCategory(course.getCategory());
            Course updated = courseRepository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public @ResponseBody <Void> ResponseEntity deletar(@PathVariable Long id) {
        return courseRepository.findById(id).map(record -> {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Course> salvar(@RequestBody Course course) {
        log.info("salvando o curso" + course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }
}
