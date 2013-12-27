package com.snowdays_enrollment.test.model;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.test.general.JavaBeanTester;


/**
 * Tests for {@link  User}.
 *
 * @author Luca Barazzuol)
 */
public class UserTest {

	/**
	 * Test the Bean User.class
	 * 
	 */
	@Test
	public void testBeanProperties() throws IntrospectionException{
	    JavaBeanTester.test(User.class);
	}

}
