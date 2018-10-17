package embasa.security;

import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    @Qualifier("secureDBUserService")
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = null;
        try {
            user = userService.findByLogin(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (user == null) {
            throw new BadCredentialsException(String.format("Користувача %s не знайдено в базі!", name));
        }

        if (!user.isEnabled()) {
            throw new BadCredentialsException(String.format("Користувача %s заблоковано в системі!", name));
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(String.format("Не вірний пароль для користувача %s!", name));
        }

        List<GrantedAuthority> auth = new ArrayList<> ();
        auth.add((GrantedAuthority) () -> "ROLE_USER");
        return new UsernamePasswordAuthenticationToken(user.getId(), null, auth);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
