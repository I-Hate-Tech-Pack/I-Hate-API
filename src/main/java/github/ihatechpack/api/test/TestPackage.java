package github.ihatechpack.api.test;

import github.ihatechpack.api.network.payload.DragonWingPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:00
 */
@EventBusSubscriber
public class TestPackage {
    @SubscribeEvent
    public static void on_right_click(PlayerInteractEvent.RightClickBlock event){
        if (!(event.getLevel().isClientSide)){
            // server side invoked
            PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(),new DragonWingPacket(true));
        }
    }

    @SubscribeEvent
    public static void on_left_click(PlayerInteractEvent.LeftClickBlock event){
        if (!(event.getLevel().isClientSide)){
            // server side invoked
            PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(),new DragonWingPacket(false));
        }
    }
}
