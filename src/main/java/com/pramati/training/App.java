package com.pramati.training;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Execution starts from this class
 * 
 */
public class App {
	public static void main(String[] args) throws IOException {
		long startTime, stopTime, elapsedTime;
		startTime = System.currentTimeMillis();

		Document doc = Jsoup.connect(
				"http://mail-archives.apache.org/mod_mbox/maven-users/").get();
		Elements links = doc.select("a[href~=2014][href$=thread]");
		for (Element l : links) {
			CUtils utils = new CUtils();
			utils.setAppUrl(l.attr("abs:href").trim());
			utils.setDownLoadFolderPath("../Downloads");
			System.out.println("Msgt url....." + utils.getDownLoadFolderPath());
			
			CFileUtils FileUtils = new CFileUtils(utils);
			FileUtils.getMails();

			List<String> msgs = new ArrayList<String>(utils.getMsglist());
			System.out.println("We are going to download " + msgs.size()
					+ " mails into folder " + utils.getDownLoadFolderPath());

			ExecutorService executor = Executors.newFixedThreadPool(10);
			for (int i = 0; i < msgs.size(); i++) {
				Runnable worker = new CMsgThread(i + ".txt", msgs.get(i),
						FileUtils);
				executor.execute(worker);
			}
			executor.shutdown();
			while (!executor.isTerminated()) {

			}
			System.out.println("Mails were downloaded for the month and year "
					+ utils.getMailsForMonthYear() + "....into folder..."
					+ utils.getDownLoadFolderPath()
					+ "......total messages are...." + msgs.size());
		}
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Files were downloaded in time.... " + elapsedTime
				/ 1000 + " secs");

	}
}
