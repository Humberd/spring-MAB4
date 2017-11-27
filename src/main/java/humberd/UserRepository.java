package humberd;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
    void remove(String username);
}
