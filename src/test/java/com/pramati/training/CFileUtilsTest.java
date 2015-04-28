package com.pramati.training;

import static org.junit.Assert.*;

import org.junit.Test;

public class CFileUtilsTest {
	CUtils utils=new CUtils();
	CFileUtils futils = new CFileUtils(utils);
	String url="http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/thread";
	@Test
	public void testWriteConentToFile() {
		utils.setAppUrl(url);
		utils.setDownLoadFolderPath("../Downloads");
		futils.writeConentToFile("1.txt", "sshttp://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/raw/%3c547C1A5F.7070709@uni-jena.de%3e");
		futils.writeConentToFile("1.txt", "http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/raw/%3c547C1A5F.7070709@uni-jena.de%3e");
	}

	@Test
	public void testGetMails() {
		assertFalse("Unable to read mails...",futils.getMails());
		utils.setAppUrl(url);
		assertTrue("Read the all mail urls for specified year ",futils.getMails());
	}

}
