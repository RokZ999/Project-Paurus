import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class GetPlayers {
    public static List<String> players = getPlayers();

    public static JSONArray getJson() throws IOException {
        return new JSONArray
        (
                IOUtils.toString
                (
                    new URL("https://api.sportsdata.io/v3/nba/scores/json/Players/DAL?key=58957574730c4ee1b809da2f53525997"),
                    StandardCharsets.UTF_8
                )
        );
    }
    public static  List<String> getPlayers() {
        try {
            List<String> players = new ArrayList<>();

            getJson().forEach(obj -> {
                players.add("https://www.nba.com/player/" + ((JSONObject) obj).getLong("NbaDotComPlayerID"));
            });
            return players;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
