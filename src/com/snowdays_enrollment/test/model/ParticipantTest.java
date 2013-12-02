package com.snowdays_enrollment.test.model;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.test.general.JavaBeanTester;

/**
 * Tests for {@link  Participant}.
 *
 * @author Luca Barazzuol)
 */
public class ParticipantTest {

	/**
	 * Test the Bean Participant.class
	 * 
	 */
	@Test
	public void testBeanProperties() throws IntrospectionException{
	    JavaBeanTester.test(Participant.class);
	}

}
