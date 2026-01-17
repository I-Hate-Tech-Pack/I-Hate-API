package github.ihatechpack.api.client.render.dragon_wing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.entity.player.Player;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 16:00
 */
public class RenderCore {

    public static void applyWingTransforms(PoseStack poseStack, Player player, int scale) {
        double scaleValue = scale / 100D;

        poseStack.pushPose();
        poseStack.scale((float) -scaleValue, (float) -scaleValue, (float) scaleValue);

        poseStack.mulPose(Axis.YP.rotationDegrees(180 + player.yBodyRot));

        poseStack.translate(0, -1.25 / scaleValue, 0);
        poseStack.translate(0, 0, 0.2 / scaleValue);

        if (player.isCrouching()) {
            poseStack.translate(0D, 0.125D / scaleValue, 0D);
        }
    }

    public static void restoreWingTransforms(PoseStack poseStack) {
        poseStack.popPose();
    }
}
