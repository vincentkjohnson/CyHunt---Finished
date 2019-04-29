package cyhunter.database.dao;

import cyhunter.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsernameIgnoreCase(String username);

    public User findFirstById(int id);
}
