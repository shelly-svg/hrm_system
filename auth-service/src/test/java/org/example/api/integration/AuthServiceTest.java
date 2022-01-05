package org.example.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.constant.ApiConstants;
import org.example.api.constant.JwtProperties;
import org.example.api.dto.AuthenticationDTO;
import org.example.api.dto.AuthorizationDTO;
import org.example.api.dto.ErrorDTO;
import org.example.api.entity.Role;
import org.example.api.entity.Status;
import org.example.api.entity.Token;
import org.example.api.entity.User;
import org.example.api.repository.UserRepository;
import org.example.api.security.JwtTokenResolver;
import org.example.api.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"embedded.containers.enabled=true",
                                                                            "embedded.mysql.enabled=true"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-containers-test.properties")
@Transactional
class AuthServiceTest {

    private static final String VALID_USERNAME = "validUsername";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String INCORRECT_PASSWORD = "incorrectPassword";
    private static final String INVALID_USERNAME = "inv";
    private static final String INVALID_PASSWORD = "inv";
    private static final String INVALID_TOKEN = "invalidToken";

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(12);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenResolver jwtTokenResolver;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void shouldSuccessfullyAuthUser_whenPostRequestToAuthenticateWithValidCredentials() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationDTO))).andExpect(status().isOk()).andReturn();
        String token = mvcResult.getResponse().getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);

        assertDoesNotThrow(
                () -> jwtTokenResolver.resolveAuthorization(mvcResult.getResponse(), token, userToAuth.getRole()));
    }

    private User createUser() {
        User user = new User();
        user.setUsername(VALID_USERNAME);
        user.setPassword(PASSWORD_ENCODER.encode(AuthServiceTest.VALID_PASSWORD));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        return user;
    }

    @Test
    void shouldReturnUnauthorizedStatus_whenPostRequestToAuthenticateAndThereIsNoUserWithSuchUsernameInUsersHolder()
            throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(VALID_USERNAME, VALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.INCORRECT_USERNAME_OR_PWD_MESSAGE),
                errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnUnauthorizedStatus_whenPostRequestToAuthenticateWithIncorrectPassword() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), INCORRECT_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.INCORRECT_USERNAME_OR_PWD_MESSAGE),
                errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthenticateWithInvalidUsername() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(INVALID_USERNAME, VALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHENTICATION_DTO_USERNAME_FIELD,
                ApiConstants.USERNAME_INVALIDITY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthenticateWithNullUsername() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(null, VALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHENTICATION_DTO_USERNAME_FIELD,
                ApiConstants.USERNAME_IS_MANDATORY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthenticateWithInvalidPassword() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(VALID_USERNAME, INVALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHENTICATION_DTO_PASSWORD_FIELD,
                ApiConstants.PASSWORD_INVALIDITY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthenticateWithNullPassword() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(VALID_USERNAME, null);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHENTICATION_DTO_PASSWORD_FIELD,
                ApiConstants.PASSWORD_IS_MANDATORY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnForbiddenStatus_whenPostRequestToAuthenticateWithValidCredentialsButThisUserIsBlocked()
            throws Exception {
        User userToAuth = createUser();
        userToAuth.setStatus(Status.BLOCKED);
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.USER_IS_BLOCKED_MESSAGE),
                errorDTO.getUserInfo());
    }

    @Test
    void shouldAddRequestedTokenToTheBlacklistAndReturnNewToken_whenPostRequestToAuthorizeWithValidTokenAndRole()
            throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MockHttpServletResponse authenticationResponse = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String token = authenticationResponse.getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(token, userToAuth.getRole());

        // the pause is required because jwt token resolver uses the current time for creating a token, and if there
        // will be too small delay he will create the same tokens for different requests so test won`t pass
        TimeUnit.SECONDS.sleep(1);

        MockHttpServletResponse authorizationResponse = mockMvc.perform(post("/api/v1/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizationDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String newToken = authorizationResponse.getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);

        assertTrue(tokenService.isTokenBlacklisted(token));
        assertFalse(tokenService.isTokenBlacklisted(newToken));
    }

    @Test
    void shouldReturnUnauthorizedStatus_whenPostRequestToAuthorizeWithBlacklistedToken() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult authenticationResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String token = authenticationResult.getResponse().getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);
        tokenService.addToBlacklist(
                new Token(token, LocalDateTime.now().plusMinutes(jwtProperties.getValidityInMinutes())));
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(token, userToAuth.getRole());

        mockMvc.perform(post("/api/v1/authorize").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorizationDTO))).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnForbiddenStatus_whenPostRequestToAuthorizeWithIncorrectRole() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult authenticationResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String token = authenticationResult.getResponse().getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(token, Role.ADMIN);

        MvcResult authorizationResult = mockMvc.perform(post("/api/v1/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizationDTO)))
                .andExpect(status().isForbidden())
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(authorizationResult.getResponse().getContentAsString(),
                ErrorDTO.class);

        assertEquals(
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.USER_DOES_NOT_HAVE_ACCESS_MESSAGE),
                errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnForbiddenStatus_whenPostRequestToAuthorizeWithInvalidToken() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(INVALID_TOKEN, userToAuth.getRole());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizationDTO)))
                .andExpect(status().isForbidden())
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.AUTH_TOKEN_IS_MALFORMED_MESSAGE),
                errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthorizeWithNullToken() throws Exception {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(null, Role.ADMIN);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizationDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHORIZATION_DTO_TOKEN_FIELD,
                ApiConstants.TOKEN_IS_MANDATORY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnBadRequestStatus_whenPostRequestToAuthorizeWithNullRole() throws Exception {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(INVALID_TOKEN, null);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizationDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(Collections.singletonMap(ApiConstants.AUTHORIZATION_DTO_ROLE_FIELD,
                ApiConstants.ROLE_IS_MANDATORY_MESSAGE), errorDTO.getUserInfo());
    }

    @Test
    void shouldReturnOkStatus_whenPostRequestToLogout() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult authenticationResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String token = authenticationResult.getResponse().getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);

        mockMvc.perform(post("/api/v1/logout").header(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME, token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedStatus_whenPostRequestToLogoutAndTokenIsBlacklisted() throws Exception {
        User userToAuth = createUser();
        userRepository.saveAndFlush(userToAuth);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userToAuth.getUsername(), VALID_PASSWORD);

        MvcResult authenticationResult = mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String token = authenticationResult.getResponse().getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);
        tokenService.addToBlacklist(
                new Token(token, LocalDateTime.now().plusMinutes(jwtProperties.getValidityInMinutes())));

        mockMvc.perform(post("/api/v1/logout").header(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME, token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnForbiddenStatus_whenPostRequestToLogoutAndTokenIsNull() throws Exception {
        mockMvc.perform(post("/api/v1/logout"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnForbiddenStatus_whenPostRequestToLogoutAndTokenIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/logout")
                        .header(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME, INVALID_TOKEN))
                .andExpect(status().isForbidden());
    }

}
