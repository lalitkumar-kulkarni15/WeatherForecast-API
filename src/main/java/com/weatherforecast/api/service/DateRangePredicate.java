package com.weatherforecast.api.service;

import static com.weatherforecast.api.utils.WeatherFrecstUtils.getElligibleDatesForRange;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

public interface DateRangePredicate {

	public static Predicate<LocalDateTime> isWithinDayRange(final String noOfDays) {

		Predicate<LocalDateTime> dateRangePredicate = dtTm -> {

			List<LocalDate> listLocalDate = getElligibleDatesForRange(noOfDays);

			for (LocalDate rangeDate : listLocalDate) {
				LocalDateTime localDtTmFrom = LocalDateTime.of(rangeDate.getYear(), rangeDate.getMonth(),
						rangeDate.getDayOfMonth(), 06, 00);
				LocalDateTime localDtTmTo = LocalDateTime.of(localDtTmFrom.getYear(), localDtTmFrom.getMonth(),
						localDtTmFrom.getDayOfMonth(), 15, 00);
				boolean inRange = (dtTm.isAfter(localDtTmFrom) || dtTm.isEqual(localDtTmFrom))
						&& (dtTm.isBefore(localDtTmTo) || dtTm.isEqual(localDtTmTo));

				if (inRange) {
					return true;
				} else {
					continue;
				}

			}
			return false;

		};

		return dateRangePredicate;
	}

	public static Predicate<LocalDateTime> isWithinNightlyRange(final String noOfDays) {

		Predicate<LocalDateTime> dateRangePredicate = dtTm -> {

			List<LocalDate> listLocalDate = getElligibleDatesForRange(noOfDays);

			for (LocalDate rangeDate : listLocalDate) {
				LocalDateTime localDtTmFrom = LocalDateTime.of(rangeDate.getYear(), rangeDate.getMonth(),
						rangeDate.getDayOfMonth(), 18, 00);
				LocalDateTime nextDate = localDtTmFrom.plusDays(1);
				LocalDateTime localDtTmTo = LocalDateTime.of(nextDate.getYear(), nextDate.getMonth(),
						nextDate.getDayOfMonth(), 06, 00);
				boolean inRange = (dtTm.isAfter(localDtTmFrom) || dtTm.isEqual(localDtTmFrom))
						&& (dtTm.isBefore(localDtTmTo) || dtTm.isEqual(localDtTmTo));

				if (inRange) {
					return true;
				} else {
					continue;
				}

			}
			return false;

		};

		return dateRangePredicate;
	}

}
