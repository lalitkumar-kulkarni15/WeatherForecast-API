package com.weatherforecast.api.service.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.weatherforecast.api.service.DateRangePredicate;

@RunWith(MockitoJUnitRunner.class)
public class DateRangePredicateTest {
	
	@Test
	public void isWithinDayRange_returnsTrue() {
		
		Predicate<LocalDateTime> consumer = DateRangePredicate.isWithinDayRange(3);
		LocalDateTime dateTm = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 12, 0);
		boolean isWithinDayRange = consumer.test(dateTm);
		assertTrue(isWithinDayRange);
	}
	
	@Test
	public void isWithinDayRange_returnsFalse() {
		
		Predicate<LocalDateTime> consumer = DateRangePredicate.isWithinDayRange(3);
		LocalDateTime dateTm = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 03, 0);
		boolean isWithinDayRange = consumer.test(dateTm);
		assertFalse(isWithinDayRange);
	}
	
	@Test
	public void isWithinNightlyRange_returnsTrue() {
		
		Predicate<LocalDateTime> consumer = DateRangePredicate.isWithinNightlyRange(3);
		LocalDateTime dateTm = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 00, 0);
		boolean isWithinDayRange = consumer.test(dateTm.plusDays(1));
		assertTrue(isWithinDayRange);
	}
	
	@Test
	public void isWithinNightlyRange_returnsFalse() {
		
		Predicate<LocalDateTime> consumer = DateRangePredicate.isWithinNightlyRange(3);
		LocalDateTime dateTm = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 15, 0);
		boolean isWithinDayRange = consumer.test(dateTm.plusDays(1));
		assertFalse(isWithinDayRange);
	}
	
	

}
