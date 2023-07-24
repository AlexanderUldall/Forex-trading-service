package forex.rest;

import forex.db.ForexEntity;
import forex.enums.CURRENCY_CODE;

import java.time.LocalTime;

public class CommonTestData {

    public static LocalTime NEVER_POSSIBLE_TIME = LocalTime.of(00, 00, 00);
    public static LocalTime ALWAYS_POSSIBLE_TIME = LocalTime.MAX;

    public static ForexEntity getForexEntityAUD() {
        ForexEntity forexEntity = new ForexEntity();
        forexEntity.setCURRENCY_CODE(CURRENCY_CODE.AUD);
        forexEntity.setCountryName("Australia");
        forexEntity.setTodayTime(NEVER_POSSIBLE_TIME);
        forexEntity.setTomorrowTime(LocalTime.of(14, 0, 0));
        forexEntity.setAfterTomorrowTime(ALWAYS_POSSIBLE_TIME);

        return forexEntity;
    }

    public static ForexEntity getForexEntityCAD() {
        ForexEntity forexEntity = new ForexEntity();
        forexEntity.setCURRENCY_CODE(CURRENCY_CODE.CAD);
        forexEntity.setCountryName("'Canada");
        forexEntity.setTodayTime(LocalTime.of(15, 30, 0));
        forexEntity.setTomorrowTime(ALWAYS_POSSIBLE_TIME);
        forexEntity.setAfterTomorrowTime(ALWAYS_POSSIBLE_TIME);

        return forexEntity;
    }
}
