package com.andres.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.andres.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.andres.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();

    @Query("SELECT DISTINCT(p.name) FROM Person p")
    List<String> findAllNamesDistinct();

    @Query("SELECT new com.andres.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllClassPerson();

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT DISTINCT(p.programmingLanguage) FROM Person p")
    List<String> findAllProgrammingLanguagesDistict();

    @Query("SELECT COUNT(DISTINCT(p.programmingLanguage)) FROM Person p")
    Integer findAllProgrammingLanguagesDistictCount();

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

    //@Query("SELECT concat(p.name, ' ', p.lastname) FROM Person p")
    @Query("SELECT p.name || ' ' || p.lastname FROM Person p")
    List<String> findAllFullNameConcat();

    @Query("SELECT upper(p.name || ' ' || p.lastname) FROM Person p")
    List<String> findAllFullNameConcatUpper();

    @Query("SELECT lower(p.name || ' ' || p.lastname) FROM Person p")
    List<String> findAllFullNameConcatLower();

    List<Person> findByIdBetween(long id1, long id2);

    @Query("SELECT p FROM Person p WHERE p.id between ?1 and ?2 order by p.name asc")
    List<Person> findAllBetween(long id1, long id2);

    List<Person> findByNameBetween(String c1, String c2);

    @Query("SELECT p FROM Person p WHERE p.name between ?1 and ?2 order by p.name desc")
    List<Person> findAllBetweenName(String c1, String c2);


}
