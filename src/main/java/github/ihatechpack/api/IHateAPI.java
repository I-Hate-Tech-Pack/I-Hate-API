package github.ihatechpack.api;

import com.mojang.logging.LogUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/13 21:29
 */
@Mod(IHateAPI.MOD_ID)
public class IHateAPI {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IHateAPI.MOD_ID);
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));


    public static final String MOD_ID = "ihateapi";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static IHateAPI instance;
    public IHateAPI(IEventBus modBus){
        instance = this; // single instance mode
        ITEMS.register(modBus);
    }

    public static IHateAPI getInstance() {
        return instance;
    }
}

