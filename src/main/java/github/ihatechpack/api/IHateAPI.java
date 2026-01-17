package github.ihatechpack.api;

import com.mojang.logging.LogUtils;
import github.ihatechpack.api.common.event.PlayerLoggedInListener;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
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

    public static final String MOD_ID = "ihateapi";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static IHateAPI instance;
    public IHateAPI(IEventBus modBus){
        instance = this; // single instance mode
        // listeners
        // modBus.addListener(PlayerLoggedInListener::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(PlayerLoggedInListener::onPlayerLoggedIn);
    }

    public static IHateAPI getInstance() {
        return instance;
    }
}

