package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.account.Savings;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class SavingsResponseDto {
    private Long savingsTotal;

    private String createdAt;

    private String content;

    private String total;

    public SavingsResponseDto(Savings savings) {
        String localDate = LocalDate.parse(savings.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toString();
        String currentMonth = savings.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("MM"));

        this.savingsTotal = savings.getSavingsTotal();
        this.createdAt = savings.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("MM.dd"));
        this.content = currentMonth + "월" + koreanDate(returnWeek(localDate)) + "주 적금";
        this.total = "+" + savings.getTotal();
    }

    public int returnWeek(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            log.info("에러났어용");
        }
        // 날짜 입력하는곳 .
        date = new Date(date.getTime() + (1000 * 60 * 60 * 24 - 1));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    public String koreanDate(int strDate) {
        Map map = new HashMap<Integer, String>();
        map.put(1, "첫째");
        map.put(2, "둘째");
        map.put(3, "셋째");
        map.put(4, "넷째");
        map.put(5, "다섯째");
        return (String) map.get(strDate);
    }
}
