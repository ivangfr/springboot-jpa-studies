package com.ivanfranchin.producer.service;

import com.ivanfranchin.producer.model.OpeningHourJavaTimeZone;
import com.ivanfranchin.producer.repository.OpeningHourJavaTimeZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaTimeZoneServiceImpl implements OpeningHourJavaTimeZoneService {

    private final OpeningHourJavaTimeZoneRepository openingHourJavaTimeZoneRepository;

    @Override
    public OpeningHourJavaTimeZone create(String date, String begin, String end, String zoneId) {
        LocalDate ldDate = LocalDate.parse(date);
        LocalTime ltBegin = LocalTime.parse(begin);
        LocalTime ltEnd = LocalTime.parse(end);

        return save(ldDate, ltBegin, ltEnd, zoneId);
    }

    @Override
    public OpeningHourJavaTimeZone createNow(String zoneId) {
        LocalDate ldDate = LocalDate.now(ZoneId.of(zoneId));
        LocalTime ltBegin = LocalTime.now(ZoneId.of(zoneId));
        LocalTime ltEnd = LocalTime.now(ZoneId.of(zoneId));

        return save(ldDate, ltBegin, ltEnd, zoneId);
    }

    @Override
    public void deleteAll() {
        openingHourJavaTimeZoneRepository.deleteAll();
    }

    private OpeningHourJavaTimeZone save(LocalDate ldDate, LocalTime ltBegin, LocalTime ltEnd, String zoneId) {
        OpeningHourJavaTimeZone openingHourJavaTimeZone = new OpeningHourJavaTimeZone();
        openingHourJavaTimeZone.setDate(ldDate);
        openingHourJavaTimeZone.setBegin(ltBegin);
        openingHourJavaTimeZone.setEnd(ltEnd);
        openingHourJavaTimeZone.setDateTimeBegin(ZonedDateTime.of(ldDate, ltBegin, ZoneId.of(zoneId)));
        openingHourJavaTimeZone.setDateTimeEnd(ZonedDateTime.of(ldDate, ltEnd, ZoneId.of(zoneId)));

        return openingHourJavaTimeZoneRepository.save(openingHourJavaTimeZone);
    }
}
