package embasa.listeners;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionListenerTest {

    SessionListener listener = new SessionListener();

    @Test
    public void sessionCreated() {
        HttpSessionEvent event = mock(HttpSessionEvent.class);
        HttpSession session = mock(HttpSession.class);
        when(event.getSession()).thenReturn(session);
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        listener.sessionCreated(event);
        verify(session).setMaxInactiveInterval(argument.capture());
        assertEquals((Integer) 600, argument.getValue());
    }
}