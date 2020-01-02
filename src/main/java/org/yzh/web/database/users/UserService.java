package org.yzh.web.database.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean save(User user)
    {
        if(findByNumber(user.getNumber()) != null)
        {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User findByNumber(Long number)
    {
        return userRepository.findByNumber(number);
    }

    public User findByNameAndPasswd(String username, String password)
    {
        return userRepository.findByUsernameAndPassword(username,password);
    }

    public boolean judge(String username,String password)
    {
        if(userRepository.findByUsernameAndPassword(username,password) == null)
        {
            return false;
        }
        return true;
    }

    public boolean judge(Long number)
    {
        if(userRepository.findByNumber(number) == null)
        {
            return false;
        }
        return true;
    }

    public List<User> list()
    {
        return userRepository.findAll();
    }
}
