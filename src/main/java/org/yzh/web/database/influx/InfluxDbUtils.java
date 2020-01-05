package org.yzh.web.database.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;

public class InfluxDbUtils {
    private String username;
    private String password;
    private String url;
    private String database;
    private String retentionPolicy;

    private InfluxDB influxDB;

    public InfluxDbUtils(String username,String password,String url,String database,String retentionPolicy)
    {
        this.username = username;
        this.password = password;
        this.database = database;
        this.url = url;
        this.retentionPolicy = retentionPolicy;
        this.influxDB = influxDbBuild();
    }

    private InfluxDB influxDbBuild() {
        if (influxDB == null)
        {
            influxDB = InfluxDBFactory.connect(url,username,password);
            influxDB.createDatabase(database);
        }
        return influxDB;
    }

    public QueryResult query(String command)
    {
        return influxDB.query(new Query(command,database));
    }

    public void insert(String measurement, Map<String,String> tags,Map<String,Object> fields)
    {
        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(database,"",builder.build());
    }
}
