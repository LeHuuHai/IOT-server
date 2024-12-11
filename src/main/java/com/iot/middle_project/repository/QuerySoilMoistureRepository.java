package com.iot.middle_project.repository;

import com.iot.middle_project.model.SoilMoistureData;
import com.mongodb.client.MongoClient;
import org.bson.Document;

import java.util.*;
import java.util.Arrays;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

@Repository
public class QuerySoilMoistureRepository {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConverter converter;

    private Calendar getTodayCalendar(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        calendar.setTime(new Date());
        System.out.println(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public ArrayList<SoilMoistureData> queryDuration(String deviceId, int num_day_pre, int num_day_next){

        Calendar todayCalendar = getTodayCalendar();
        todayCalendar.add(Calendar.DAY_OF_MONTH, num_day_pre);
        Date today = todayCalendar.getTime();
        System.out.println(today);

        Calendar tomorrowCalendar = getTodayCalendar();
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, num_day_next);
        Date tomorrow = tomorrowCalendar.getTime();
        System.out.println(tomorrow);

        MongoDatabase database = mongoClient.getDatabase("SoilMoistureData");
        MongoCollection<Document> collection = database.getCollection("SoilMoisture");


        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("deviceId", deviceId)
                                .append("time",
                                        new Document("$gte", today)
                                                .append("$lt", tomorrow)
                                )
                )
        ));

        ArrayList<SoilMoistureData> li = new ArrayList<>();
        result.forEach(data -> li.add(converter.read(SoilMoistureData.class, data)));
        return li;
    }

    public ArrayList<SoilMoistureData> getByDay(String deviceId){
        return queryDuration(deviceId, 0, 1);
    }

    public ArrayList<SoilMoistureData> getByWeek(String deviceId){
        return queryDuration(deviceId,-6, 1);
    }


}
