package com.phonebook.main;

import com.phonebook.spring.ApplicationConfig;
import com.phonebook.spring.PhoneBook;
import com.phonebook.spring.PhoneBookFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * PhoneBook entry point
 */
public class PhoneBookMain {

    public static void main(String[] args) {
        ApplicationContext context = newApplicationContext(args);

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(System.getProperty("line.separator"));

        PhoneBook phoneBook = context.getBean("phoneBook", PhoneBook.class);
        PhoneBookFormatter renderer = (PhoneBookFormatter) context.getBean("phoneBookFormatter");


        renderer.info("type 'exit' to quit.");

        while (sc.hasNext()) {
            String line = sc.nextLine();

            if (line.equals("exit")) {
                renderer.info("Have a good day...");
                break;
            }


            try {
                //TODO
                if (line.equals("SHOW")) {
                    renderer.show(phoneBook.findAll());
                    continue;
                }
                if (line.contains("ADD")) {
                    String[] strLine = line.split(" ");
                    phoneBook.addPhone(strLine[1], strLine[2]);
                    continue;
                }
                if (line.contains("REMOVE_PHONE")) {
                    String[] strLine = line.split(" ");
                    phoneBook.removePhone(strLine[1]);
                    continue;
                }
                throw new UnsupportedOperationException();
            } catch (Exception e) {
                renderer.error(e);
            }
        }
    }

    static ApplicationContext newApplicationContext(String... args) {
        return args.length > 0 && args[0].equals("classPath")
                ? new ClassPathXmlApplicationContext("application-config.xml")
                : new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

}
