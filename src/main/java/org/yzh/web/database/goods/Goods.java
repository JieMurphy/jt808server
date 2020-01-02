package org.yzh.web.database.goods;

import javax.persistence.*;

@Entity
@Table
public class Goods {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String goodName;
    private String kind;
    private float price;

    public Goods()
    {
        super();
    }

    public Goods(String goodName,String kind,float price)
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

    public void setKind(String kind)
    {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
