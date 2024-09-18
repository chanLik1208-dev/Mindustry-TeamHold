import arc.Events;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.mod.Plugin;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.game.Team;
import mindustry.Vars;

public class KeepTeamPlugin extends Plugin {

    // 用于存储玩家自定义数据的ObjectMap
    private ObjectMap<String, ObjectMap<String, Object>> playerData = new ObjectMap<>();

    @Override
    public void init() {
        // 监听玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            // 获取玩家UUID
            String uuid = event.player.uuid();
            // 检查玩家是否有之前的队伍记录
            if (getPlayerData(uuid).containsKey("team")) {
                // 将玩家分配到之前的队伍
                int teamId = (int) getPlayerData(uuid).get("team");
                Vars.playerGroup.find(p -> p.uuid().equals(uuid)).team(Team.get(teamId));
                Log.info("Player @ joined and was assigned to team @", uuid, teamId);
            }
        });

        // 监听玩家离开事件
        Events.on(PlayerLeave.class, event -> {
            // 获取玩家UUID
            String uuid = event.player.uuid();
            // 保存玩家的队伍信息
            getPlayerData(uuid).put("team", event.player.team().id);
            Log.info("Player @ left and their team was saved", uuid);
        });
    }

    // 获取玩家自定义数据的方法
    private ObjectMap<String, Object> getPlayerData(String uuid) {
        return playerData.get(uuid, ObjectMap::new);
    }
}
