package edu.netcracker.project.logistic.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        throw new ParseException("This parser can only convert to String", 0);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(formatter);
    }
}
