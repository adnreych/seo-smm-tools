

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchUrl {

	private String part1 = "https://search-maps.yandex.ru/v1/?text=";
	private String part2 = "&type=biz&lang=ru_RU&results=1&apikey=024a4de3-f595-4d24-a2ab-b553cb0a3d02";

	protected String getUrl(String adress) {
		try {
			URL concatUrl = new URL(part1 + adress + part2);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode newNode = mapper.readTree(concatUrl);
			return newNode.findPath("url").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}