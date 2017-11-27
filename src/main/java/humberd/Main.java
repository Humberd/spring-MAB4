package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Main {
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        User user = new User("jan", "admin123", "JanNowak", "jan@nowak.com");
        userService.addUser(user);
        verifyUserPassword("jan", "admin123");
        verifyUserPassword("jan", "defenitelyNotAValidPassword");
    }

    private void verifyUserPassword(String username, String password) {
        if (userService.verifyUser(username, password)) {
            System.out.println("User " + username + " is verified with password " + password);
        } else {
            System.out.println("User " + username + " is not verified with password " + password);
        }
    }
}
