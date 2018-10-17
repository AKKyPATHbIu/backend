package embasa.security;

import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationProviderTest {

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserService userService;

    @InjectMocks
    CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();

    private final String userName = "user";
    private final String password = "S3CRET";

    private final String badUserName = "user1";
    private final String badPassword = "S3CRET1";

    @Test
    public void authenticate() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(badUserName);
        when(authentication.getCredentials()).thenReturn(badPassword);
        when(userService.findByLogin(badUserName)).thenReturn(null);

        checkException(authentication, String.format("Користувача %s не знайдено в базі!", badUserName));

        User dUser = new User();
        dUser.setEnabled(false);

        when(userService.findByLogin(badUserName)).thenReturn(dUser);
        checkException(authentication, String.format("Користувача %s заблоковано в системі!", badUserName));

        dUser.setEnabled(true);
        dUser.setPassword(badPassword);
        when(encoder.matches(badPassword, dUser.getPassword())).thenReturn(false);
        checkException(authentication, String.format("Не вірний пароль для користувача %s!", badUserName));

        User user = new User();
        user.setId(1111L);
        user.setUsername(userName);
        user.setPassword(password);
        user.setEnabled(true);

        when(authentication.getName()).thenReturn(userName);
        when(authentication.getCredentials()).thenReturn(password);
        when(userService.findByLogin(userName)).thenReturn(user);
        when(encoder.matches(password, user.getPassword())).thenReturn(true);

        Authentication token = authProvider.authenticate(authentication);
        assertEquals(user.getId(), token.getPrincipal());
        Collection<? extends GrantedAuthority> auths = token.getAuthorities();
        assertFalse(auths.isEmpty());
    }

    private void checkException(Authentication authentication, String expectedStr) {
        try {
            authProvider.authenticate(authentication);
            fail();
        } catch (BadCredentialsException ex) {
            assertEquals(expectedStr, ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void supports() {
        assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
        assertFalse(authProvider.supports(Object.class));
    }
}