package com.mycompany.producer.service;

import com.mycompany.producer.model.OpeningHourJavaTimeLocal;
import com.mycompany.producer.repository.OpeningHourJavaTimeLocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaTimeLocalServiceImpl implements OpeningHourJavaTimeLocalService {

    private final OpeningHourJavaTimeLocalRepository openingHourJavaTimeLocalRepository;

    @Override
    public OpeningHourJavaTimeLocal create(String date, String begin, String end) {
        LocalDate ldDate = LocalDate.parse(date);
        LocalTime ltBegin = LocalTime.parse(begin);
        LocalTime ltEnd = LocalTime.parse(end);

        return save(ldDate, ltBegin, ltEnd);
    }

    @Override
    public OpeningHourJavaTimeLocal createNow() {
        LocalDate ldDate = LocalDate.now();
        LocalTime ltBegin = LocalTime.now();
        LocalTime ltEnd = LocalTime.now();

        return save(ldDate, ltBegin, ltEnd);
    }

    @Override
    public void deleteAll() {
        openingHourJavaTimeLocalRepository.deleteAll();
    }

    private OpeningHourJavaTimeLocal save(LocalDate ldDate, LocalTime ltBegin, LocalTime ltEnd) {
        OpeningHourJavaTimeLocal openingHourJavaTimeLocal = new OpeningHourJavaTimeLocal();
        openingHourJavaTimeLocal.setDate(ldDate);
        openingHourJavaTimeLocal.setBegin(ltBegin);
        openingHourJavaTimeLocal.setEnd(ltEnd);
        openingHourJavaTimeLocal.setDateTimeBegin(LocalDateTime.of(ldDate, ltBegin));
        openingHourJavaTimeLocal.setDateTimeEnd(LocalDateTime.of(ldDate, ltEnd));

        return openingHourJavaTimeLocalRepository.save(openingHourJavaTimeLocal);
    }
}
