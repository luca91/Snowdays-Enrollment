package com.snowdays_enrollment.test.model;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.test.general.JavaBeanTester;


/**
 * Tests for {@link  Group}.
 *
 * @author Luca Barazzuol)
 */
public class GroupTest {

	/**
	 * Test the Bean Group.class
	 * 
	 */
	@Test
	public void testBeanProperties() throws IntrospectionException{
	    JavaBeanTester.test(Group.class);
	}

}
