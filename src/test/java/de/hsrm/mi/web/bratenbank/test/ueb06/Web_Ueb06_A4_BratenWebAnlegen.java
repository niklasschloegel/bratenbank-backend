package de.hsrm.mi.web.bratenbank.test.ueb06;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.web.bratenbank.benutzer.Benutzer;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerRepository;
import de.hsrm.mi.web.bratenbank.benutzer.BenutzerService;
import de.hsrm.mi.web.bratenbank.bratrepo.Braten;
import de.hsrm.mi.web.bratenbank.bratrepo.BratenRepository;
import de.hsrm.mi.web.bratenbank.bratservice.BratenService;
import de.hsrm.mi.web.bratenbank.bratui.BratenWebController;

// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class Web_Ueb06_A4_BratenWebAnlegen {
	public static final String LOGGEDINUSERNAMEATTR = "loggedinusername";

	private final String TESTLOGINNAME = "willibert";
	private final String TESTLOGINPASSWORT = "dingdong123";
	private final String TESTLOGINVOLLNAME = "Herr Bert Willi";

	private final String TESTABHOLORT = "In der Ecke 17, 99441 Vollradisroda";
	private final String TESTHALTBARBISSTR = "2020-07-17";
	private final String TESTBESCHREIBUNG = "Leckerer vegetarischer Gummibaerenbraten";
	private final String TESTVEGETARIZITAETSTR = "25";

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private BratenWebController bratenwebctrlr;

	@Autowired
	BenutzerService benutzerservice;

	@Autowired
	BenutzerRepository benutzerrepo;

	@Autowired
	BratenService bratenservice;

	@Autowired
	BratenRepository bratenrepo;


	@Test
	void vorabcheck() {
		assertThat(bratenwebctrlr).isNotNull();
		assertThat(mockmvc).isNotNull();
	}

	@BeforeEach
	public void init() {
		benutzerrepo.deleteAll();
		bratenrepo.deleteAll();

		Benutzer willi = new Benutzer();
		willi.setLoginname(TESTLOGINNAME);
		willi.setPasswort(TESTLOGINPASSWORT);
		willi.setVollname(TESTLOGINVOLLNAME);
		benutzerservice.registriereBenutzer(willi);
	}

	@Test
	@DisplayName("GET /braten/angebot liefert HTML")
	void angebot_get() throws Exception {
		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put(LOGGEDINUSERNAMEATTR, TESTLOGINNAME);

		mockmvc.perform(
			get("/braten/angebot")
			.sessionAttrs(sessiondata)
			.contentType("text/html")
			).andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /braten/angebot/neu liefert HTML-Seite")
	void angebot_neu_get() throws Exception {
		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put(LOGGEDINUSERNAMEATTR, TESTLOGINNAME);
		mockmvc.perform(
			get("/braten/angebot/neu")
			.sessionAttrs(sessiondata)
			.contentType("text/html")
			).andExpect(status().isOk());

	}

	@Test
	@DisplayName("POST /angebot mit Formulardaten für BratenDaten-Attribute führt zu angebot/liste")
	void angebot_neu_post() throws Exception {
		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put(LOGGEDINUSERNAMEATTR, TESTLOGINNAME);

		// Eintrag anlegen
		mockmvc.perform(
			post("/braten/angebot/neu")
			.sessionAttrs(sessiondata)
			.param("abholort", TESTABHOLORT)
			.param("haltbarbis", TESTHALTBARBISSTR)
			.param("beschreibung", TESTBESCHREIBUNG)
			.param("vgrad", TESTVEGETARIZITAETSTR)
			.param("sub", "ok").contentType(MediaType.MULTIPART_FORM_DATA)
			).andReturn();

		assertThat(bratenrepo.count()).isEqualTo(1);
	}

	@Test
	@DisplayName("Mehrere POST auf /angebot zum Anlegen mehrerer Listeneinträge auf /angebot")
	void angebot_neu_post_many() throws Exception {
		String[][] w = { { "Albert", "Astweg 1, 52070 Aachen", "2022-01-11", "Ameisenbraten mit Ahornsirup", "25" },
				{ "Berta", "Bommel Berg 25, 10115 Berlin", "2022-02-22", "Burgerbratling mit Birne", "100" },
				{ "Charles", "Chemieplatz 456, 09117 Chemnitz", "2022-03-03", "Chemiebraten mit BASF", "75" },
				{ "Dora-Viktualia ", "In den Blamuesen 1234, 40489 Duesseldorf bei Krefeld", "2022-04-04", "Dodohackbraten in Pfeffersosse", "0" }, };

		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put(LOGGEDINUSERNAMEATTR, TESTLOGINNAME);
				
		// Einträge anlegen
		for (String[] a : w) {
			mockmvc.perform(
				post("/braten/angebot/neu")
				.sessionAttrs(sessiondata)
				.param("abholort", a[1])
				.param("haltbarbis", a[2])
				.param("beschreibung", a[3])
				.param("vgrad", a[4])
				.param("sub", "ok")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andReturn();
		}
		// es sind genau |w|-viele Einträge hinzugekommen.

		assertThat(bratenrepo.count()).isEqualTo(w.length);
		assertThat(bratenservice.alleBraten().size()).isEqualTo(bratenrepo.count());
		assertThat(bratenrepo.findByBeschreibungContainsIgnoringCase("mit").size()).isEqualTo(3);
		assertThat(bratenrepo.findByBeschreibungContainsIgnoringCase("basf").size()).isEqualTo(1);
		assertThat(bratenrepo.findByBeschreibungContainsIgnoringCase("abcdefghijkl").size()).isEqualTo(0);
	}


	@Test
	@DisplayName("GET /braten/angebot/.../del - Eintrag löschen")
	void angebot_del() throws Exception {
		String[][] w = { { "Albert", "Astweg 1, 52070 Aachen", "2022-01-11", "Ameisenbraten mit Ahornsirup", "25" },
				{ "Berta", "Bommel Berg 25, 10115 Berlin", "2022-02-22", "Burgerbratling mit Birne", "100" },
				{ "Charles", "Chemieplatz 456, 09117 Chemnitz", "2022-03-03", "Chemiebraten mit BASF", "75" },
				{ "Dora-Viktualia ", "In den Blamuesen 1234, 40489 Duesseldorf bei Krefeld", "2022-04-04", "Dodohackbraten in Pfeffersosse", "0" }, };

		Map<String, Object> sessiondata = new HashMap<>();
		sessiondata.put(LOGGEDINUSERNAMEATTR, TESTLOGINNAME);
				
		// Einträge anlegen
		for (String[] a : w) {
			mockmvc.perform(
				post("/braten/angebot/neu")
				.sessionAttrs(sessiondata)
				.param("abholort", a[1])
				.param("haltbarbis", a[2])
				.param("beschreibung", a[3])
				.param("vgrad", a[4])
				.param("sub", "ok")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andReturn();
		}
		// es sind genau |w|-viele Einträge hinzugekommen.

		assertThat(bratenrepo.count()).isEqualTo(w.length);

		List<Braten> bratenliste = bratenrepo.findAll();
		int id2 = bratenliste.get(2).getId();

		Optional<Braten> bo_vor = bratenrepo.findById(id2);
		assertThat(bo_vor.isPresent()).isTrue();

		mockmvc.perform(
			get("/braten/angebot/"+id2+"/del")
			.sessionAttrs(sessiondata)
			.contentType("text/html")
			).andReturn();
		
		Optional<Braten> bo_nach = bratenrepo.findById(id2);
		assertThat(bo_nach.isPresent()).isFalse();

		
		int id0 = bratenliste.get(0).getId();

		Optional<Braten> bo0_vor = bratenrepo.findById(id0);
		assertThat(bo0_vor.isPresent()).isTrue();

		mockmvc.perform(
			get("/braten/angebot/"+id0+"/del")
			.sessionAttrs(sessiondata)
			.contentType("text/html")
			).andReturn();
		
		Optional<Braten> bo0_nach = bratenrepo.findById(id2);
		assertThat(bo0_nach.isPresent()).isFalse();

	}

}
