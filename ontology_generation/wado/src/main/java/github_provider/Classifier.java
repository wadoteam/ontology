package github_provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classifier {

	public static Map<String, Map<String, List<Repository>>> classifyRepositories(List<String> classes) {
		Map<String, Map<String, List<Repository>>> classification = initMap(classes);
		
		while (GraphqlQueryHelper.hasNext()) {
			List<Repository> list = GraphqlQueryHelper.getAllRepositories();
			
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < classes.size(); j++) {
					String res = classify(list.get(i), classes.get(j));
					if (res != null) {
						classification.get(classes.get(j)).get(res).add(list.get(i));
					}
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
			if (repository.getName().toLowerCase().matches("(.*[^a-zA-Z])?"+words[i]+"([^a-zA-Z].*)?")) {
				result[0]++;
			} 
			else if (repository.getDescription().toLowerCase().matches("(.*[^a-zA-Z])?"+words[i]+"([^a-zA-Z].*)?")) {
				result[2]++;
			} else {
				List<String> topics = repository.getTopics();
				for (int j = 0; j < topics.size(); j++) {
					if (topics.get(j).matches("(.*[^a-zA-Z])?"+words[i]+"([^a-zA-Z].*)?")) {
						result[1]++;
						break;
					}
				}
			}
		}
		if (result[0] >= words.length) {
			return "name";
		} else if (result[1] >= words.length) {
			return "topics";
		} else if (result[2] >= words.length) {
			return "description";
		} else
			return null;

	}
}
