package com.github.sparkmuse.spark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeletionConverterTest {

    @Test
    @DisplayName("converts from DeletedTriple to DeletedTriple Clean")
    void test() {

        DeletedTriple deletedTriple = DeletedTriple.builder()
                .creationTimestamp("1575158401000")
                .creator("/user/mwcl_musicbrainz")
                .deletionTimestamp("1575244802000")
                .deletor("/user/google_gardener")
                .subject("/m/0nkqwrl")
                .predicate("/type/object/type")
                .object("/music/group_membership")
                .languageCode("en")
                .build();

        Deletion expected = Deletion.builder()
                .creationDateTime("2019-12-01T00:00:01")
                .creator("mwcl_musicbrainz")
                .deletionDateTime("2019-12-02T00:00:02")
                .deletor("google_gardener")
                .subject("/m/0nkqwrl")
                .predicate("/type/object/type")
                .object("/music/group_membership")
                .languageCode("en")
                .build();

        Deletion actual = DeletionConverter.from(deletedTriple);

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    @DisplayName("sets defaults")
    class ErrorFixerClass {

        private DeletedTriple deletedTriple;
        private Deletion expected;

        @BeforeEach
        void setUp() {
            deletedTriple = DeletedTriple.builder().build();

            expected = Deletion.builder()
                    .creationDateTime("1970-01-01T00:00:00")
                    .deletionDateTime("1970-01-01T00:00:00")
                    .build();
        }

        @Test
        @DisplayName("when creation and deletedTriple stamps are null")
        void returnNow() {

            Deletion actual = DeletionConverter.from(deletedTriple);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("when creator and deletor are null")
        void returnNullCreatorDeletor() {

            Deletion actual = DeletionConverter.from(deletedTriple);

            assertThat(actual).isEqualTo(expected);
        }
    }
}