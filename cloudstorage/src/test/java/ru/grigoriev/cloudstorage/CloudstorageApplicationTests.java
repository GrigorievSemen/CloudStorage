package ru.grigoriev.cloudstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.cloudstorage.controller.Auth;
import ru.grigoriev.cloudstorage.service.AuthService;

import javax.annotation.PostConstruct;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.grigoriev.cloudstorage.DataForTest.*;


@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SqlGroup({
        @Sql(value = "classpath:db/initDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/populateDB.sql", executionPhase = BEFORE_TEST_METHOD)
})
class CloudstorageApplicationTests {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private Jackson2ObjectMapperBuilder mapperBuilder;
    private ObjectMapper mapper;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new Auth(authService))
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();

        mapper = mapperBuilder.build();
    }

    @Test
    void testLoginReturnsOk() throws Exception {
        perform(post("/login")
                .content(mapper().writeValueAsString(AUTHENTICATION_GOOD_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLoginException() {
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(LOGIN_URL)
                        .content(mapper().writeValueAsString(AUTHENTICATION_WRONG_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Invalid name or password"));
    }

    @Test
    void logoutUserTest() throws Exception {
        perform(post(LOGOUT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AUTHENTICATION_GOOD_REQUEST)))
                .andExpect(status().is4xxClientError());
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected ObjectMapper mapper() {
        return mapper;
    }
}
