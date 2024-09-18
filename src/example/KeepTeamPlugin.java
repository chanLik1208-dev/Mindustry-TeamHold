import mindustry.mod.Plugin;
import mindustry.gen.Player;
import arc.util.Log;
import arc.Events;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;

import java.util.HashMap;
import java.util.Map;

public class TeamSavePlugin extends Plugin {
    // 用於保存玩家隊伍信息的地圖
    private final Map<String, String> playerTeams = new HashMap<>();

    @Override
    public void init() {
        // 監聽玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            String uuid = player.uuid();
            if (playerTeams.containsKey(uuid)) {
                // 恢復玩家隊伍
                String team = playerTeams.get(uuid);
                player.team(Team.valueOf(team));
                Log.info("恢復玩家 @ 的隊伍為 @", player.name, team);
            }
        });

        // 監聽玩家離開事件
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            String uuid = player.uuid();
            // 保存玩家隊伍信息
            playerTeams.put(uuid, player.team().name());
            Log.info("保存玩家 @ 的隊伍為 @", player.name, player.team().name());
        });
    }
}
