package edu.netcracker.project.logistic.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        try {
            return LocalDateTime.parse(text, formatter);
        } catch (DateTimeParseException ex) {
            throw new ParseException("Invalid date format", 0);
        }
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(formatter);
    }
}
