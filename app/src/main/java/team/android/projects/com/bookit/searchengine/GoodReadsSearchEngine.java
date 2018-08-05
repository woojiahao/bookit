package team.android.projects.com.bookit.searchengine;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilderFactory;

import team.android.projects.com.bookit.dataclasses.Book;
import team.android.projects.com.bookit.logging.ApplicationCodes;

public class GoodReadsSearchEngine implements ISearchEngine {
	private static String mKey;
	
	public GoodReadsSearchEngine(String key) {
		mKey = key;
	}
	
	public Map<String, Double> getPrices(String title) throws ExecutionException, InterruptedException {
		Map<String, Double> prices = new PriceTask().execute(title).get();
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
		private final double USD_TO_SGD_RATE = 1.37;
		
		@Override protected Map<String, Double> doInBackground(String... strings) {
			String title = strings[0];
			
			String base = "https://www.goodreads.com/book/title.xml";
			try {
				title = URLEncoder.encode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.e(ApplicationCodes.Error.name(), "Unable to encode URL");
				e.printStackTrace();
			}
			String query = String.format("%s?key=%s&title=%s", base, mKey, title);
			Log.d("Prices", query);
			
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
			Map<String, Double> prices = new HashMap<>();
			try {
				prices.put("Book Depository", bookDepositorySearch(links.get("Book Depository")));
				prices.put("Amazon", amazonSearch(links.get("Amazon")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prices;
		}
		
		private Double bookDepositorySearch(String url) throws IOException {
			return search(url, "span.sale-price");
		}
		
		private Double amazonSearch(String url) throws IOException {
			double type1 = search(url, "span.offer-price");
			double type2 = search(url, "span.header-price");
			double toReturn = -1;
			if (type1 == -1 && type2 > -1) {
				toReturn = type2;
			} else if (type1 > -1 && type2 == -1) {
				toReturn = type1;
			} else if (type1 != -1 && type2 != -1) {
				toReturn = type1 < type2 ? type1 : type2;
			}
			return toReturn * USD_TO_SGD_RATE;
		}
		
		private Double search(String url, String selector) throws IOException {
			String temp = "";
			
			try {
				org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
				org.jsoup.select.Elements prices = doc.select(selector);
				for (org.jsoup.nodes.Element price : prices) {
					temp = price.childNode(0).toString();
				}
			} catch (IllegalArgumentException | HttpStatusException e) {
				Log.e("Prices", "Invalid url: " + url);
				return -1.0;
			}
			double price = -1.0;
			temp = temp.trim();
			if (!temp.equals("")) {
				int indexOfDollar = temp.indexOf("$") + 1;
				String sub = temp.substring(indexOfDollar);
				price = Double.parseDouble(sub);
			}
			
			return price;
		}
	}
}
