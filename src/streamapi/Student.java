package streamapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Student {
    String name;
    Double score;


    public Student(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", score=" + score + '}';
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("James", 33.0));
        students.add(new Student("Andy", 17.9));
        students.add(new Student("Matt", 23.0));
        students.add(new Student("Anthony", 3.0));
        students.add(new Student("Frank", 37.0));

        students.add(new Student("Chris", 0.0));
        students.add(new Student("Christy", 0.0));
        students.add(new Student("Abby", 0.0));
        students.add(new Student("Bob", 0.0));
        students.add(new Student("Fly", -15.0));

        students.stream().sorted((p1, p2) -> p1.getScore().compareTo(p2.getScore())).forEach(System.out::println);
        System.out.print(System.lineSeparator());
        students.stream().sorted(Comparator.comparing(Student::getScore)).forEach(System.out::println);
        System.out.print(System.lineSeparator());
        students.stream().sorted((p1, p2) -> p1.getScore().equals(p2.getScore()) ? p2.getName().compareTo(p1.getName()) : p1.getScore().compareTo(p2.getScore())).forEach(System.out::println);
        // Non-static method cannot be referenced from a static context
        // weathers.stream().sorted(Weather::getTemperature).forEach(System.out::println);

    }


}
