package eu.nampi.backend.unittests.controller;

import eu.nampi.backend.controller.PersonController;
import eu.nampi.backend.repository.PersonRepository;
import eu.nampi.backend.util.JenaUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * Test is based on standard mockito testing capabilities
 * and does not use the built in spring-boot testing features
 * like @WebMvcTest or @MockBean.
 * */
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    JenaUtils jenaUtils;

    @InjectMocks
    PersonController personController;

    @BeforeEach
    void setUp(){
        doNothing().when(jenaUtils).writeModel(any(), any(), any());
        reset(personRepository, jenaUtils);
    }

    @Test
    @DisplayName("Given personController /getPerson is called with random UUID, it should call the personRepository exactly once with expected UUID.")
    void testGetPerson() {
        //given
        UUID randomUuid = UUID.randomUUID();
        given(personRepository.getPerson(randomUuid)).willReturn(null);
        //when
        personController.getPerson(null, randomUuid, null);
        //then
        then(personRepository).should(times(1)).getPerson(randomUuid);
    }

    @Test
    @DisplayName("getPersons ahould call the jenaUtils exactly once with given mock objects.")
    void testGetPersons(){
        //given
        Model jenaModelMock = Mockito.mock(Model.class);
        Lang jenaLangMock = Mockito.mock(Lang.class);
        HttpServletResponse httpServletResponseMock = Mockito.mock(HttpServletResponse.class);
        given(personRepository.getPersons()).willReturn(jenaModelMock);
        //when
        personController.getPersons(jenaLangMock, httpServletResponseMock);
        //then
        then(jenaUtils).should(times(1)).writeModel(jenaModelMock,jenaLangMock, httpServletResponseMock);
    }
}