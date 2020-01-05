package org.yzh.jt808.codec;

public class InfluxDBTest {
    /*private InfluxDbUtils influxDbUtils;
    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://127.0.0.1:8086";//连接地址
    private String database = "test_db";//数据库
    private String measurement = "sys_code";

    @Before
    public void setUp()
    {
        influxDbUtils = new InfluxDbUtils(username,password,openurl,database,"");
    }

    @Test
    public void testInsert()
    {
        Map<String,String> tags = new HashMap<String, String>();
        Map<String,Object> fields = new HashMap<String, Object>();

        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setAltitude(445);
        codeInfo.setLatitude(777);
        codeInfo.setLongitude(888);
        codeInfo.setStatus(1);
        codeInfo.setTagCode("0");
        codeInfo.setTagName("goo");

        tags.put("TAG_CODE",codeInfo.getTagCode());
        tags.put("TAG_NAME",codeInfo.getTagName());

        fields.put("Altitude",codeInfo.getAltitude());
        fields.put("Longitude",codeInfo.getLongitude());
        fields.put("Latitude",codeInfo.getLatitude());
        fields.put("Status",codeInfo.getStatus());

        influxDbUtils.insert(measurement,tags,fields);
    }
    */
}
