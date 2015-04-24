package com.pramati.training;

/**
 * @Author : Pradeep Shetty
 * @Usage  : Thread which writes data to different files
 * @Creation Date : 24-04-2015
 */
public class CMsgThread implements Runnable {
	public String downloadPath, mailUrl;
	public CFileUtils futils;

	public CMsgThread(String downloadPath, String mailUrl, CFileUtils futils) {
		this.downloadPath = downloadPath;
		this.mailUrl = mailUrl;
		this.futils = futils;
	}

	@Override
	public void run() {
		/*System.out.println("File Name : " + this.downloadPath
				+ " performed by " + Thread.currentThread().getName());*/

		futils.writeConentToFile(downloadPath, mailUrl);
	}

}
