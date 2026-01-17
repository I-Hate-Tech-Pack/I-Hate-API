package github.ihatechpack.api.client.event;

import github.ihatechpack.api.IHateAPI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 09:47
 */
// Client only counter
@EventBusSubscriber(modid = IHateAPI.MOD_ID,value = Dist.CLIENT)
public class ClientTickCounter {
    public static float RainbowGradationCounter = 0;
    @SubscribeEvent
    public static void on_client_tick(ClientTickEvent.Pre evt) {
        RainbowGradationCounter += 0.5f;
        if (RainbowGradationCounter >= 720.0f) {
            RainbowGradationCounter = 0.0f;
        }
    }
}
