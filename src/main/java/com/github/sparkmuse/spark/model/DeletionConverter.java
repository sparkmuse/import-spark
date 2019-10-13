package com.github.sparkmuse.spark.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DeletionConverter {

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
    private static final Pattern USER_PATTERN = Pattern.compile("/user/");

    public static Deletion from(DeletedTriple deletedTriple) {

        LocalDateTime creationDateTime = getLocalDateTime(deletedTriple.getCreationTimestamp());
        LocalDateTime deletionDateTime = getLocalDateTime(deletedTriple.getDeletionTimestamp());

        String creator = getDefaultString(deletedTriple.getCreator());
        String deletor = getDefaultString(deletedTriple.getDeletor());

        return Deletion.builder()
                .creationDateTime(creationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .creator(creator)
                .deletionDateTime(deletionDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .deletor(deletor)
                .subject(deletedTriple.getSubject())
                .predicate(deletedTriple.getPredicate())
                .object(deletedTriple.getObject())
                .languageCode(deletedTriple.getLanguageCode())
                .build();
    }

    private static String getDefaultString(String creator) {
        return creator == null ? null : USER_PATTERN.matcher(creator).replaceFirst("");
    }

    private static LocalDateTime getLocalDateTime(String timeStamp) {
        return  timeStamp != null ?
                LocalDateTime.ofEpochSecond(Long.parseLong(timeStamp) / 1000, 0, ZoneOffset.UTC) :
                DEFAULT_DATE;
    }
}
