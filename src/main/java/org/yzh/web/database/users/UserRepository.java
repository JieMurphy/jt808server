package org.yzh.web.database.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username,String password);
    public User findByNumber(Long number);
}
