package github.ihatechpack.api.network.registry;

import github.ihatechpack.api.IHateAPI;
import github.ihatechpack.api.network.handler.DragonWingPacketHandler;
import github.ihatechpack.api.network.payload.DragonWingPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:44
 */
@EventBusSubscriber(modid = IHateAPI.MOD_ID) // both side
public class PlayloadRegistry {

    @SubscribeEvent
    public static void onRegister(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(IHateAPI.MOD_ID).versioned("1");
        registrar.playBidirectional(
                DragonWingPacket.TYPE,
                DragonWingPacket.STREAM_CODEC,
                DragonWingPacketHandler::onHandle
        );
    }

}
