package de.hsrm.mi.web.bratenbank.tests.ueb05;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;

// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html

@SpringBootTest
@AutoConfigureMockMvc
class Web_Ueb05_A5_BenutzerRegistrierenWeb {
	final String TESTLOGINNAME = "hopsi";
    final String TESTPASSWORT = "abcxyz";
	final String TESTVOLLNAME = "Hopsi van Dackelen";
	final String REGVIEWNAME = "benutzerui/benutzerregistrierung";

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private BenutzerRepository benutzerrepo;

	@Test
	void vorabcheck() {
		assertThat(benutzerrepo).isNotNull();
		assertThat(mockmvc).isNotNull();
	}

	@Test
	@DisplayName("GET /benutzer liefert HTML")
	void angebot_get() throws Exception {
		mockmvc.perform(
			get("/benutzer")
			.contentType("text/html")
		).andExpect(status().isOk())
		.andExpect(view().name(REGVIEWNAME));
	}

	@Test
	@DisplayName("POST /benutzer mit ok Formulardaten legt Benutzer an")
	void benutzer_neu_post_ok() throws Exception {
		// Eintrag anlegen

		benutzerrepo.deleteAll();

		mockmvc.perform(
			post("/benutzer")
			.param("loginname", TESTLOGINNAME)
			.param("passwort", TESTPASSWORT)
			.param("vollname", TESTVOLLNAME)
			.param("nutzungsbedingungenok", "on")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(model().hasNoErrors());

		Benutzer b = benutzerrepo.findByLoginname(TESTLOGINNAME);
		assertThat(b).isNotNull();
		assertThat(b.getLoginname()).isEqualTo(TESTLOGINNAME);
		assertThat(b.getPasswort()).isEqualTo(TESTPASSWORT);
		assertThat(b.getVollname()).isEqualTo(TESTVOLLNAME);
		assertThat(b.isNutzungsbedingungenok()).isTrue();
	}

	@Test
	@DisplayName("POST /benutzer mit falschen Formulardaten")
	void benutzer_neu_post_falsch() throws Exception {
		final String ZUKURZESPASSWORT = "xy";

		benutzerrepo.deleteAll();

		mockmvc.perform(
			post("/benutzer")
			.param("loginname", "")
			.param("passwort", ZUKURZESPASSWORT)
			.param("vollname", "")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name(REGVIEWNAME))
		.andExpect(model().attributeHasFieldErrors("benutzer", "loginname"))
		.andExpect(model().attributeHasFieldErrors("benutzer", "passwort"))
		.andExpect(model().attributeHasFieldErrors("benutzer", "vollname"));
	}

	@Test
	@DisplayName("POST /benutzer mit doppeltem Loginnamen geht nicht")
	void benutzer_neu_post_name_doppelt() throws Exception {
		// Eintrag anlegen

		benutzerrepo.deleteAll();

		mockmvc.perform(
			post("/benutzer")
			.param("loginname", TESTLOGINNAME)
			.param("passwort", TESTPASSWORT)
			.param("vollname", TESTVOLLNAME)
			.param("nutzungsbedingungenok", "on")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(model().hasNoErrors());

		assertThat(benutzerrepo.count()).isEqualTo(1);

		mockmvc.perform(
			post("/benutzer")
			.param("loginname", TESTLOGINNAME)
			.param("passwort", TESTPASSWORT)
			.param("vollname", TESTVOLLNAME)
			.param("nutzungsbedingungenok", "on")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name(REGVIEWNAME));
	}


}
