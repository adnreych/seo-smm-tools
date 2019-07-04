
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetMail {
	private SearchUrl searchURL = new SearchUrl();
	private ArrayList<String> query;
	private ArrayList<String> domains = new ArrayList<String>();
	private LinkedHashSet<String> siteNotFound = new LinkedHashSet<String>();
	private LinkedHashMap<String, LinkedHashSet<String>> emails = new LinkedHashMap<String, LinkedHashSet<String>>();

	public GetMail() {

	}

	public GetMail(ArrayList<String> query) {
		this.query = query;
	}

	private void fillSiteDomains() {
		for (String q : query) {
			String s = searchURL.getUrl(q);
			if (!s.equals(""))
				domains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
			else
				siteNotFound.add(q);
		}
	}

	private String validateInnerUrl(String inner, String domain) {
		if (inner.charAt(0) == '/')
			inner = inner.substring(1);
		if (inner.contains(domain.substring(8))) // "https://".length()==8
			inner = inner.substring(domain.length() - 1);
		return inner;
	}

	private String getInnerPage(String domain) {
		String inner = "Страница Контакты не найдена";
		try {
			Document html = Jsoup.connect(domain).get();
			Elements els = html.select("a");
			for (Element e2 : els) {
				if (e2.ownText().equals("Контакты"))
					inner = e2.attr("href");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		inner = validateInnerUrl(inner, domain);
		return inner;
	}

	private LinkedHashSet<String> searchMails(String domain) {
		LinkedHashSet<String> mails = new LinkedHashSet<String>();
		try {
			Document html = Jsoup.connect(domain).get();
			Elements sel = html.select("a[href~=.+@.+]");
			for (Element e : sel) {
				mails.add(e.attr("href"));
			}
			return mails;
		} catch (HttpStatusException e) {
			mails.add("Not found Emails on site");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mails;
	}

	public LinkedHashMap<String, LinkedHashSet<String>> getMail() {
		fillSiteDomains();
		for (String domain : domains) {
			emails.put(domain, searchMails(domain));
			if (searchMails(domain).isEmpty()) {
				emails.put(domain, searchMails(domain + getInnerPage(domain)));
			}
		}
		emails.put("Не удалось найти сайты по запросам: ", siteNotFound);
		return emails;
	}

}
