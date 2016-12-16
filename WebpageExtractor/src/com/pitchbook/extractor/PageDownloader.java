package com.pitchbook.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * The Class PageDownloader downloads the page from url
 * @author Avijit Vishen
 */
public class PageDownloader {
	
	/** The url. */
	String url;
	
	/**
	 * Instantiates a new page downloader.
	 *
	 * @param url the url
	 */
	public PageDownloader(String url) {
		this.url = url;
	}
	
	/**
	 * Gets the raw page by opening a connection
	 *
	 * @return the raw page
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getRawPage() throws IOException
	{
		checkLink();
		String line = "";
	    URL myUrl = null;
	    StringBuilder bd = new StringBuilder();
	    BufferedReader in = null;
	    try {
	        myUrl = new URL(url);
	        in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

	        while ((line = in.readLine()) != null) {
	            bd.append(line);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	    }
	    return bd.toString();
	}
	
	/**
	 * Check link for invalid protocols or missing http protocol
	 */
	protected void checkLink()
	{
		if(url.isEmpty()) throw new IllegalArgumentException("Link Not Provided");
		if(!url.startsWith("http"))
		{
			if(url.contains("//"))throw new IllegalArgumentException("Protocol not Supported. Try with HTTP or HTTPS");
			url = "http://"+ url;
		}
	}
}
