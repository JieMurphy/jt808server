package org.yzh.web.database.users;

import javax.persistence.*;

@Entity
@Table
public class User {
    public static int 管理员 = 0;
    public static int 消费者 = 1;
    public static int 司机 = 2;

    public static int 上线 = 3;
    public static int 在线 = 4;
    public static int 下线 = 5;

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String number;
    private String username;
    private String password;
    private int power;
    private String terNumber;

    public User(){
        super();
    }

    public  User(String username,String password)
    {
        super();

        this.number = "15651089916";
        this.username = username;
        this.password = password;
        this.power = 管理员;
    }

    public  User(String number,String username,String password)
    {
        super();

        this.number = number;
        this.username = username;
        this.password = password;
        this.power = 消费者;
    }

    public  User(String number,String username,String password,String terNumber)
    {
        super();

        this.number = number;
        this.username = username;
        this.password = password;
        this.power = 司机;
        this.terNumber = terNumber;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
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

    public void setTerNumber(String terNumber)
    {
        this.terNumber = terNumber;
    }

    public String getTerNumber()
    {
        return terNumber;
    }

    public long getId() {
        return id;
    }
}
