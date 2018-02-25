
package beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import core.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class YandexSpellerAnswer {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("pos")
    @Expose
    private Integer pos;

    @SerializedName("row")
    @Expose
    private Integer row;

    @SerializedName("col")
    @Expose
    private Integer col;

    @SerializedName("len")
    @Expose
    private Integer len;

    @SerializedName("word")
    @Expose
    public String word;

    @SerializedName("s")
    @Expose
    public List<String> similar = new ArrayList<>();

    public Error getError() {
        return Error.getByCode(code);
    }
}
