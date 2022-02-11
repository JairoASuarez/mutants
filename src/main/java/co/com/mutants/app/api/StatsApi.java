package co.com.mutants.app.api;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Stats Api")
public class StatsApi {

    @JsonProperty(value = "count_mutant_dna")
    @Schema(description = "Number of mutants")
    private long countMutantDna;

    @JsonProperty(value = "count_human_dna")
    @Schema(description = "Number of humans")
    private long countHumanDna;

    @JsonProperty(value = "ratio")
    @Schema(description = "Ratio")
    private double ratio;

    public StatsApi() {
        super();
    }

    private StatsApi(Builder builder) {
        countMutantDna = builder.countMutantDna;
        countHumanDna = builder.countHumanDna;
        ratio = builder.ratio;
    }

    public static Builder newBuilder() {
        return new Builder();
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

        private long countMutantDna;
        private long countHumanDna;
        private double ratio;

        private Builder() {
        }

        public Builder countMutantDna(long val) {
            countMutantDna = val;
            return this;
        }

        public Builder countHumanDna(long val) {
            countHumanDna = val;
            return this;
        }

        public Builder ratio(double val) {
            ratio = val;
            return this;
        }

        public StatsApi build() {
            return new StatsApi(this);
        }

    }

}