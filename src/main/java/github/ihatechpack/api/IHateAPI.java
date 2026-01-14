package github.ihatechpack.api;

import github.ihatechpack.api.font.NewFont;
import net.minecraft.client.gui.Font;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/13 21:29
 */
@Mod(IHateAPI.MOD_ID)
public class IHateAPI {
    public static final String MOD_ID = "ihateapi";
    private static IHateAPI instance;
    public IHateAPI(IEventBus modBus){
        instance = this; // single instance mode
        Test.ITEMS.register(modBus);
        modBus.addListener(IHateAPI::reg);
    }

    public static IHateAPI getInstance() {
        return instance;
    }

    @OnlyIn(Dist.CLIENT)
    public static void reg(RegisterClientExtensionsEvent event){
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @Nullable Font getFont(ItemStack stack, FontContext context) {
                return NewFont.getINSTANCE();
            }
        },Test.EXAMPLE_ITEM);
    }
}

class Test{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IHateAPI.MOD_ID);
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

}
