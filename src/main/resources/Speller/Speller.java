
import java.util.ArrayList;
import java.util.HashMap;

public class Speller {

	static HashMap<String, ArrayList<String>> spellCheck(String s) {
		ResultHandlerDom rhd = new ResultHandlerDom();
		return rhd.handResult(s);
	}

	public static void main(String[] args) {

	}
}