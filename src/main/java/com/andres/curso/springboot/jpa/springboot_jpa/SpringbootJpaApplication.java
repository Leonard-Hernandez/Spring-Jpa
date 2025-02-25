package com.andres.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
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

		// create();

		// findOne();

		// update();

		//delete();

		perzonalizedQueries();
	}

	@Transactional(readOnly = true)
	public void findOne() {
		personRepository.findByNameContaining("nard").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void list() {

		List<Person> persons = (List<Person>) personRepository.buscarByProgrammingLanguage("Java");

		persons.stream().forEach(System.out::println);

		System.out.println("===========================================================");
		List<Object[]> personData = personRepository.obtenerPersonData();

		personData.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}

	@Transactional
	public void create() {

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

	@Transactional
	public void update() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();

		Optional<Person> person = personRepository.findById(id);

		person.ifPresent(p -> {
			System.out.println(p);

			System.out.println("Ingrese el lenguaje de programacion de la persona: ");
			p.setProgrammingLanguage(scanner.nextLine());

			Person pNew = personRepository.save(p);

			System.out.println("Persona actualizada: " + pNew);

		});

		scanner.close();

	}

	@Transactional
	public void delete() {
		Scanner scanner = new Scanner(System.in);

		personRepository.findAll().forEach(System.out::println);

		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();
		scanner.close();

		personRepository.deleteById(id);

		System.out.println("Persona eliminada");
		personRepository.findAll().forEach(System.out::println);

	}

	@Transactional
	public void delete2() {
		Scanner scanner = new Scanner(System.in);

		personRepository.findAll().forEach(System.out::println);

		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();
		scanner.close();

		personRepository.findById(id).ifPresentOrElse(
			person -> {
				personRepository.delete(person);
				System.out.println("Persona eliminada");
				personRepository.findAll().forEach(System.out::println);
			}, 
			() -> System.out.println("No se encontro la persona")
		);

	}

	@Transactional
	public void perzonalizedQueries(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("===========================================================");
		System.out.println("Consulta nombre por id \n Ingrese el id de la persona: ");
		Long id = scanner.nextLong();
		System.out.println("El nombre de la persona es " + personRepository.getNameById(id));

		System.out.println("===========================================================");
		System.out.println("Consulta nombre y apellido por id \n Ingrese el id de la persona: ");
		id = scanner.nextLong();
		System.out.println("El nombre y apellido de la persona es " + personRepository.getFullNameById(id));
		
		scanner.close();
	}

}
