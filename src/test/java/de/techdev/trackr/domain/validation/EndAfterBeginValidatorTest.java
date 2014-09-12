package de.techdev.trackr.domain.validation;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.validation.Validation;
import javax.validation.Validator;

import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;

public class EndAfterBeginValidatorTest {

	@EndAfterBegin(begin="fromDate",end="toDate")
	static class EndIsLong{

		Date fromDate=new Date(0L);
		Long toDate=1L;
	}
	
	@EndAfterBegin(begin="fromDate",end="toDate")
	static class BeginIsLong{

		Long fromDate=0L;
		Date toDate=new Date(1L);
	}

//	@Test
	public void validateFailsOnEndIsLong() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		try {
			validator.validate(new EndIsLong());
			fail("ValidationException caused by an IllegalArgumentException expected" );
		} catch(Exception ex) {
			assertThat(ex.getCause(), instanceOf(IllegalArgumentException.class));	
		}
	}
	
//	@Test
	public void validateFailsOnBeginIsLong() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		try {
			validator.validate(new BeginIsLong());
			fail("ValidationException caused by an IllegalArgumentException expected" );
		} catch(Exception ex) {
			assertThat(ex.getCause(), instanceOf(IllegalArgumentException.class));	
		}
	}

}
