package org.yzh.web.database.users;

import javax.persistence.*;

@Entity
@Table
public class User {
    public static int 管理员 = 0;
    public static int 消费者 = 1;
    public static int 司机 = 2;

    @Id
    @GeneratedValue
    private long id;

    @Column
    private long number;
    private String username;
    private String password;
    private int power;
    private long terNumber;

    public User(){
        super();
    }

    public  User(String username,String password)
    {
        super();

        this.number = 15651089916L;
        this.username = username;
        this.password = password;
        this.power = 管理员;
    }

    public  User(Long number,String username,String password)
    {
        super();

        this.number = number;
        this.username = username;
        this.password = password;
        this.power = 消费者;
    }

    public  User(Long number,String username,String password,long terNumber)
    {
        super();

        this.number = number;
        this.username = username;
        this.password = password;
        this.power = 司机;
        this.terNumber = terNumber;
    }

    public long getNumber()
    {
        return number;
    }

    public void setNumber(long number)
    {
        this.number = number;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getPower()
    {
        return power;
    }

    public void setPower(int power)
    {
        this.power = power;
    }

    public void setTerNumber(long terNumber)
    {
        this.terNumber = terNumber;
    }

    public long getTerNumber()
    {
        return terNumber;
    }
}
