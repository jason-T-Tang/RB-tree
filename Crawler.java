package pa3;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

//Crawler class which crawls for a list of 30 urls
public class Crawler {
	public String[] crawl(String searchTerm) throws IOException {
		String tag = "cite"; // identifier tag for URLs used by google
		String[] Urls = new String[30];// Container for the 30 Urls
		int counter = 1; // counter to ensure only 30 URLS are added

		// Performs a google search of the search term with 40 search entries and
		// records the Urls
		Document doc = Jsoup.connect("https://www.google.com/search?q=" + searchTerm + "&num=40").userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();// Creates the
																										// connection
																										// between the
																										// webpage and
																										// code
		Elements links = doc.getElementsByTag(tag);// Parses the document to find the desired URL elements

		// Converts 30 the elements to TextNodes, then to their text values
		for (Element link : links) { // enhanced for loop to parse the elements
			String name = ((TextNode) link.childNode(0)).text();// element to be added
			if (counter < 31 && (name.substring(0, 3).equals("htt") || name.substring(0, 3).equals("www"))) { // counter
																												// to
																												// only
																												// accept
																												// 30
																												// and
																												// avoid
																												// overflow
				// converts the element to the url text
				Urls[counter - 1] = name; // converting counter, which is # of elements, to array indexes
				counter++; // incrementation for the counter
			}
		}

		return Urls; // return the list of URLS
	}
}
