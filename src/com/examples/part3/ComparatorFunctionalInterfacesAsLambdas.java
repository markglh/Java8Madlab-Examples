package com.examples.part3;

import com.examples.domain.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.examples.domain.Person.getLoadsOfPeople;

/**
 * Good old fashioned comparator.
 */
class SortByPersonAge implements Comparator<Person> {

    public int compare(Person p1, Person p2) {
        return p1.getAge() - p2.getAge();
    }
}


class Main {

    public static void main(String[] args) {
        //Old method
        List<Person> loadsOfPeople1 = getLoadsOfPeople();
        Collections.sort(loadsOfPeople1, new SortByPersonAge());
        System.out.println("Old method: " + loadsOfPeople1);


        //Java8
        List<Person> loadsOfPeople2 = getLoadsOfPeople();
        Comparator<Person> newComparator = (p1, p2) -> p1.getAge() - p2.getAge(); //could instead use Comparator.comparing (see later)
        Collections.sort(loadsOfPeople2, newComparator);
        System.out.println("Java8 single comparator   : " + loadsOfPeople2);
        //How, what?
        /**
         how does this work?
         1) converted to a function
         2) invoke the generated function.
         3) The return type of the function is inferred by the compiler.
         */

        //As I said, they've added a load of new default method to the comparator interface....
        Collections.sort(loadsOfPeople2, newComparator.thenComparing(Comparator.comparing(e -> e.getIq())));
        System.out.println("Java8 composite comparator: " + loadsOfPeople2);


        //infer the comparator....
        List<Person> loadsOfPeople3 = getLoadsOfPeople();
        Collections.sort(loadsOfPeople3, (p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println("Java8 inferred comparator: " + loadsOfPeople3);

        //Yet more Java 8 niceties....
        List<Person> loadsOfPeople4 = getLoadsOfPeople();
        loadsOfPeople4.sort((p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println("Java8 list niceties: " + loadsOfPeople4);


    }
}
