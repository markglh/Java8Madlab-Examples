package com.examples.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private int age;
    private int iq;
    private Sex gender;


    public Person(String name, int age, int iq, Sex gender) {
        this.name = name;
        this.age = age;
        this.iq = iq;
        this.gender = gender;
    }

    public String getName() {
        return name;
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
                "name='" + name + '\'' +
                ", age=" + age +
                ", iq=" + iq +
                ", gender=" + gender +
                '}';
    }

    public Person dolly() {
        return new Person(this.name, this.age, this.iq, this.gender);
    }


    public static List<Person> getLoadsOfPeople() {
        List<Person> Persons = new ArrayList<>();
        Persons.add(new Person("Dave", 28, 190, Sex.MALE));
        Persons.add(new Person("Fred", 28, 78, Sex.MALE));
        Persons.add(new Person("Damo", 52, 113, Sex.MALE));
        Persons.add(new Person("Helen", 19, 97, Sex.FEMALE));
        Persons.add(new Person("Laura", 25, 55, Sex.FEMALE));
        Persons.add(new Person("Ben", 25, 17, Sex.MALE));
        Persons.add(new Person("Jayne", 25, 109, Sex.FEMALE));
        Persons.add(new Person("Jess", 60, 120, Sex.FEMALE));
        Persons.add(new Person("Matt", 60, 115, Sex.FEMALE));
        return Persons;
    }
}
