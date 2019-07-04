import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetText {
	private HashSet<String> words;

	GetText(String s) {
		Pattern pattern = Pattern.compile("[А-Яа-яЁё]+&?", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(s);
		HashSet<String> words = new HashSet<String>();
		while (matcher.find()) {
			words.add(s.substring(matcher.start(), matcher.end()));
		}
		this.words = words;
	}

	private HashSet<String> urlClusters = new HashSet<String>();

	public HashSet<String> getUrlClusters() {
		this.doClusters();
		return urlClusters;
	}

	private void doClusters() {
		int counter;
		HashSet<String> urlClusters = new HashSet<String>();
		for (Iterator<String> i = words.iterator(); i.hasNext();) {
			String clusterElement = "";
			counter = 0;
			while (counter * 4 < 1000 & i.hasNext()) { // API takes 1000 UTF-8 chars max
				String s = i.next();
				clusterElement = clusterElement + s + "+";
				counter += s.length();
			}
			urlClusters.add(clusterElement + " ");

		}
		this.urlClusters = urlClusters;
	}

}
