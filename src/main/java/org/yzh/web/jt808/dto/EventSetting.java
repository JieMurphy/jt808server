package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.common.MessageId;

import java.util.ArrayList;
import java.util.List;

@Type(MessageId.事件设置)
public class EventSetting extends AbstractBody {

    //清空
    public static final int Clean = 0;
    //更新（先清空，后追加）
    public static final int Update = 1;
    //追加
    public static final int Append = 2;
    //修改
    public static final int Modify = 3;
    //删除特定几项事件，之后事件项中无需带事件内容
    public static final int Delete = 4;

    private Integer type;

    private Integer total;

    private List<Event> list;

    @Property(index = 0, type = DataType.BYTE, desc = "设置类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "设置总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 2, type = DataType.LIST, desc = "事件项列表")
    public List<Event> getList() {
        return list;
    }

    public void setList(List<Event> list) {
        this.list = list;
    }

    public void addEvent(int id, String content) {
        if (this.list == null)
            this.list = new ArrayList();
        this.list.add(new Event(id, content));
        this.total = list.size();
    }

    public static class Event {
        private Integer id;
        private Integer length;
        private String content;

        public Event() {
        }

        public Event(Integer id, String content) {
            this.id = id;
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }

        @Property(index = 0, type = DataType.BYTE, desc = "事件ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Property(index = 1, type = DataType.BYTE, desc = "长度")
        public Integer getLength() {
            if (length == null)
                this.length = content.getBytes(Charsets.GBK).length;
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Property(index = 2, type = DataType.STRING, lengthName = "length", desc = "内容")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }
    }
}