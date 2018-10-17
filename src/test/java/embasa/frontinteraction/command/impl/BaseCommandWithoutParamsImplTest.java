package embasa.frontinteraction.command.impl;

import embasa.frontinteraction.Request;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.persistence.securedb.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaseCommandWithoutParamsImplTest {

    @Test
    public void getSignature() {
        Command cmd = new BaseCommandWithoutParamsImpl(new Class[] { Integer.class, String.class, Boolean.class }) {

            @Override
            public String getName() {
                return "getSignatureTest";
            }

            @Override
            public String executeCmd(Request request, User user) throws BadParametersException {
                return null;
            }
        };

        String signature = cmd.getSignature();
        String expectedSignature = cmd.getName() + "(" + Integer.class.getSimpleName() + ", " +
                String.class.getSimpleName() + ", " + Boolean.class.getSimpleName() + ")";
        assertEquals(expectedSignature, signature);
    }
}