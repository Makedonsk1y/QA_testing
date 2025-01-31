package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sound {
    @JsonProperty
    private String sound;
}
