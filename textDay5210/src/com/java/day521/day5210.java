package com.java.day521;

public class day5210 {

	public static void main(String[] args) {

		System.out.println("Hello world!");

		School schools = new School();

		schools.name = "清华大学";
		schools.timeAge = 200;
		schools.mainJi = 180000;

		System.out.println(schools.name);
		System.out.println(schools.timeAge);
		System.out.println(schools.mainJi);

		Studen studens = new Studen();

		schools.studen = studens;

		studens.age = 18;
		studens.name = "zhangsan";
		studens.school = schools;
		System.out.println(schools.studen.age);
		System.out.println(schools.studen.name);
		System.out.println(studens.school.timeAge);

	}

}
