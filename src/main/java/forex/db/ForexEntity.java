package forex.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forex_cutoff")
public class ForexEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE")
    private forex.enums.CURRENCY_CODE CURRENCY_CODE;
    @Column(name = "country")
    private String countryName;
    @Column(name = "today_time")
    private LocalTime todayTime;
    @Column(name = "tomorrow_time")
    private LocalTime tomorrowTime;
    @Column(name = "after_tomorrow_time")
    private LocalTime afterTomorrowTime;

}