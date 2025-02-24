package com.andres.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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

		create();

		findOne();

	}

	@Transactional(readOnly = true)
	public void findOne(){
		personRepository.findByNameContaining("nard").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void list(){
		
		List<Person> persons = (List<Person>) personRepository.buscarByProgrammingLanguage("Java");

		persons.stream().forEach(System.out::println);

		System.out.println("===========================================================");
		List<Object[]> personData = personRepository.obtenerPersonData();

		personData.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}

	@Transactional
	public void create(){
		
		Person person = new Person();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese el nombre de la persona: ");
		person.setName(scanner.nextLine());
		System.out.println("Ingrese el apellido de la persona: ");
		person.setlastname(scanner.nextLine());
		System.out.println("Ingrese el lenguaje de programacion de la persona: ");
		person.setProgrammingLanguage(scanner.nextLine());

		personRepository.save(person);

		scanner.close();
	}

}
