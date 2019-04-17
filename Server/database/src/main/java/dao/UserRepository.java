package dao;

import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsernameIgnoreCase(String username);
    public List<User> findAllByOrderByPointsAsc();
}
