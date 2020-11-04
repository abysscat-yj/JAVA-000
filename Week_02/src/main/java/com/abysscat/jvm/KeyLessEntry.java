package com.abysscat.jvm;

import java.util.HashMap;
import java.util.Map;

public class KeyLessEntry {

	static class Key {
		Integer id;
		Key(Integer id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			boolean result = false;
			if (obj instanceof Key) {
				Key key = (Key) obj;
				result = key.id.equals(this.id);
			}
			return result;
		}
	}

	public static void main(String[] args) {
		Map m = new HashMap();
		while (true) {
			for (int i = 0; i < 10000; i++) {
				if (!m.containsKey(new Key(i))) {
					m.put(new Key(i), "Number:" + i);
				}
			}
			System.out.println("m.size()=" + m.size());
		}
	}


}
