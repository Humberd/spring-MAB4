package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@Component
public class Main {
    @Autowired
    UserService userService;

    private Scanner stdin = new Scanner(System.in);

    public static final String ADD_USER = "1";
    public static final String VERIFY_PASSWORD = "2";

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        System.out.println("____HELLO IN SPRING APP____");
        while (true) {
            System.out.println();
            System.out.println("What do you want to do?");
            System.out.println("[" + ADD_USER + "] Add new User");
            System.out.println("[" + VERIFY_PASSWORD + "] Verify Password");
            switch (getFromStdin("")) {
                case ADD_USER:
                    addUserFromStdin();
                    break;
                case VERIFY_PASSWORD:
                    verifyPasswordFromStdin();
                    break;
                default:
                    System.err.println("Invalid option");
                    continue;
            }
        }
    }


    private void addUserFromStdin() {
        System.out.println();
        System.out.println("__Add new User__");
        String username = getFromStdin("Username");
        String password = getFromStdin("Password");
        String name = getFromStdin("Name");
        String email = getFromStdin("Email");
        User user = new User(username, password, name, email);

        try {
            userService.addUser(user);
            System.out.println("--User was added--");
        } catch (UserService.IllegalUsernameException e) {
            System.err.println("--Invalid username--");
        } catch (UserService.IncorrectEmailException e) {
            System.err.println("--Incorrect email--");
        }
    }

    private void verifyPasswordFromStdin() {
        System.out.println();
        System.out.println("__Verify Password__");
        String username = getFromStdin("Username");
        String password = getFromStdin("Password");

        if (userService.verifyUser(username, password)) {
            System.out.println("--Password Verified!--");
        } else {
            System.err.println("--Password not verified--");
        }
    }

    private String getFromStdin(String label) {
        System.out.print(label + ": ");
        return stdin.nextLine().trim();
    }
}
