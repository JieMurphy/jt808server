package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;
//身份鉴定
@Type(MessageId.终端鉴权)
public class Authentication extends AbstractBody {

    /** 终端重连后上报鉴权码 */
    private String token;

    @Property(index = 0, type = DataType.STRING, desc = "鉴权码")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}