package org.yzh.jt808.codec;

import org.influxdb.dto.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.yzh.web.database.influx.CodeInfo;
import org.yzh.web.database.influx.InfluxDbUtils;

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
        CodeInfo codeInfo = new CodeInfo(CodeInfo.在线,"015850612081");
        codeInfo.setAltitude(54);
        codeInfo.setLatitude(31929138);
        codeInfo.setLongitude(118887769);
        codeInfo.setStatus(55524);
        codeInfo.setAlltime(31565l);

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
        long l = influxDbUtils.getAllTimeIn20("015850612081");
        System.out.println(l);
        QueryResult queryResult = influxDbUtils.queryByTernumberAndStatsLimitOne("015850612081",CodeInfo.在线);
        List<CodeInfo> codeInfos = influxDbUtils.turn(queryResult);
        System.out.println(codeInfos.get(codeInfos.size() - 1).getLatitude());
    }
}
