import mindustry.mod.Plugin;
import mindustry.gen.Player;
import mindustry.net.Administration;

import java.util.HashMap;
import java.util.Map;

public class TeamPersistencePlugin extends Plugin {
    private final Map<String, String> playerTeams = new HashMap<>();

    @Override
    public void init() {
        // 监听玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            String uuid = player.uuid();
            if (playerTeams.containsKey(uuid)) {
                player.team(Team.get(playerTeams.get(uuid)));
            }
        });

        // 监听玩家离开事件
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            playerTeams.put(player.uuid(), player.team().name);
        });

        // 监听玩家更换队伍事件
        Events.on(PlayerChangeTeamEvent.class, event -> {
            Player player = event.player;
            playerTeams.put(player.uuid(), event.newTeam.name);
        });
    }
}
