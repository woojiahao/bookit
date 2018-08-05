package team.android.projects.com.bookit.searchengine;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilderFactory;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;

public class GoodReadsSearchEngine implements ISearchEngine {
	private static String mKey;
	private static String base = "https://www.goodreads.com/book/isbn/";
	
	public GoodReadsSearchEngine(String key) {
		mKey = key;
	}
	
	public Map<String, Double> getPrices(String isbn) throws ExecutionException, InterruptedException {
		Map<String, Double> prices = new PriceTask().execute(isbn).get();
		Log.d("Prices", "Retrieved");
		return prices;
	}
	
	@Override public List<Book> groupSearch(SearchType searchType, String group, long querySize) {
		return null;
	}
	
	@Override public List<Book> bookSearch(SearchType searchType, String search) {
		return null;
	}
	
	@Override public List<Book> batchSearch(SearchType searchType, String[] searchTerms) {
		return null;
	}
	
	private static class PriceTask extends AsyncTask<String, Void, Map<String, Double>> {
		@Override protected Map<String, Double> doInBackground(String... strings) {
			String isbn = strings[0];
			
			String query = String.format("%s%s?key=%s", base, isbn, mKey);
			
			Document doc = null;
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(query).openConnection();
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
			} catch (Exception e) {
				Log.e(ApplicationCodes.Error.name(), "Unable to read document");
				e.printStackTrace();
			}
			
			Map<String, String> links = new HashMap<>();
			if (doc != null) {
				String bookId = ((Element) doc.getElementsByTagName("book").item(0))
						.getElementsByTagName("id")
						.item(0)
						.getTextContent();
				
				NodeList buyLinks = doc.getElementsByTagName("buy_link");
				links = new HashMap<String, String>() {{
					for (int i = 0; i < buyLinks.getLength(); i++) {
						Node link = buyLinks.item(i);
						if (link.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element) link;
							String location = element.getElementsByTagName("name").item(0).getTextContent();
							String follow = element.getElementsByTagName("link").item(0).getTextContent();
							put(location, String.format("%s?book_id=%s&source=compareprices", follow, bookId));
						}
					}
				}};
			}
			Map<String, String> finalLinks = links;
			return new HashMap<String, Double>() {{
				try {
					put("Book Depository", bookDepositorySearch(finalLinks.get("Book Depository")));
					put("Amazon", amazonSearch(finalLinks.get("Amazon")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}};
		}
		
		private Double bookDepositorySearch(String url) throws IOException {
			return search(url, "div.price > span.sale-price");
		}
		
		private Double amazonSearch(String url) throws IOException {
			return search(url, "span.offer-price");
		}
		
		private Double search(String url, String selector) throws IOException {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			org.jsoup.select.Elements prices = doc.select(selector);
			String temp = "";
			for (org.jsoup.nodes.Element price : prices) {
				temp = price.childNode(0).toString();
			}
			return temp.equals("") ? 0.0 : Double.parseDouble(temp.substring(temp.indexOf("$") + 1));
		}
	}
}
