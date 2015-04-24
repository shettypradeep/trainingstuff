package com.pramati.training;

import java.util.HashSet;
/*
 * @Author : Pradeep Shetty
 * @Usage  : Class will set all the pre-requisites like setting application url, download folder path, mail counts for a month
 *
 * */
public class CUtils {
	private String appUrl;
	private String msglistUrl;
	private String msgUrl;
	private String downLoadFolderPath;
	private int totalMsgs;
	private String mailsForMonthYear;
	private int paginations;
	private HashSet<String> msglist;

	public HashSet<String> getMsglist() {
		return msglist;
	}

	public void setMsglist(HashSet<String> msglist) {
		this.msglist = msglist;
	}

	public int getPaginations() {
		return paginations;
	}

	public void setPaginations(int paginations) {
		this.paginations = paginations;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getMsglistUrl() {
		this.msglistUrl = appUrl.substring(0, appUrl.lastIndexOf("/"))
				+ "/ajax/thread";
		return msglistUrl;
	}

	public String getMsgUrl() {
		this.msgUrl = appUrl.substring(0, appUrl.lastIndexOf("/")) + "/raw/";
		return msgUrl;
	}

	public String getDownLoadFolderPath() {
		return downLoadFolderPath;
	}

	public void setDownLoadFolderPath(String downLoadFolderPath) {
		this.downLoadFolderPath = downLoadFolderPath + "/"
				+ getMailsForMonthYear();
	}

	public int getTotalMsgs() {
		return totalMsgs;
	}

	public void setTotalMsgs(int totalMsgs) {
		this.totalMsgs = totalMsgs;
	}

	public String getMailsForMonthYear() {
		this.mailsForMonthYear = appUrl.replaceAll("[^0-9]+", "").trim();
		return mailsForMonthYear;
	}

}
