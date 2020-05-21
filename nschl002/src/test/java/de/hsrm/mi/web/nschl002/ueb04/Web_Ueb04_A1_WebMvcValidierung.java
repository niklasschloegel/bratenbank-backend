package de.hsrm.mi.web.bratenbank.test.ueb04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.web.bratenbank.bratboerse.BratenAngebotController;

// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html

@SpringBootTest
@AutoConfigureMockMvc
class Web_Ueb04_A1_WebMvcValidierung {
    private final String TESTNAME = "Der coole Braten";
    private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
    private final String TESTHALTBARBISSTR = "2022-07-17";
	private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibaerenbraten";
	private final String TESTVEGETARIZITAETSTR = "25";

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
	@DisplayName("POST /angebot mit Validierung: Eintrag ok")
	void angebot_neu_post_validate_neueintrag_ok() throws Exception {
		mockmvc.perform(
			post("/angebot")
			.param("name", TESTNAME)
			.param("abholort", TESTABHOLORT)
			.param("haltbarbis", TESTHALTBARBISSTR)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub","ok")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name("angebote/liste"))
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(TESTABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}



	@Test
	@DisplayName("POST /angebot mit Validierung: Haltbarkeit in Vergangenheit")
	void angebot_neu_post_validate_haltbarbis_vergangenheit() throws Exception {
		final String FALSCHHALTBARBIS = "2010-01-01";
		mockmvc.perform(
			post("/angebot")
			.param("name", TESTNAME)
			.param("abholort", TESTABHOLORT)
			.param("haltbarbis", FALSCHHALTBARBIS)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub","ok")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name("angebote/bearbeiten"))
		.andExpect(model().attributeHasFieldErrors("angebotform", "haltbarbis"))
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(TESTABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}


	@Test
	@DisplayName("POST /angebot mit Validierung: Abholort ohne Hausnr")
	void angebot_neu_post_validate_abholort_ohne_hausnr() throws Exception {
		final String FALSCHABHOLORT = "Am Winkelbusch, 58342 Jockelingen";
		mockmvc.perform(
			post("/angebot")
			.param("name", TESTNAME)
			.param("abholort", FALSCHABHOLORT)
			.param("haltbarbis", TESTHALTBARBISSTR)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub","ok")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name("angebote/bearbeiten"))
		.andExpect(model().attributeHasFieldErrors("angebotform", "abholort"))
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(FALSCHABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}

	@Test
	@DisplayName("POST /angebot mit Validierung: Abholort ohne PLZ")
	void angebot_neu_post_validate_abholort_ohne_plz() throws Exception {
		final String FALSCHABHOLORT = "Am Winkelbusch 17, Burg Jockelingen";
		mockmvc.perform(
			post("/angebot")
			.param("name", TESTNAME)
			.param("abholort", FALSCHABHOLORT)
			.param("haltbarbis", TESTHALTBARBISSTR)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub","ok")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name("angebote/bearbeiten"))
		.andExpect(model().attributeHasFieldErrors("angebotform", "abholort"))
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(FALSCHABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}

	@Test
	@DisplayName("POST /angebot mit Validierung: Abholort ohne Komma")
	void angebot_neu_post_validate_abholort_ohne_komma() throws Exception {
		final String FALSCHABHOLORT = "Am Winkelbusch 17  47827 Jockelingen";
		mockmvc.perform(
			post("/angebot")
			.param("name", TESTNAME)
			.param("abholort", FALSCHABHOLORT)
			.param("haltbarbis", TESTHALTBARBISSTR)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub","ok")
			.contentType(MediaType.MULTIPART_FORM_DATA)
		).andExpect(status().isOk())
		.andExpect(view().name("angebote/bearbeiten"))
		.andExpect(model().attributeHasFieldErrors("angebotform", "abholort"))
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(FALSCHABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}


}
