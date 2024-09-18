import arc.Events;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.mod.Plugin;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class KeepTeamPlugin extends Plugin {

    private ObjectMap<String, ObjectMap<String, Object>> playerData = new ObjectMap<>();

    @Override
    public void init() {
        Events.on(PlayerJoin.class, event -> {
            String uuid = event.player.uuid();
            Log.info("Player @ joined", uuid);
            if (getPlayerData(uuid).containsKey("team")) {
                int teamId = (int) getPlayerData(uuid).get("team");
                Player player = Groups.player.find(p -> p.uuid().equals(uuid));
                if (player != null) {
                    player.team(Team.get(teamId));
                    Call.setPlayerTeam(player.con, Team.get(teamId));
                    Log.info("Player @ assigned to team @", uuid, teamId);
                }
            }
        });

        Events.on(PlayerLeave.class, event -> {
            String uuid = event.player.uuid();
            getPlayerData(uuid).put("team", event.player.team().id);
            Log.info("Player @ left and their team @ was saved", uuid, event.player.team().id);
        });
    }

    private ObjectMap<String, Object> getPlayerData(String uuid) {
        return playerData.get(uuid, ObjectMap::new);
    }
}
