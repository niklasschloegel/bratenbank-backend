package de.hsrm.mi.web.bratenbank.tests.ueb04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.web.bratenbank.bratboerse.BratenAngebotController;

@SpringBootTest
@AutoConfigureMockMvc
class Web_Ueb04_A4_I18n_und_L10n_OPTIONAL {
	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private BratenAngebotController bratenangebotctrlr;

	@Test
	void vorabcheck() {
		assertThat(bratenangebotctrlr).isNotNull();
		assertThat(mockmvc).isNotNull();
	}

	@Test
	@DisplayName("GET /angebot/neu mit Locale.GERMAN")
	void angebot_neu_deutsch() throws Exception {
		mockmvc.perform(
			get("/angebot/neu")
			.locale(Locale.GERMAN)  // beide Varianten ok
			.param("sprache","de")
		).andExpect(status().isOk())
		.andExpect(content().string(containsString("Anbieter")))
		.andExpect(content().string(containsString("Abholort")))
		.andExpect(content().string(containsString("Beschreibung")));
	}

	@Test
	@DisplayName("GET /angebot/neu mit Locale.ENGLISH")
	void angebot_neu_englisch() throws Exception {
		mockmvc.perform(
			get("/angebot/neu")
			.locale(Locale.ENGLISH)  // beide Varianten ok
			.param("sprache","en")
		).andExpect(status().isOk())
		.andExpect(content().string(containsString("Provider")))
		.andExpect(content().string(containsString("Pickup")))
		.andExpect(content().string(containsString("Description")));
	}

}
