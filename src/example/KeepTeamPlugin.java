import mindustry.mod.Plugin;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import arc.Events;
import mindustry.Vars;
import mindustry.game.Team;
import arc.struct.ObjectMap;

public class KeepTeamHandler {

    // 用于存储玩家自定义数据的ObjectMap
    private ObjectMap<String, ObjectMap<String, Object>> playerData = new ObjectMap<>();

    public KeepTeamHandler() {
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
    private ObjectMap<String, Object> getPlayerData(Player player) {
        return playerData.get(player.uuid(), ObjectMap::new);
    }
}
