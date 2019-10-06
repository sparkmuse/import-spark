package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.model.DeletionConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeletionConverterTest {

    @Test
    @DisplayName("converts from Deletion to Deletion Clean")
    void test() {

        Deletion deletion = Deletion.builder()
                .creationTimestamp("1575158401000")
                .creator("/user/mwcl_musicbrainz")
                .deletionTimestamp("1575244802000")
                .deletor("/user/google_gardener")
                .subject("/m/0nkqwrl")
                .predicate("/type/object/type")
                .object("/music/group_membership")
                .languageCode("en")
                .build();

        DeletionClean expected = DeletionClean.builder()
                .creationDateTime("2019-12-01T00:00:01")
                .creator("mwcl_musicbrainz")
                .deletionDateTime("2019-12-02T00:00:02")
                .deletor("google_gardener")
                .subject("/m/0nkqwrl")
                .predicate("/type/object/type")
                .object("/music/group_membership")
                .languageCode("en")
                .build();

        DeletionClean actual = DeletionConverter.from(deletion);

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    @DisplayName("sets defaults")
    class ErrorFixerClass {

        private Deletion deletion;
        private DeletionClean expected;

        @BeforeEach
        void setUp() {
            deletion = Deletion.builder().build();

            expected = DeletionClean.builder()
                    .creationDateTime("1970-01-01T00:00:00")
                    .deletionDateTime("1970-01-01T00:00:00")
                    .build();
        }

        @Test
        @DisplayName("when creation and deletion stamps are null")
        void returnNow() {

            DeletionClean actual = DeletionConverter.from(deletion);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("when creator and deletor are null")
        void returnNullCreatorDeletor() {

            DeletionClean actual = DeletionConverter.from(deletion);

            assertThat(actual).isEqualTo(expected);
        }
    }
}