package com.hpog.android.utils;

import java.util.Comparator;

import com.pras.table.Record;

public class SortWineUtils {
	private static String NAME = "name";
	private static String CATEGORY = "category";
	private static String GRAPE = "grape";

	public enum RC implements Comparator<Record> {
		GRAPE_SORT {
			public int compare(Record o1, Record o2) {
				return o1.getData().get(GRAPE)
						.compareTo(o2.getData().get(GRAPE));
			}
		},
		NAME_SORT {
			public int compare(Record o1, Record o2) {
				return o1.getData().get(NAME).compareTo(o2.getData().get(NAME));
			}
		},
		CATEGORY_SORT {
			public int compare(Record o1, Record o2) {
				return o1.getData().get(CATEGORY)
						.compareTo(o2.getData().get(CATEGORY));
			}
		};
	}

	// public static void init(String name, String grape, String category) {
	// NAME = name;
	// GRAPE = grape;
	// CATEGORY = category;
	// }

	public static Comparator<Record> decending(final Comparator<Record> other) {
		return new Comparator<Record>() {
			public int compare(Record o1, Record o2) {
				return -1 * other.compare(o1, o2);
			}
		};
	}

	public static Comparator<Record> accending(final Comparator<Record> other) {
		return new Comparator<Record>() {
			public int compare(Record o1, Record o2) {
				return 1 * other.compare(o1, o2);
			}
		};
	}

	public static Comparator<Record> getComparator(final RC... multipleOptions) {
		return new Comparator<Record>() {
			public int compare(Record o1, Record o2) {
				for (RC option : multipleOptions) {
					int result = option.compare(o1, o2);
					if (result != 0) {
						return result;
					}
				}
				return 0;
			}
		};
	}
}
