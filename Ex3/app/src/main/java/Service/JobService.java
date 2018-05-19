package Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JobService {

	private String url;

	public JobService() {
		this.url = "https://jobs.github.com/positions.json?";
	}

	private JSONArray getJSONFromUrl(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return new JSONArray(response.toString());
	}

	public List<Job> getAllJob() {
		List<Job> result = null;
		try {
			result = generateJob(getJSONFromUrl(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Can't find JSON");
		}
		return result;
	}

	public List<Job> searchCriteria(SearchCriteria searchCriteria) throws Exception {

		List<Job> result = null;
		String name = searchCriteria.getName();
		String location = searchCriteria.getLocation();
		boolean isFullTime = searchCriteria.isFullTime();
		String search = this.url + "description=" + name + "&location=" + location + "&full_time="
				+ (isFullTime ? "true" : "false");
		try {
			result = generateJob(getJSONFromUrl(search));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Can't find JSON");
		}
		return result;
	}

	private List<Job> generateJob(JSONArray jsonArray) throws JSONException {
		List<Job> result = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jobJson = jsonArray.getJSONObject(i);
			result.add(convertJob(jobJson));
		}
		return result;
	}

	private Job convertJob(JSONObject jobJson) throws JSONException {
		String id = jobJson.getString("id");
		String created_at = jobJson.getString("created_at");
		String title = jobJson.getString("title");
		String location = jobJson.getString("location");
		String type = jobJson.getString("type");
		String description = jobJson.getString("description");
		String how_to_apply = jobJson.getString("how_to_apply");
		String company = jobJson.getString("company");
		String company_url = jobJson.get("company_url").toString();
		String company_logo = jobJson.get("company_logo").toString();
		String url = jobJson.get("url").toString();
		Job job = new Job(id, created_at, title, location, type, description, how_to_apply, company, company_url,
				company_logo, url);
		return job;
	}

	public List<Job> searchAnything(String anything) throws Exception {
		String search = this.url + "search=" + anything;
		List<Job> result = null;
		try {
			result = generateJob(getJSONFromUrl(search));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Can't find JSON");
		}
		return result;
	}

}
