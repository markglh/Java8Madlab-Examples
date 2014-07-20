package com.examples.lambdas;

import com.examples.domain.Person;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.examples.lambdas.PersonPredicates.*;

public class SimpleLambdas {

    public static void main(String[] args) {
        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        //old inflexible way
        printAllPeopleOlderThan50(loadsOfPeople);

        //pre java 8, using a standard predicate implementation
        printAllPeopleMatchingPredicate(loadsOfPeople, new PersonOlderThan50Predicate());

        //Java 8, using a lambda to instantiate the predicate
        printAllPeopleMatchingPredicate(loadsOfPeople, x -> x.getAge() > 50);

        //composite predicates
        Predicate<Person> ageCheck = x -> x.getAge() > 50;
        printAllPeopleMatchingPredicate(loadsOfPeople, ageCheck.and(x -> x.getIq() > 100));

        printAndImproveAllPeopleMatchingPredicate(loadsOfPeople,
                x -> x.getAge() > 50,
                x -> new Person("Mutated" + x.getName(), x.getAge() / 2, x.getIq() * 2, x.getGender()));

        //method references, can be clearer - also easier to debug
        printAndImproveAllPeopleMatchingPredicate(loadsOfPeople,
                PersonPredicates::isPersonOlderThan50,
                PersonPredicates::improvePerson);


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
        r1.run();

        Consumer<Object> c2 = System.out::println;
    }
}

/**
 * It's laborious defining our own simple FunctionalInterfaces, so check out {@link java.util.function}.
 */
interface RandomInterface {
    public void methodThatDoesAwesomeStuff(String s);
}
