package org.springframework.clinicaetsii.configuration;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class H2Function {

	public static long dateDifference(final Date date1, final Date date2) {
		Objects.nonNull(date1);
		Objects.nonNull(date2);
		return ChronoUnit.DAYS.between(date2.toLocalDate(), date1.toLocalDate());
	}

}
