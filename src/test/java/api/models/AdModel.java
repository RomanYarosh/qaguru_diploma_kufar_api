package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdModel {
    @JsonProperty("ad_id")
    private Long adId;
    private String subject;
    @JsonProperty("price_byn")
    private String priceByn;
    private String category;
    @JsonProperty("ad_parameters")
    private List<AdParameter> adParameters;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdParameter {
        private String p;
        private Object v;
    }
}