import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {

        String[] example = initialData();
        if (example.length < 6) {
            System.out.println("Вы ввели слишком мало данных");
        }
        if (example.length > 6) {
            System.out.println("Вы ввели слишком много данных");
        }

        String FamilyName = example[0];
        String Name = example[1];
        String MiddleName = example[2];
        LocalDate birthDate = LocalDate.of(1900, 1, 1);
        int phone = 0;
        char gender =' ';

        try {
            birthDate = dateRefactoring(example[3]);
        } catch (MyFormatException e) {
            System.out.println("Вы ввели неверный формат даты, нужно dd.mm.yyyy");
        }

        try {
            phone = phoneRefactoring(example[4]);
        } catch (MyFormatException e) {
            System.out.println("Вы ввели неверный формат телефонного номера, допускается только формат целого числа");
        }

        try {
            gender = genderRefactoring(example[5]);
        } catch (MyFormatException e) {
            System.out.println("Вы ввели неверный формат пола, нужно буквой m или f");
        }

        try {
            fileWriter(FamilyName, Name, MiddleName, birthDate, phone, gender);
        } catch (MyFormatException e) {
            System.out.println("Произошел сбой в записи в файл");
        }

    }

    public static String[] initialData() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите свою фамилию, имя, отчество, дату рождения, номер телефона и пол через пробел");
            String input = reader.readLine();
            String[] example = input.split(" ");
            return example;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int phoneRefactoring(String phoneString) throws MyFormatException {
        try {
            return Integer.parseInt(phoneString);
        } catch (NumberFormatException e) {
            throw new MyFormatException("Вы ввели неверный формат телефонного номера, " +
                    "допускается только формат целого числа");
        }

    }

    public static char genderRefactoring(String genderString) throws MyFormatException {
        if (genderString.length() == 1 ) {
            char gender = genderString.charAt(0);
            if (gender == 'm' || gender ==  'f') {
                return gender;
            } else {
                throw new MyFormatException("Пол это или m, или f");
            }
        } else {
            throw new MyFormatException("Вы ввели неверный формат пола, нужно буквой m или f");
        }
    }

    public static LocalDate dateRefactoring(String dateString) throws MyFormatException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthdayDate = LocalDate.parse(dateString, formatter);
            return birthdayDate;
        } catch (DateTimeParseException e) {
            throw new MyFormatException("Вы ввели неверный формат даты, нужно dd.mm.yyyy");
        }
    }

    public static void fileWriter(String FamilyName, String Name, String MiddleName, LocalDate birthDate,
                                  int phone, char gender) throws Exception{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FamilyName + ".txt", true))) {
            writer.write("<" + FamilyName + "><" + Name + "><" + MiddleName + "><" + birthDate + "><" +
                    phone + "><" + gender + ">");
            writer.newLine();
        } catch (Exception e) {
            throw new MyFormatException("Произошел сбой в записи в файл");
        }
    }


}