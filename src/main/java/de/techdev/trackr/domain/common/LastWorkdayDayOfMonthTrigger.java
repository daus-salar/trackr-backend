package de.techdev.trackr.domain.common;

import de.techdev.trackr.util.LocalDateUtil;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * A trigger that is executed on the last workday in the month.
 *
 * @author Moritz Schulze
 */
public class LastWorkdayDayOfMonthTrigger implements Trigger {

    /**
     * Get the last day in month that is not a saturday or sunday.
     *
     * @param date The month
     * @return New date with the last day that is not saturday or sunday in the month.
     */
    protected LocalDate lastWeekdayInMonth(LocalDate date) {
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        if(lastDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return lastDayOfMonth.minusDays(2);
        }
        if(lastDayOfMonth.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return lastDayOfMonth.minusDays(1);
        }
        return lastDayOfMonth;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        return LocalDateUtil.fromLocalDate(nextExecutionTimeInternal(triggerContext));
    }

    protected LocalDate nextExecutionTimeInternal(TriggerContext triggerContext) {
        LocalDate now = LocalDate.now();
        if (triggerContext.lastScheduledExecutionTime() != null &&
                LocalDateUtil.fromDate(triggerContext.lastScheduledExecutionTime()).getMonth() == now.getMonth()) {
            now = now.plusMonths(1);
        }
        return lastWeekdayInMonth(now);
    }
}