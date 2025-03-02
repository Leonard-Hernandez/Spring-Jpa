package com.andres.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.andres.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
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

		//perzonalizedQueries();
		
		//perzonalizedQueries2();

		//perzonalizedQueriesDistinct();

		//perzonalizedQueriesConcatUpperAndLowerCase();

		perzonalizedQueriesBetween();
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

	@Transactional(readOnly = true)
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

		System.out.println("===========================================================");
		System.out.println("Consulta por campos personalizados por el id \n Ingrese el id de la persona: ");
		id = scanner.nextLong();
		Object[] personReg =(Object[]) personRepository.obtenerPersonDataById(id);
		System.out.println(personReg[0] + " " + personReg[1] +" es experto en " + personReg[3]);

		System.out.println("===========================================================");
		System.out.println("Consulta por campos personalizados Lista");
		List<Object[]> personRegList = personRepository.obtenerPersonDataList();
		personRegList.forEach( p -> System.out.println(p[0] + " " + p[1] +" es experto en " + p[2]));
		
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void perzonalizedQueries2(){
		System.out.println("===========================================================");
		System.out.println("Consulta nombre y lenguaje de programacion: ");
		List<Object[]> personRegList = personRepository.findAllMixPerson();

		personRegList.forEach(p -> {
			System.out.println("programingLanguage: " + p[1] + " name: " + p[0]);
		});

		System.out.println("===========================================================");
		System.out.println("Consulta con constructor dinamico");
		List<PersonDto> personRegList2 = personRepository.findAllClassPerson();

		personRegList2.forEach(p -> {
			System.out.println(p);
		});
	}

	@Transactional(readOnly = true)
	public void perzonalizedQueriesDistinct(){
		
		System.out.println("===========================================================");
		System.out.println("Consulta con nombres de personas");
		List<String> personRegList = personRepository.findAllNames();

		personRegList.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta con nombres de personas con distinct");
		List<String> personRegList2 = personRepository.findAllNamesDistinct();

		personRegList2.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta con lenguajes de programacion con distinct");
		List<String> personRegList3 = personRepository.findAllProgrammingLanguagesDistict();

		personRegList3.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta count lenguajes de programacion");
		Integer personRegList4 = personRepository.findAllProgrammingLanguagesDistictCount();

		System.out.println(personRegList4);

	}

	@Transactional(readOnly = true)
	public void perzonalizedQueriesConcatUpperAndLowerCase(){

		System.out.println("===========================================================");
		System.out.println("Consulta con nombres y apellidos de personas");

		List<String> personRegList = personRepository.findAllFullNameConcat();

		personRegList.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta con nombres y apellidos de personas en minuscula");

		List<String> personRegList2 = personRepository.findAllFullNameConcatLower();

		personRegList2.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta con nombres y apellidos de personas en mayuscula");

		List<String> personRegList3 = personRepository.findAllFullNameConcatUpper();

		personRegList3.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void perzonalizedQueriesBetween(){

		System.out.println("===========================================================");
		System.out.println("Consultas usuarios id entre 2 y 5");

		List<Person> personRegList = personRepository.findAllBetween();

		personRegList.forEach(System.out::println);

		System.out.println("===========================================================");
		System.out.println("Consulta usuarios nombre entre j y p");

		List<Person> personRegList2 = personRepository.findAllBetweenName();

		personRegList2.forEach(System.out::println);
	}
}
