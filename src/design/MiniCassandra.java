package design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

class Column {
	Integer a;
	String b;

	Column(Integer a, String b) {

	}
}

public class MiniCassandra {
	private Map<String, NavigableMap<Integer, String>> hash;

	public MiniCassandra() {
		hash = new HashMap<String, NavigableMap<Integer, String>>();
	}

	public void insert(String raw_key, int column_key, String column_value) {
		if (!hash.containsKey(raw_key))
			hash.put(raw_key, new TreeMap<Integer, String>());
		hash.get(raw_key).put(column_key, column_value);
	}

	public List<Column> query(String raw_key, int column_start, int column_end) {
		List<Column> rt = new ArrayList<Column>();
		if (!hash.containsKey(raw_key))
			return rt;
		for (Map.Entry<Integer, String> entry : hash.get(raw_key)
				.subMap(column_start, true, column_end, true).entrySet()) {
			rt.add(new Column(entry.getKey(), entry.getValue()));
		}
		return rt;
	}
}
