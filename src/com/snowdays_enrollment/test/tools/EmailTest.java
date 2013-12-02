package com.snowdays_enrollment.test.tools;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.snowdays_enrollment.tools.Email;

/**
 * Tests for {@link  Email}.
 *
 * @author Luca Barazzuol)
 */
@RunWith(JUnit4.class)
public class EmailTest {

	@Test
	public final void testEmail() {
		Email e = new Email();
		Properties p = e.getProps();
			
		Assert.assertEquals("failure - wrong property [mail.smtp.starttls.enable] ", p.getProperty("mail.smtp.starttls.enable"), "true");
		Assert.assertEquals("failure - wrong property [mail.smtp.host] ", p.getProperty("mail.smtp.host"), "smtp.googlemail.com");
		Assert.assertEquals("failure - wrong property [mail.smtp.user] ", p.getProperty("mail.smtp.user"), "ems2013.staff@gmail.com");
		Assert.assertEquals("failure - wrong property [mail.smtp.password] ", p.getProperty("mail.smtp.password"), "PaSsWoRd");
		Assert.assertEquals("failure - wrong property [mail.smtp.port] ", p.getProperty("mail.smtp.port"), "587");
		Assert.assertEquals("failure - wrong property [mail.smtp.auth] ", p.getProperty("mail.smtp.auth"), "true");
		
	}
	
	@Test
	public final void testSendEmail() {
		
		Email e = new Email();
		Assert.assertTrue("failure - email address is wrong", e.sendEmail("luca.barazzuol@gmail.com", "test", "message"));
		Assert.assertFalse("failure - email sent also if the addres is wrong", e.sendEmail("luca.barazzuol", "test", "message"));
	}
}
