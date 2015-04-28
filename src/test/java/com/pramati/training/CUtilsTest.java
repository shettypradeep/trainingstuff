package com.pramati.training;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class CUtilsTest {
	CUtils utils = new CUtils();
	String url="http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/thread";

	@Test
	public void testGetMsglist() {
		assertNull(utils.getMsglist());
		utils.setMsglist(new HashSet<String>());
		assertNotNull(utils.getMsglist());
	}

	@Test
	public void testGetPaginations() {
		boolean b = (0==utils.getPaginations());
		assertTrue(b);
		utils.setPaginations(10);
		b = (10==utils.getPaginations());
		assertTrue(b);
	}

	@Test
	public void testGetAppUrl() {
		assertNull(utils.getAppUrl());
		utils.setAppUrl(url);
		assertEquals(url,utils.getAppUrl());
		utils.setAppUrl(url+"/wrong");
		assertNotSame(utils, utils.getAppUrl());
		utils.setAppUrl(null);
		assertNull(utils.getAppUrl());
	}

	@Test
	public void testGetMsglistUrl() {
		utils.setAppUrl(url);
		assertEquals("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/ajax/thread",utils.getMsglistUrl());
	}

	@Test
	public void testGetMsgUrl() {
		utils.setAppUrl(url);
		assertEquals("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/raw/",utils.getMsgUrl());
	}

	@Test
	public void testGetDownLoadFolderPath() {
		utils.setAppUrl(url);
		boolean b=(201412==Integer.parseInt(utils.getMailsForMonthYear()));
		assertTrue("Expected 201412 and Actual is 201412",b);
		assertNull(utils.getDownLoadFolderPath());
		utils.setDownLoadFolderPath("/Downloads");
		assertEquals("/Downloads/201412",utils.getDownLoadFolderPath());

	}

	@Test
	public void testGetTotalMsgs() {
		boolean b=(0==utils.getTotalMsgs());
		assertTrue("Expected 0 and Actual is 0",b);
		utils.setTotalMsgs(10);
		b=(10==utils.getTotalMsgs());
		assertTrue("Expected 10 and Actual is 10",b);
		utils.setTotalMsgs(15);
		b=(10==utils.getTotalMsgs());
		assertFalse("Expected 15 and Actual is 10",b);
	}

	@Test
	public void testGetMailsForMonthYear() {
		utils.setAppUrl(url);
		boolean b=(201412==Integer.parseInt(utils.getMailsForMonthYear()));
		assertTrue("Expected 201412 and Actual is 201412",b);
		b=(201413==Integer.parseInt(utils.getMailsForMonthYear()));
		assertFalse("Expected 201413 and Actual is 201412",b);
	}

}
