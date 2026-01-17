package github.ihatechpack.api.common.event;

import github.ihatechpack.api.Res;
import github.ihatechpack.api.network.payload.DragonWingPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:54
 */
public class PlayerLoggedInListener {
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // send nbt to client
            PacketDistributor.sendToPlayer(serverPlayer, new DragonWingPacket(serverPlayer.getPersistentData().getBoolean(Res.rl("render_dragon_wings").toString())));
        }
    }
}
