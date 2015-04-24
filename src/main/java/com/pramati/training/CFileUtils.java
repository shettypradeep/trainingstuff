package com.pramati.training;

/**
 * @Author : Pradeep Shetty
 * @Usage  : Creates a directory structure and read the contents from a file or url
 * @Creation Date : 24-04-2015
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CFileUtils {
	public CUtils utils;

	public CFileUtils(CUtils utils) {
		this.utils = utils;
	}

	/**
	 * Get the file content from specified url
	 * 
	 * @param url
	 * @return
	 */
	private String getFileConent(String url) {
		String strTemp = "", fileContent = "";
		BufferedReader br = null;
		try {
			URL my_url = new URL(url);
			br = new BufferedReader(new InputStreamReader(my_url.openStream()));

			while (null != (strTemp = br.readLine())) {
				fileContent = fileContent + strTemp + "\r\n";
			}
			br.close();
			return fileContent;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Unhandled Exception while reading filecontent");
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Method used to write content to specified file
	 * 
	 * @param downloadPath
	 * @param fileContent
	 */
	public void writeConentToFile(String downloadPath, String fileContent) {
		File dir;
		PrintWriter p = null;

		try {
			dir = new File(utils.getDownLoadFolderPath());
			dir.mkdirs();
			p = new PrintWriter(new FileWriter(new File(dir, downloadPath)));
			p.write(getFileConent(fileContent));
			p.flush();
			p.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out
					.println("Unhandled Exception while writing filecontent in writeConentToFile");
		} finally {
			if (p != null) {
				p.close();
			}
		}

	}

	/**
	 * Reads all mail urls in specified month
	 * 
	 * @return true if all data was read or false
	 */
	public boolean getMails() {
		try {
			String msglistUrl, msgUrl, encodedUrl;
			HashSet<String> msgUrls = new HashSet<String>();

			msglistUrl = utils.getMsglistUrl();
			msgUrl = utils.getMsgUrl();

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new URL(msglistUrl + "?0")
					.openStream());
			doc.getDocumentElement().normalize();

			int paginations = Integer.parseInt(doc.getDocumentElement()
					.getAttribute("pages"));
			utils.setPaginations(paginations);

			for (int k = 0; k < paginations; k++) {
				doc = docBuilder.parse(new URL(msglistUrl + "?" + k)
						.openStream());
				NodeList listOfMsgs = doc.getElementsByTagName("message");
				for (int i = 0; i < listOfMsgs.getLength(); i++) {
					Node msgNode = listOfMsgs.item(i);
					if (msgNode.getNodeType() == Node.ELEMENT_NODE) {
						Element firstElement = (Element) msgNode;
						encodedUrl = URLEncoder.encode(
								firstElement.getAttribute("id"), "UTF-8");
						msgUrls.add(msgUrl + encodedUrl);
					}
				}

			}
			utils.setTotalMsgs(msgUrls.size());
			utils.setMsglist(msgUrls);
			return true;
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());
			return false;
		} catch (SAXException e) {
			System.out.println(" " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println(" " + e.getMessage());
			return false;
		}

	}

}
