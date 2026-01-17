package github.ihatechpack.api.client;

import github.ihatechpack.api.IHateAPI;
import github.ihatechpack.api.client.font.RainbowGradationFont;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 11:26
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class TestEventRegistry {

    @SubscribeEvent
    public static void reg(RegisterClientExtensionsEvent event){
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return RainbowGradationFont.Instance;
            }
        }, Items.NETHERITE_SWORD);
    }
}
