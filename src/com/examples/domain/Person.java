package com.examples.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    private int age;
    private int iq;
    private Sex gender;


    public Person(int age, int iq) {
        this.age = age;
        this.iq = iq;
        this.gender = gender.MALE;
    }

    public int getAge() {
        return age;
    }

    public int getIq() {
        return iq;
    }

    public Sex getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", iq=" + iq +
                ", gender=" + gender +
                '}';
    }

    public static List<Person> getLoadsOfPeople() {
        List<Person> Persons = new ArrayList<>();
        Persons.add(new Person(28, 190));
        Persons.add(new Person(28, 78));
        Persons.add(new Person(52, 113));
        Persons.add(new Person(19, 97));
        Persons.add(new Person(25, 55));
        Persons.add(new Person(25, 17));
        Persons.add(new Person(25, 109));
        Persons.add(new Person(60, 120));
        return Persons;
    }
}
