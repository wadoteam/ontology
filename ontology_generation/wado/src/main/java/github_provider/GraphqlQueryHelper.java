package github_provider;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class GraphqlQueryHelper {
	private static final String URL = "https://api.github.com/graphql";
	private static final String KEY = "20f4e2cedd64f7a733a970df59d5e1f0df7cb53d";


	private static final int NR_USERS = 1;
	private static final int NR_REPOS = 50;
	
	private static String cursor;
	private static boolean hasNextRepos = true;

	public static boolean hasNext() {
		return hasNextRepos;
	}

	public static List<Repository> getAllRepositories() {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response;

		HttpPost httpPost = new HttpPost(URL);
		httpPost.addHeader("Authorization", "Bearer " + KEY);
		httpPost.addHeader("Accept", "application/json");

		File file = new File("request.json");
		FileInputStream fis;

		try {
			fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();

			JSONObject jsonObjectRequest = new JSONObject();
			jsonObjectRequest.put("query", new String(data, "UTF-8"));

			JSONObject variables = new JSONObject();
			variables.put("nrUsers", NR_USERS);
			variables.put("nrRepositories", NR_REPOS);
			if (hasNextRepos) {
				variables.put("cursor", cursor);
			}
			jsonObjectRequest.put("variables", variables.toString());
			StringEntity entity = new StringEntity(jsonObjectRequest.toString());

			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			JSONTokener jsonTokener = new JSONTokener(
					new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			JSONObject jsonData = new JSONObject(jsonTokener);

			return JSONObjectToRepositoriesList(jsonData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private static List<Repository> JSONObjectToRepositoriesList(JSONObject object) throws Exception {
		
		if (object.get("data").equals("null")) {
			System.out.println(object);
			return new ArrayList<>();
		}

		List<Repository> repositoriesList = new ArrayList<Repository>();
		JSONObject jsonData = object.getJSONObject("data").getJSONObject("search");

		cursor = jsonData.getJSONObject("pageInfo").getString("endCursor");
		hasNextRepos = jsonData.getJSONObject("pageInfo").getBoolean("hasNextPage");

		JSONArray jsonUsersList = jsonData.getJSONArray("edges");

		for (int i = 0; i < jsonUsersList.length(); i++) {
			JSONArray jsonRepositriesList = jsonUsersList.getJSONObject(i).getJSONObject("node")
					.getJSONObject("repositories").getJSONArray("edges");
			for (int j = 0; j < jsonRepositriesList.length(); j++) {
				JSONObject jsonRepository = jsonRepositriesList.getJSONObject(j).getJSONObject("node");
				Repository newRepository = JSONObjectToRepository(jsonRepository);
				repositoriesList.add(newRepository);
			}
		}

		return repositoriesList;
	}

	private static Repository JSONObjectToRepository(JSONObject jsonRepository) throws Exception {
		
		String name = jsonRepository.getString("name");
		String url = jsonRepository.getString("url");
		String description = !jsonRepository.isNull("description") ? jsonRepository.getString("description") : "";
		String license = !jsonRepository.isNull("license") ? jsonRepository.getString("license") : "";

		String language = !jsonRepository.isNull("primaryLanguage")
				? jsonRepository.getJSONObject("primaryLanguage").getString("name") : "";
		int stars = jsonRepository.getJSONObject("stargazers").getInt("totalCount");
		
		JSONArray topicsJson = jsonRepository.getJSONObject("repositoryTopics").getJSONArray("edges");	
		List<String> topics = new ArrayList<>();
		for(int i=0; i<topicsJson.length(); i++) {
			String topic = topicsJson.getJSONObject(i).getJSONObject("node").getJSONObject("topic").getString("name");
			topics.add(topic);
		}
		
		JSONArray issuesJson = jsonRepository.getJSONObject("issues").getJSONArray("edges");	
				
		List<Issue> issues = new ArrayList<>();
		for(int i=0; i<issuesJson.length(); i++) {
			String title = issuesJson.getJSONObject(i).getJSONObject("node").getString("title");
			String text = issuesJson.getJSONObject(i).getJSONObject("node").getString("bodyText");
			boolean closed = issuesJson.getJSONObject(i).getJSONObject("node").getBoolean("closed");
			String createdAt = issuesJson.getJSONObject(i).getJSONObject("node").getString("createdAt");
			issues.add(new Issue(title, text,closed, createdAt));
		}

		Repository newRepository = new Repository(name, url, description, language, license, stars, topics, issues);
		return newRepository;
	}
}
