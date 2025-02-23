package com.andres.curso.springboot.jpa.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andres.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.andres.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		findOne();

	}

	public void findOne(){
		personRepository.findByNameContaining("ria").ifPresent(System.out::println);
	}

	public void list(){
		
		List<Person> persons = (List<Person>) personRepository.buscarByProgrammingLanguage("Java");

		persons.stream().forEach(System.out::println);

		System.out.println("===========================================================");
		List<Object[]> personData = personRepository.obtenerPersonData();

		personData.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}

}
