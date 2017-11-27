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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        User user = new User("jan", "admin123", "JanNowak", "jan@nowak.com");
        userService.addUser(user);
        userService.verifyUser("jan", "admin123");
    }


}
