package edu.netcracker.project.logistic.formatter;

import edu.netcracker.project.logistic.model.Role;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class RoleFormatter implements Formatter<Role> {
    @Override
    public Role parse(String text, Locale locale) throws ParseException {
        throw new ParseException("This parser can only convert to String", 0);
    }

    @Override
    public String print(Role role, Locale locale) {
        String roleName = role.getRoleName().substring(5);
        char[] letters = roleName.toCharArray();
        char[] result = new char[letters.length];
        for (int i = 0; i < letters.length; i++) {
            if (i == 0) {
                result[i] = Character.toUpperCase(letters[i]);
            } else {
                result[i] = Character.toLowerCase(letters[i]);
            }
        }
        return new String(result);
    }
}
