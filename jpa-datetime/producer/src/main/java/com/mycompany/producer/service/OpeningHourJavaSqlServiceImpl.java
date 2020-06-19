package com.mycompany.producer.service;

import com.mycompany.producer.model.OpeningHourJavaSql;
import com.mycompany.producer.repository.OpeningHourJavaSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaSqlServiceImpl implements OpeningHourJavaSqlService {

    private final OpeningHourJavaSqlRepository openingHourJavaSqlRepository;

    @Override
    public OpeningHourJavaSql create(String date, String begin, String end) throws ParseException {
        Date dDate = Date.valueOf(date);
        Time tBegin = Time.valueOf(begin);
        Time tEnd = Time.valueOf(end);
        java.util.Date dateBegin = convertStringToDate(date, begin);
        java.util.Date dateEnd = convertStringToDate(date, end);

        return save(dDate, tBegin, tEnd, dateBegin, dateEnd);
    }

    @Override
    public OpeningHourJavaSql createNow() {
        java.util.Date now = new java.util.Date();
        Date date = new Date(now.getTime());
        Time begin = new Time(now.getTime());
        Time end = new Time(now.getTime());

        return save(date, begin, end, now, now);
    }

    @Override
    public void deleteAll() {
        openingHourJavaSqlRepository.deleteAll();
    }

    private OpeningHourJavaSql save(Date date, Time begin, Time end, java.util.Date dateBegin, java.util.Date dateEnd) {
        OpeningHourJavaSql openingHourJavaSql = new OpeningHourJavaSql();
        openingHourJavaSql.setDate(date);
        openingHourJavaSql.setBegin(begin);
        openingHourJavaSql.setEnd(end);
        openingHourJavaSql.setDateTimeBegin(dateBegin);
        openingHourJavaSql.setDateTimeEnd(dateEnd);

        return openingHourJavaSqlRepository.save(openingHourJavaSql);
    }

    private java.util.Date convertStringToDate(String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(String.format("%s %s", date, time));
    }

}
