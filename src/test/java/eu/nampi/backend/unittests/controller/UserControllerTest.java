package eu.nampi.backend.unittests.controller;

import eu.nampi.backend.controller.UserController;
import eu.nampi.backend.model.User;
import eu.nampi.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp(){
        reset(userRepository);
    }

    @Test
    @DisplayName("currentUser will return expected User and therefore call the userRepo exactly one time.")
    void testCurrentUser(){
        //given
        User user = Mockito.mock(User.class);
        given(userRepository.getCurrentUser()).willReturn(user);
        //when
        User foundUser =  userController.currentUser();
        //then
        then(userRepository).should(times(1)).getCurrentUser();
        Assertions.assertEquals(user, foundUser);
    }

}
