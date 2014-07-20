package com.examples.lambdas;

import com.examples.domain.Person;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by markharrison on 19/07/2014.
 */
public class PersonPredicates {

    public static class PersonOlderThan50Predicate implements Predicate<Person> {

        @Override
        public boolean test(Person person) {
            return person.getAge() >= 50;
        }
    }

    public static void printAllPeopleOlderThan50(List<Person> people) {
        for (Person person : people) {
            if (person.getAge() >= 50) {
                System.out.println(person);
            }
        }
    }

    public static void printAllPeopleMatchingPredicate(List<Person> people, Predicate<Person> predicate) {
        for (Person person : people) {
            if (predicate.test(person)) {
                System.out.println(person);
            }
        }
    }

    public static void printAndImproveAllPeopleMatchingPredicate(List<Person> people,
                                                                 Predicate<Person> predicate,
                                                                 Function<Person, Person> improver) {
        for (Person person : people) {
            if (predicate.test(person)) {
                System.out.println(improver.apply(person));
            }
        }
    }

    public static Person improvePerson(Person person) {
        return new Person("Mutated" + person.getName(), person.getAge() / 2, person.getIq() * 2, person.getGender());
    }

    public static boolean isPersonOlderThan50(Person person) {
        return person.getAge() >= 50;
    }

    //instance method
    public boolean isPersonYoungerThan50(Person person) {
        return person.getAge() <= 50;
    }
}
