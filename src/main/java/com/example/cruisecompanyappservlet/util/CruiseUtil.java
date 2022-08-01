package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.entity.Port;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CruiseUtil {
    public static HashMap<Port, Date> calculateSchedule(Date departure,List<Port> ports, List<Integer> delays){
        HashMap<Port,Date> schedule = new HashMap<>();
        schedule.put(ports.get(0),departure);
        Date tmpDate = new Date(departure.getTime());
        for(int i =0;i<delays.size();i++){
            tmpDate = new Date(tmpDate.getTime());
            tmpDate.setDate(tmpDate.getDate()+delays.get(i)+1);
            schedule.put(ports.get(i+1),tmpDate);
        }
        return schedule;
    }
}
