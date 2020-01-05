package org.yzh.jt808.codec;

import org.influxdb.dto.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.yzh.web.database.influx.CodeInfo;
import org.yzh.web.database.influx.InfluxDbUtils;

import java.sql.Timestamp;
import java.util.List;

public class InfluxDBTest {
    private InfluxDbUtils influxDbUtils;

    @Before
    public void setUp()
    {
        influxDbUtils = new InfluxDbUtils();
    }

    @Test
    public void testInsert()
    {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setAltitude(54);
        codeInfo.setLatitude(707);
        codeInfo.setLongitude(488);
        codeInfo.setStatus(1);
        codeInfo.setTagCode(CodeInfo.上线);
        codeInfo.setTagName("078569");

        influxDbUtils.insert(codeInfo);
    }

    @Test
    public void testShow()
    {
        QueryResult queryResult = influxDbUtils.queryAll();
        List<CodeInfo> codeInfos = influxDbUtils.turn(queryResult);
        for(CodeInfo info:codeInfos)
        {
            System.out.println(info.getTime());
            System.out.println(info.getTagName());
        }
    }

    @Test
    public void testSelect()
    {
        QueryResult queryResult = influxDbUtils.queryByTernumberAndTime("goo",new Timestamp(1578223091));
        System.out.println(queryResult.toString());
    }
}
