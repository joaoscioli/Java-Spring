package com.example.springMongoDB;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class JavaSpring_MongoDB {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpring_MongoDB.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address(
					"England",
					"NE9",
					"London"
			);
			String email = "jahmed@gmail.com";
			Student student = new Student(
					"Jamile",
					"Ahmed",
					email,
					Gender.FEMALE,
					address,
					List.of("Computer Science", "Math"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			//usingMongoTemplateAndQuery(StudentRepository repository,MongoTemplate, email,student)
			repository.findStudentByEmail(email)
					.ifPresentOrElse(s -> {
						System.out.println(s + "already exists");
					}, () -> {
						System.out.println("Inserting student" + student);
						repository.insert(student);
					});
		};
	}
	String email = "jahmed@gmail.com";
	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate,Student student) {
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));
			List<Student> students = mongoTemplate.find(query, Student.class);
			if(students.size()>1)
			{
				throw new IllegalStateException("found many student with email" + email);
			}
				if(students.isEmpty())
			{
				System.out.println("Inserting student" + student);
				repository.insert(students);
			} else {
				System.out.println(student + "already exists");
			}
		}
	}

