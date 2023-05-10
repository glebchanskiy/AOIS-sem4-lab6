package org.glebchanskiy.app;

import org.glebchanskiy.SimpleHashMap;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

record Student(String name, Integer age) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

public class Main {

    public static void main(String[] args) {
        printStringMap();
        printIntegerMap();
        printOtherTest();
    }

    private static void printOtherTest() {
        System.out.println("================================= Other");
        Map<String, Student> map = new SimpleHashMap<>();

        List<Student> students = List.of(
                new Student("Вася", 20),
                new Student("Петя", 21),
                new Student("Иван", 19),
                new Student("Андрей", 22),
                new Student("Сергей", 18),
                new Student("Ольга", 20)
        );

        for (Student student : students) {
            map.put(student.name(), student);
        }

        System.out.println("Содержимое мапы:");
        for (Map.Entry<String, Student> entry : map.entrySet())
            System.out.println("ключ: " + entry.getKey() + ", значение: " + entry.getValue());

        System.out.println("Кирилл в мапе? - " + map.containsKey("Кирилл"));
        System.out.println("Петя в мапе? - " + map.containsKey("Петя"));

        System.out.println("Список ключей: " + map.keySet());
        System.out.println("Список значений: " + map.values());

        System.out.println();

        System.out.println(map);

        System.out.println();

        Map<String, Student> students2 = Map.of(
                "Мария", new Student("Мария", 20),
                "Елена", new Student("Елена", 21),
                "Екатерина", new Student("Екатерина", 19),
                "Дмитрий", new Student("Дмитрий", 22),
                "Александр", new Student("Александр", 18),
                "Да", new Student("Да", 20),
                "Глеб", new Student("Глеб", 19),
                "Егор", new Student("Егор", 20),
                "Володя", new Student("Володя", 19)
        );

        map.putAll(students2);
        System.out.println("\nПосле добавления: ");
        System.out.println(map);
    }

    private static void printStringMap() {
        System.out.println("================================= String");

        Function<String, Integer> simpleStringHashFunction = key -> {
            int hash = 0;
            for (int i = 0; i < key.length(); i++) {
                hash = 127 * hash + key.charAt(i);
            }
            return hash;
        };

        Map<String, String> map = new SimpleHashMap<>(4, simpleStringHashFunction);

        map.put("apple", "яблоко");
        map.put("banana", "банан");

        System.out.println();

        System.out.println(map);

        System.out.println();

        map.put("cherry", "вишня");
        map.put("dog", "собака");
        map.put("frog", "лягушка");
        map.put("grape", "виноград");
        map.put("iguana", "игуана");
        map.put("iguana", "ящерка");

        System.out.println();

        System.out.println(map);
    }

    private static void printIntegerMap() {
        System.out.println("================================= Integer");

        Function<Integer, Integer> intHashFunction = key -> {
            try {
                // Преобразуем число в байтовый массив
                byte[] bytes = String.valueOf(key).getBytes(StandardCharsets.UTF_8);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(bytes);
                return ByteBuffer.wrap(hashBytes, 0, 4).getInt();
            } catch (NoSuchAlgorithmException ignored) {
            }
            return 0;
        };


        Map<Integer, String> map = new SimpleHashMap<>(intHashFunction);

        map.put(10, "десять");
        map.put(2, "два");
        map.put(5, "пять");
        map.put(11, "одиннадцать");
        map.put(12, "twelve");
        map.put(12, "двенадцать");

        System.out.println();

        System.out.println(map);
    }
}
