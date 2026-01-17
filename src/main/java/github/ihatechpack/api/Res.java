package github.ihatechpack.api;

import net.minecraft.resources.ResourceLocation;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:03
 */
public class Res {
    public static ResourceLocation rl(String registry){
        return ResourceLocation.fromNamespaceAndPath(IHateAPI.MOD_ID,registry);
    }
}
