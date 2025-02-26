package com.andres.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.andres.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT new Person(p.name, p.lastname) from Person p")
    List<Person> findAllClassPerson();

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id = ?1")
    Object obtenerPersonDataById(Long id);

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    Optional<Person> findOneName(String name);

    @Query("SELECT p FROM Person p WHERE p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> findAllMixPerson();

    @Query("SELECT p.name FROM Person p WHERE p.id = ?1")
    String getNameById(Long id);

    @Query("SELECT concat(p.name, ' ', p.lastname) FROM Person p WHERE p.id = ?1")
    String getFullNameById(Long id);

}
