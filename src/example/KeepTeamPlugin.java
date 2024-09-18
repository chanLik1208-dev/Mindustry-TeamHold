import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration;
import mindustry.Vars;


public class KeepTeamPlugin extends Plugin {

    @Override
    public void init() {
        // 监听玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            player = event.player;
            // 检查玩家是否有之前的队伍记录
            if (player.customData().has("team")) {
                // 将玩家分配到之前的队伍
                player.team(Team.get(player.customData().getInt("team")));
            }
        });

        // 监听玩家离开事件
        Events.on(PlayerLeave.class, event -> {
            player = event.player;
            // 保存玩家的队伍信息
            player.customData().put("team", player.team().id);
        });
    }
}
