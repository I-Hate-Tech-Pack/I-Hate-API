package github.ihatechpack.api.network.handler;

import github.ihatechpack.api.IHateAPI;
import github.ihatechpack.api.client.render.dragon_wing.RenderHandler;
import github.ihatechpack.api.network.payload.DragonWingPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:05
 */
public class DragonWingPacketHandler {

    public static void onHandle(final DragonWingPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                onClientHandle(data, context);
            } else {
                onServerHandle(data, context);
            }
        });
    }

    // invoked when accept packet from server
    @OnlyIn(Dist.CLIENT)
    private static void onClientHandle(final DragonWingPacket data, final IPayloadContext context) {
        // IHateAPI.LOGGER.info("accpet packet from server");
        // PacketDistributor.sendToServer(new DragonWingPacket(true));
        RenderHandler.enable = data.enable();
    }

    // invoked when accept packet from client
    private static void onServerHandle(final DragonWingPacket data, final IPayloadContext context) {
        // IHateAPI.LOGGER.info("accept packet to client");
    }
}
