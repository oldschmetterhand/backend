package eu.nampi.backend.integrationTest.controller;

import eu.nampi.backend.controller.EventController;
import eu.nampi.backend.repository.EventRepository;
import eu.nampi.backend.util.JenaUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test is based on SpringBoot's testing capablilties
 * using @WebMvcTest and @MockBean etc. With MockMVC can do some
 * kind of leightweight integration testing.
 */
@Disabled
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    JenaUtils jenaUtils;

    @MockBean
    Model model;

    @MockBean
    Lang jenaLang;

    @MockBean
    HttpServletResponse httpServletResponse;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("EventController's /events will call jenaUtils one time with expected objects.")
    @WithMockUser()
    void testGetEvents() throws Exception {
        //given
        given(eventRepository.getEvents()).willReturn(model);
        //when
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk());
        //then
        then(jenaUtils).should(times(1)).writeModel(model, jenaLang, httpServletResponse);
    }

}
