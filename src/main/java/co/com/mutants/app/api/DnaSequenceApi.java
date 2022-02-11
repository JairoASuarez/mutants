package co.com.mutants.app.api;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dna Sequence Api")
public class DnaSequenceApi {

    @NotNull(message = "DNA cannot be null")
    @JsonProperty(value = "dna", required = true)
    @Schema(description = "DNA")
    private String[] dna;

    public DnaSequenceApi() {
        super();
    }

    private DnaSequenceApi(Builder builder) {
        dna = builder.dna;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String[] getDna() {
        return dna;
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this, true);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    public static final class Builder {

        private String[] dna;

        public Builder() {
            super();
        }

        public Builder dna(String[] val) {
            dna = val;
            return this;
        }

        public DnaSequenceApi build() {
            return new DnaSequenceApi(this);
        }

    }

}
