package eu.nampi.backend.unittests.controller;

import eu.nampi.backend.controller.EventController;
import eu.nampi.backend.repository.EventRepository;
import eu.nampi.backend.util.JenaUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("EventController Unittests")
@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    JenaUtils jenaUtils;

    @Mock
    Model model;

    @Mock
    Lang jenaLang;

    @Mock
    HttpServletResponse httpServletResponse;

    @InjectMocks
    EventController eventController;

    @BeforeEach
    void setUp(){
        reset(eventRepository, jenaUtils, model, jenaLang, httpServletResponse);
    }

    @Test
    @DisplayName("getEvents should lead to jenaUtils try to write given model into jena.")
    void testGetEvents() {
        //given
        given(eventRepository.getEvents()).willReturn(model);
        // when
        eventController.getEvents(jenaLang, httpServletResponse);
        //then
        then(jenaUtils).should(times(1)).writeModel(model, jenaLang, httpServletResponse);
    }

}
