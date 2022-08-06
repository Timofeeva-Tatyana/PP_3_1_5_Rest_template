package ru.kata.PP_3_1_5_Rest_template;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.PP_3_1_5_Rest_template.model.User;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Pp315RestTemplateApplication {
	private static final String URL = "http://94.198.50.185:7081/api/users";
	static RestTemplate restTemplate = new RestTemplate();
	static HttpHeaders httpHeaders = new HttpHeaders();


	public static void main(String[] args) {
		Pp315RestTemplateApplication pp315 = new Pp315RestTemplateApplication();
		pp315.getCookies(httpHeaders);
		pp315.createUser();
		pp315.updateUser();
		pp315.deleteUser();
	}

	public void getCookies(HttpHeaders httpHeaders) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
		List<String> cookies = forEntity.getHeaders().get("Set-Cookie");
		httpHeaders.set("Cookie", String.join(";", Objects.requireNonNull(cookies)));
		System.out.println(forEntity);
	}

	private void createUser() {
		User user = new User(3L, "James", "Brown", (byte) 17);
		HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
		System.out.println(httpEntity);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
		System.out.println(responseEntity.getBody());

	}

	private void updateUser() {
		User user = new User(3L, "Thomas", "Shelby", (byte) 17);
		HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class);
		System.out.println(responseEntity.getBody());

	}

	private void deleteUser() {
		HttpEntity<User> httpEntity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/3",
				HttpMethod.DELETE, httpEntity, String.class);
		System.out.println(responseEntity.getBody());

	}

}
