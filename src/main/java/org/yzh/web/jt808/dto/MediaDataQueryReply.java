package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

import java.util.List;

@Type(MessageId.存储多媒体数据检索应答)
public class MediaDataQueryReply extends AbstractBody {

    private Integer serialNumber;
    private Integer total;
    private List<Item> list;

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Property(index = 2, type = DataType.WORD, desc = "多媒体数据总项数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 4, type = DataType.LIST, desc = "检索项")
    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public static class Item {

        private Integer id;
        private Integer type;
        private Integer channelId;
        private Integer event;
        private PositionReport position;

        @Property(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Property(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频")
        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        @Property(index = 5, type = DataType.BYTE, desc = "通道ID")
        public Integer getChannelId() {
            return channelId;
        }

        public void setChannelId(Integer channelId) {
            this.channelId = channelId;
        }

        @Property(index = 6, type = DataType.BYTE, desc = "事件项编码")
        public Integer getEvent() {
            return event;
        }

        public void setEvent(Integer event) {
            this.event = event;
        }

        @Property(index = 7, type = DataType.OBJ, length = 28, desc = "位置信息")
        public PositionReport getPosition() {
            return position;
        }

        public void setPosition(PositionReport position) {
            this.position = position;
        }
    }
}