import mindustry.mod.Plugin;
import mindustry.gen.Player;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import arc.Events;
import mindustry.Vars;
import mindustry.game.Team;
import java.util.HashMap;
import java.util.Map;

public class KeepTeamPlugin extends mindustry.mod.Plugin {

    // 用于存储玩家自定义数据的Map
    private Map<String, Map<String, Object>> playerData = new HashMap<>();

    public void init() {
        // 监听玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            // 检查玩家是否有之前的队伍记录
            if (getPlayerData(player).containsKey("team")) {
                // 将玩家分配到之前的队伍
                player.team(Team.get((int) getPlayerData(player).get("team")));
            }
        });

        // 监听玩家离开事件
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            // 保存玩家的队伍信息
            getPlayerData(player).put("team", player.team().id);
        });
    }

    // 获取玩家自定义数据的方法
    private Map<String, Object> getPlayerData(Player player) {
        return playerData.computeIfAbsent(player.uuid(), k -> new HashMap<>());
    }
}
