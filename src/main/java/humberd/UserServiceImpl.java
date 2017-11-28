package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Autowired
    HashService hashService;
    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) throws IllegalUsernameException, IncorrectEmailException {
        if (user.getUsername().equals("admin")) {
            throw new IllegalUsernameException();
        }

        if (!isEmail(user.getEmail())) {
            throw new IncorrectEmailException();
        }

        try {
            user.setPassword(hashService.getHash(user.getPassword()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void removeUser(String username) throws IllegalUsernameException {
        userRepository.remove(username);
    }

    @Override
    public boolean verifyUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        try {
            return hashService.getHash(password).equals(user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}
