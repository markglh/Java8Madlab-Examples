package com.examples.part1;

import com.examples.domain.Person;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleLambdas {

    private static void printAllPeopleOlderThan50(List<Person> people) {
        for (Person person : people) {
            if (person.getAge() >= 50) {
                System.out.println(person);
            }
        }
    }

    private static void printAllPeopleMatchingPredicate(List<Person> people, Predicate<Person> predicate) {
        for (Person person : people) {
            if (predicate.test(person)) {
                System.out.println(person);
            }
        }
    }

    private static void printAndImproveAllPeopleMatchingPredicate(List<Person> people,
                                                                  Predicate<Person> predicate,
                                                                  Function<Person, Person> improver) {
        for (Person person : people) {
            if (predicate.test(person)) {
                System.out.println(improver.apply(person));
            }
        }
    }

    private static Person improvePerson(Person person) {
        return new Person(person.getAge() / 2, person.getIq() * 2);
    }

    private static boolean isPersonOlderThan50(Person person) {
        return person.getAge() >= 50;
    }

    public static void main(String[] args) {
        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        printAllPeopleOlderThan50(loadsOfPeople);
        printAllPeopleMatchingPredicate(loadsOfPeople, new PersonOlderThan50Predicate());
        printAllPeopleMatchingPredicate(loadsOfPeople, x -> x.getAge() > 50);
        printAndImproveAllPeopleMatchingPredicate(loadsOfPeople,
                x -> x.getAge() > 50,
                x -> new Person(x.getAge() / 2, x.getIq() * 2));

        //method references, can be clearer - also easier to debug
        printAndImproveAllPeopleMatchingPredicate(loadsOfPeople,
                SimpleLambdas::isPersonOlderThan50,
                SimpleLambdas::improvePerson);


        //we could take this even further, perhaps replacing the println with a Consumer...


        System.out.println("\n\nProve that Lambdas are nothing more than Interfaces with one method...");
        Consumer<String> inferredConsumer = x -> System.out.println(x);
        //???
        //consumer is an interface with just one (non-default) method...

        //See..... nothing special (just a method that takes a String!)
        RandomInterface meh = x -> System.out.println(x);
        meh.methodThatDoesAwesomeStuff("Woop!");


        // Lambda Runnable
        Runnable r = () -> System.out.println("Runnable!");
        r.run();

        //replaces this mess:
        // Anonymous Runnable
        Runnable r1 = new Runnable() {

            @Override
            public void run() {
                System.out.println("Hello world one!");
            }
        };
    }
}

class PersonOlderThan50Predicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        //if
        return person.getAge() >= 50;
    }
}

/**
 * It's laborious defining our own simple FunctionalInterfaces, so check out {@link java.util.function}.
 */
interface RandomInterface {
    public void methodThatDoesAwesomeStuff(String s);
}
