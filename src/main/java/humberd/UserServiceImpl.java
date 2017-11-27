package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired HashService hashService;
    @Autowired UserRepository userRepository;

    @Override
    public void addUser(User user) throws IllegalUsernameException, IncorrectEmailException {
        if (user.getUsername().equals("admin")) {
            throw new IllegalUsernameException();
        }

        if (user.getEmail().equals("admin@admin.com")) {
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
}
