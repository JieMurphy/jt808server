package org.yzh.web.database.goods;

import javax.persistence.*;

@Entity
@Table
public class Goods {
    public static int 水果 = 1;
    public static int 蔬菜 = 2;
    public static int 谷物 = 3;

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String goodName;
    private int kind;
    private float price;

    public Goods()
    {
        super();
    }

    public Goods(String goodName,int kind,float price)
    {
        super();
        this.goodName = goodName;
        this.kind = kind;
        this.price = price;
    }

    public void setGoodName(String goodName)
    {
        this.goodName = goodName;
    }

    public String getGoodName()
    {
        return goodName;
    }

    public void setKind(int kind)
    {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }
}
