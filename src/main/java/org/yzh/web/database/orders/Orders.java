package org.yzh.web.database.orders;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public class Orders {
    public static int 待分配 = 0;
    public static int 待确认 = 1;
    public static int 待签收 = 2;
    public static int 已签收 = 3;

    @Id
    @GeneratedValue
    private long id;

    @Column
    private Timestamp startTime;
    private Timestamp changeTime;
    private String cumsNumber;
    private String goodName;
    private int count;
    private String startAddress;
    private String endAddress;
    private int status;
    private String driNumber;

    public Orders()
    {
        super();
        this.startTime = new Timestamp(System.currentTimeMillis());
    }

    public Orders(String cumsNumber,String goodName,int count,String address)
    {
        super();
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.cumsNumber = cumsNumber;
        this.goodName = goodName;
        this.count = count;
        this.endAddress = address;
        this.status = 待分配;
        setChangeTime();
    }

    private void setChangeTime()
    {
        this.changeTime = new Timestamp(System.currentTimeMillis());
    }

    public void setStatus(int status) {
        this.status = status;
        setChangeTime();
    }

    public int getStatus()
    {
        return status;
    }

    public String getDriNumber() {
        return driNumber;
    }

    public void setDriNumber(String driNumber) {
        this.driNumber = driNumber;
    }

    public String getCumsNumber() {
        return cumsNumber;
    }

    public long getId() {
        return id;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }
}
