package de.hsrm.mi.web.bratenbank.test.ueb03;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.web.bratenbank.bratboerse.BratenAngebotController;
import de.hsrm.mi.web.bratenbank.bratboerse.BratenDaten;

// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html

@SpringBootTest
@AutoConfigureMockMvc
class Web_Ueb03_A4_AngebotAnlegen {
    private final String TESTNAME = "Der coole Braten";
    private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
    private final String TESTHALTBARBISSTR = "2020-07-17";
	private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibärenbraten";
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
	@DisplayName("GET /angebot liefert HTML")
	void angebot_get() throws Exception {
		mockmvc.perform(
			get("/angebot")
			.contentType("text/html")
		).andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /angebot liefert HTML und legt 'angebote' Sessionattribut an")
	void angebot_get_sessionattr() throws Exception {
		mockmvc.perform(
			get("/angebot")
			.contentType("text/html")
		).andExpect(status().isOk())
		.andExpect(request().sessionAttribute("angebote", isA(List.class)));
	}

	@Test
	@DisplayName("GET /angebot/neu liefert HTML-Seite")
	void angebot_neu_get() throws Exception {
		mockmvc.perform(
			get("/angebot/neu")
			.contentType("text/html")
		).andExpect(status().isOk());
	}

	@Test
	@DisplayName("POST /angebot mit Formulardaten für BratenDaten-Attribute führt zu angebot/liste")
	void angebot_neu_post() throws Exception {
		// Eintrag anlegen
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
		.andExpect(content().string(containsString(TESTNAME)))
		.andExpect(content().string(containsString(TESTABHOLORT)))
		.andExpect(content().string(containsString(TESTBESCHREIBUNG)));
	}

	@Test
	@DisplayName("Mehrere POST auf /angebot zum Anlegen mehrerer Listeneinträge auf /angebot")
	void angebot_neu_post_many() throws Exception {
		String[][] w = {
			{"Albert", "Astweg 1, 52070 Aachen", "2022-01-11", "Ameisenbraten mit Ahornsirup", "25"},
			{"Berta", "Bommel Berg 25, 10115 Berlin", "2022-02-22", "Burgerbratling mit Birne", "100"},
			{"Charles", "Chemieplatz 456, 09117 Chemnitz", "2022-03-03", "Chemiebraten mit BASF", "75"},
			{"Dora-Viktualia ", "In den Blamüsen 1234, 40489 Düsseldorf bei Krefeld", "2022-04-04", "Dodohackbraten in Pfeffersoße", "0"},
		};

		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put("angebote", new ArrayList<BratenDaten>());

		// Session aufmachen
		mockmvc.perform(
			get("/angebot")
			.sessionAttrs(sessiondata)
		).andExpect(status().isOk());

		int nAnfang = ((List<?>)sessiondata.get("angebote")).size();

		// Einträge anlegen
		for (String[] a: w) {
			mockmvc.perform(
				post("/angebot")
				.sessionAttrs(sessiondata)
				.param("name", a[0])
				.param("abholort", a[1])
				.param("haltbarbis", a[2])
				.param("beschreibung", a[3])
				.param("vgrad", a[4])
				.param("sub","ok")
				.contentType(MediaType.MULTIPART_FORM_DATA)
			).andExpect(status().isOk())
			.andExpect(content().string(containsString(a[0])))
			.andExpect(content().string(containsString(a[1])))
			.andExpect(content().string(containsString(a[3])))
			// immer (ab 1. Runde) müssen alle drin sein, also zumindest #1
			.andExpect(content().string(containsString(w[0][0])))
			.andExpect(content().string(containsString(w[0][1])))
			.andExpect(content().string(containsString(w[0][3])))
			;
		}
		// es sind genau |w|-viele Einträge hinzugekommen.

		List<?> lst = (List<?>) sessiondata.get("angebote");
		assertEquals(nAnfang + w.length, lst.size());
	}
}
