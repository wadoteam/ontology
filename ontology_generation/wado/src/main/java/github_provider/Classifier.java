package github_provider;

import connection_tdb.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classifier {
	private static final int NR_MATCHES = 2;
	private static boolean hasNextPrivate = true;

	public static Map<String, Map<String, List<Repository>>> classifyRepositories(Map<String, String> classes) {
		Map<String, Map<String, List<Repository>>> classification = initMap(Utils.getValuesArrayFromMap(classes));

		List<Repository> list = GraphqlQueryHelper.getAllRepositories();
		hasNextPrivate = GraphqlQueryHelper.hasNext();

		List<String> formatedClassesNames = Utils.getKeysArrayFromMap(classes);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < formatedClassesNames.size(); j++) {
				String res = classify(list.get(i), formatedClassesNames.get(j).toLowerCase());
				if (res != null) {
					classification.get(classes.get(formatedClassesNames.get(j))).get(res).add(list.get(i));
				}
			}
		}
		return classification;

	}

	private static Map<String, Map<String, List<Repository>>> initMap(List<String> classes) {
		Map<String, Map<String, List<Repository>>> classification = new HashMap<>();
		for (int i = 0; i < classes.size(); i++) {
			Map<String, List<Repository>> priorityMap = new HashMap<>();
			priorityMap.put("name", new ArrayList<Repository>());
			priorityMap.put("topics", new ArrayList<Repository>());
			priorityMap.put("description", new ArrayList<Repository>());

			classification.put(classes.get(i), priorityMap);
		}
		return classification;
	}

	private static String classify(Repository repository, String cls) {
		String[] words = cls.split(" ");
		int[] result = { 0, 0, 0 };

		for (int i = 0; i < words.length; i++) {
			if (repository.getName().toLowerCase().matches("(.*[^a-zA-Z])?" + words[i] + "([^a-zA-Z].*)?")) {
				result[0]++;
			}
			if (repository.getDescription().toLowerCase().matches("(.*[^a-zA-Z])?" + words[i] + "([^a-zA-Z].*)?")) {
				result[2]++;
			}
			List<String> topics = repository.getTopics();
			for (int j = 0; j < topics.size(); j++) {
				if (topics.get(j).matches("(.*[^a-zA-Z])?" + words[i] + "([^a-zA-Z].*)?")) {
					result[1]++;
					break;
				}
			}
		}

		if (result[0] >= Math.min(NR_MATCHES, words.length)) {
			return "name";
		} else if (result[1] >= Math.min(NR_MATCHES, words.length)) {
			return "topics";
		} else if (result[2] >= Math.min(NR_MATCHES, words.length)) {
			return "description";
		} else
			return null;
	}

	public static boolean hasNext() {
		return hasNextPrivate;
	}
}
