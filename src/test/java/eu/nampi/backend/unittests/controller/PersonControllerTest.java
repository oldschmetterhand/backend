package eu.nampi.backend.unittests.controller;

import eu.nampi.backend.controller.PersonController;
import eu.nampi.backend.repository.PersonRepository;
import eu.nampi.backend.util.JenaUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    JenaUtils jenaUtils;

    @InjectMocks
    PersonController personController;


    @Test
    @DisplayName("") //TODO add BDD sentence
    void testGetPerson() {

        //given
        UUID randomUuid = UUID.randomUUID();
        given(personRepository.getPerson(randomUuid)).willReturn(null);

        //when
        personController.getPerson(null, randomUuid, null);

        //then
        then(personRepository).should(times(1)).getPerson(randomUuid);

    }


}