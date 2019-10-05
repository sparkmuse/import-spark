package spark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletionClean implements Serializable {
    private String creationDateTime;
    private String creator;
    private String deletionDateTime;
    private String deletor;
    private String subject;
    private String predicate;
    private String object;
    private String languageCode;
}