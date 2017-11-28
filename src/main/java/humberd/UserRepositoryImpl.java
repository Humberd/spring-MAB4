package humberd;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> repo = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return repo.get(username);
    }

    @Override
    public void save(User user) {
        repo.put(user.getUsername(), user);
    }

    @Override
    public void remove(String username) {
        repo.remove(username);
    }
}
