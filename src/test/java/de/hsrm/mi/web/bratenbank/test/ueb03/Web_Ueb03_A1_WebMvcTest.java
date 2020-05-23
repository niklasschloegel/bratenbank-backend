package de.hsrm.mi.web.bratenbank.test.ueb03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import de.hsrm.mi.web.bratenbank.login.LoginController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html

@SpringBootTest
@AutoConfigureMockMvc
class Web_Ueb03_A1_WebMvcTest {
	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private LoginController logincontroller;

	@Test
	void vorabcheck() {
		assertThat(logincontroller).isNotNull();
		assertThat(mockmvc).isNotNull();
	}

	@Test
	@DisplayName("GET /login liefert HTML-Seite, die username und password abfragt")
	void login_get_UsernamePassword() throws Exception {
		mockmvc.perform(
			get("/login")
			.contentType("text/html")
		).andExpect(status().isOk())
		.andExpect(content().string(containsString("username")))
		.andExpect(content().string(containsString("password")));
	}

	@Test
	@DisplayName("POST /login mit username und falschem password liefert Passwort-Hilfetext")
	void login_post_falsch_UsernamePassword() throws Exception {
		mockmvc.perform(
			post("/login")
			.param("username", "abc")
			.param("password", "abc")
		).andExpect(status().isOk())
		.andExpect(content().string(containsString("abc3")));
	}

}
