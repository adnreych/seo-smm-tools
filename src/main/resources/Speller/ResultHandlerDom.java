import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ResultHandlerDom {

	private HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

	private void resultHandlerDOM(String xmlSpeller) throws ParserConfigurationException, SAXException, IOException {
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		ByteArrayInputStream input = new ByteArrayInputStream(xmlSpeller.getBytes("UTF-8"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);
		NodeList errors = document.getDocumentElement().getElementsByTagName("error");
		for (int i = 0; i < errors.getLength(); i++) {
			NodeList currErr = errors.item(i).getChildNodes();
			ArrayList<String> version = new ArrayList<String>();
			for (int j = currErr.getLength() - 1; j > 0; j--) {
				version.add(currErr.item(j).getTextContent());
			}
			result.put(currErr.item(0).getTextContent(), version);
		}
		this.result = result;
	}

	public HashMap<String, ArrayList<String>> handResult(String url) {
		try {
			HashMap<String, ArrayList<String>> finalResult = new HashMap<String, ArrayList<String>>();
			String s = Jsoup.connect(url).get().outerHtml();// получаем исходный код проверяемого сайта
			GetText getText = new GetText(s); // отбираем все слова с русскими символами и
			HashSet<String> cl = getText.getUrlClusters(); // разбиваем на блоки, чтобы не было слишком длинного URL
			for (String cluster : cl) {
				String resultSpellXML = "https://speller.yandex.net/services/spellservice/checkText?text=" + cluster;
				resultHandlerDOM(Jsoup.connect(resultSpellXML).get().outerHtml());
				finalResult.putAll(result);
			}
			return finalResult;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
