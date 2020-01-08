package org.yzh.web.database.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfluxDbUtils {
    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://127.0.0.1:8086";//连接地址
    private String database = "test_db";//数据库
    private String measurement = "sys_code";

    private InfluxDB influxDB;

    public InfluxDbUtils()
    {
        this.influxDB = influxDbBuild();
    }

    private InfluxDB influxDbBuild() {
        if (influxDB == null)
        {
            influxDB = InfluxDBFactory.connect(openurl,username,password);
            influxDB.createDatabase(database);
        }
        return influxDB;
    }

    public QueryResult query(String command)
    {
        return influxDB.query(new Query(command,database));
    }

    public void insert(CodeInfo codeInfo)
    {
        Map<String,String> tags = new HashMap<>();
        Map<String,Object> fields = new HashMap<>();

        tags.put("TAG_CODE",codeInfo.getTagCode());
        tags.put("TAG_NAME",codeInfo.getTagName());

        fields.put("Altitude",codeInfo.getAltitude());
        fields.put("Longitude",codeInfo.getLongitude());
        fields.put("Latitude",codeInfo.getLatitude());
        fields.put("Status",codeInfo.getStatus());
        fields.put("AllTime",codeInfo.getAlltime());

        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(database,"",builder.build());
    }



    public Long getAllTime(String terNumber)
    {
        QueryResult queryResult = queryByTernumberAndStatsLimitOne(terNumber,CodeInfo.在线);
        if(queryResult == null)
        {
            return null;
        }
        List<CodeInfo> codeInfos = turn(queryResult);
        if(codeInfos.isEmpty())
        {
            return null;
        }
        CodeInfo codeInfo = codeInfos.get(codeInfos.size() - 1);
        if(codeInfo == null)
        {
            return 0l;
        }
        return codeInfo.getAlltime();
    }

    public Long getAllTimeIn20(String terNumber)
    {
        String time = turnTimestampToTime(System.currentTimeMillis() - 20 * 60 * 1000);
        QueryResult queryResult = queryByTernumberAndStatsLimitOneIntime(terNumber,CodeInfo.在线,time);
        if(queryResult == null)
        {
            return 0l;
        }
        List<CodeInfo> codeInfos = turn(queryResult);
        if(codeInfos.isEmpty())
        {
            return 0l;
        }
        CodeInfo codeInfo = codeInfos.get(codeInfos.size() - 1);
        if(codeInfo == null)
        {
            return 0l;
        }

        return codeInfo.getAlltime();
    }

    public QueryResult queryAll()
    {
        String command = "select * from " + measurement;
        return query(command);
    }

    public QueryResult queryByTernumberAndStats(String ternumber,String stat)
    {
        String command = "select * from " + measurement + " where TAG_CODE = '" + stat + "' and TAG_NAME = '" + ternumber + "'";
        return query(command);
    }

    public QueryResult queryByTernumberAndStatsLimitOne(String ternumber,String stat)
    {
        String command = "select * from " + measurement + " where TAG_CODE = '" + stat + "' and TAG_NAME = '" + ternumber + "' order by time desc limit 1";
        return query(command);
    }

    public QueryResult queryByTernumberAndStatsLimitOneIntime(String ternumber,String stat,String time)
    {
        String command = "select * from " + measurement + " where TAG_CODE = '" + stat + "' and time >= '" + time + "' and TAG_NAME = '" + ternumber + "' order by time desc limit 1";
        return query(command);
    }

    public QueryResult queryByTernumberAndTime(String ternumber,String time)
    {
        String command = "select * from " + measurement + " where time >= '" + time + "' and TAG_NAME = '" + ternumber + "'";
        return query(command);
    }

    public QueryResult queryByTernumberAndTime(String ternumber, Timestamp timestamp)
    {
        String newTime = turnTimestampToTime(timestamp.getTime());
        return queryByTernumberAndTime(ternumber,newTime);
    }

    public List<CodeInfo> turn(QueryResult queryResult)
    {
        if(queryResult.getResults() == null){
            return null;
        }
        List<CodeInfo> lists = new ArrayList<CodeInfo>();
        for (QueryResult.Result result : queryResult.getResults()) {

            List<QueryResult.Series> series= result.getSeries();
            if(series == null)
            {
                return lists;
            }
            for (QueryResult.Series serie : series) {
//				Map<String, String> tags = serie.getTags();

                    List<List<Object>>  values = serie.getValues();
                    List<String> columns = serie.getColumns();
                    lists.addAll(getQueryData(columns, values));
            }
        }

        return lists;
    }

    private String turnTimestampToTime(Long timestamp)
    {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = timestamp - 8 * 60 * 60 * 1000;
        return sd.format(time);
    }

    private List<CodeInfo> getQueryData(List<String> columns, List<List<Object>>  values){
        List<CodeInfo> lists = new ArrayList<CodeInfo>();

        for (List<Object> list : values) {
            CodeInfo info = new CodeInfo();
            BeanWrapperImpl bean = new BeanWrapperImpl(info);
            for(int i=0; i< list.size(); i++){

                String propertyName = setColumns(columns.get(i));//字段名
                Object value = list.get(i);//相应字段值
                bean.setPropertyValue(propertyName, value);
            }

            lists.add(info);
        }

        return lists;
    }

    private String setColumns(String column){
        String[] cols = column.split("_");
        StringBuffer sb = new StringBuffer();
        for(int i=0; i< cols.length; i++){
            String col = cols[i].toLowerCase();
            if(i != 0){
                String start = col.substring(0, 1).toUpperCase();
                String end = col.substring(1).toLowerCase();
                col = start + end;
            }
            sb.append(col);
        }
        return sb.toString();
    }
}
