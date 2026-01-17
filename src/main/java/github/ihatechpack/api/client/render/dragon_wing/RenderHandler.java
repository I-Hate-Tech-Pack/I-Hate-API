package github.ihatechpack.api.client.render.dragon_wing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.ihatechpack.api.Res;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 16:02
 */
@EventBusSubscriber(value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class RenderHandler {

    public static boolean enable = false;

    private static RenderHandler instance;

    private final ModelPart wing;
    private final ModelPart wingTip;

    private RenderHandler() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wingPart = partdefinition.addOrReplaceChild("wing", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-10.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F)
                        .texOffs(-10, 8)
                        .addBox(-10.0F, 0.0F, 0.5F, 10.0F, 0.0F, 10.0F),
                PartPose.offset(-2.0F, 0.0F, 0.0F)
        );

        wingPart.addOrReplaceChild("wingtip", CubeListBuilder.create()
                        .texOffs(0, 5)
                        .addBox(-10.0F, -0.5F, -0.5F, 10.0F, 1.0F, 1.0F)
                        .texOffs(-10, 18)
                        .addBox(-10.0F, 0.0F, 0.5F, 10.0F, 0.0F, 10.0F),
                PartPose.offset(-10.0F, 0.0F, 0.0F)
        );

        ModelPart root = partdefinition.bake(30, 30);
        this.wing = root.getChild("wing");
        this.wingTip = wing.getChild("wingtip");
    }

    public static RenderHandler getInstance() {
        if (instance == null) {
            instance = new RenderHandler();
        }
        return instance;
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (player.isSwimming()) return;
        // there is still bug when jump out from the water
        WingType wingType = WingType.ENDER_DRAGON;
        renderWings(player, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), wingType);
    }

    public static void renderWings(Player player, PoseStack poseStack, MultiBufferSource buffer, int packedLight, WingType wingType) {

        if (!enable) return;
        RenderHandler renderer = getInstance();

        int scale = 100; //Config.getWingsScale(wingType);

        RenderCore.applyWingTransforms(poseStack, player, scale);

        ResourceLocation texture = Res.rl(wingType.getTexturePath());
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));

        renderer.updateWingAnimation();

        for (int j = 0; j < 2; ++j) {
            renderer.wing.render(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

            if (j == 0) {
                poseStack.scale(-1.0F, 1.0F, 1.0F);
            }
        }

        RenderCore.restoreWingTransforms(poseStack);
    }

    private void updateWingAnimation() {
        float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
        this.wing.xRot = (float) Math.toRadians(-80F) - (float) Math.cos((double)f11) * 0.2F;
        this.wing.yRot = (float) Math.toRadians(20F) + (float) Math.sin(f11) * 0.4F;
        this.wing.zRot = (float) Math.toRadians(20F);
        this.wingTip.zRot = -((float)(Math.sin((double)(f11 + 2.0F)) + 0.5D)) * 0.75F;
    }
}
