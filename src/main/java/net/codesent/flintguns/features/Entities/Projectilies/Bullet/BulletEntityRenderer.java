package net.codesent.flintguns.features.Entities.Projectilies.Bullet;

import com.mojang.blaze3d.vertex.PoseStack;
import net.codesent.flintguns.FlintGuns;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class BulletEntityRenderer extends ArrowRenderer<BulletEntity,BulletEntityRenderState> {
    private final BulletModel model;
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FlintGuns.MODID, "textures/entity/bullet.png");


    public BulletEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BulletModel(context.bakeLayer(BulletModel.MODEL_LAYER));
    }

    @Override
    public BulletEntityRenderState createRenderState() {
        return new BulletEntityRenderState();
    }

    @Override
    public void extractRenderState(BulletEntity entity, BulletEntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
    }

    @Override
    public void submit(BulletEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        super.submit(state, poseStack, collector, cameraState);
        // Submits the model to be rendered
        int light = 15728880;

        // Get the texture and define the render type

        RenderType renderType = model.renderType(getTextureLocation(state));

        // Submit the model using the correct integer parameters instead of a lambda
        collector.order(1).submitModel(model,state,poseStack,renderType,light,OverlayTexture.NO_OVERLAY,0,null);
    }
    @Override
    public Identifier getTextureLocation(BulletEntityRenderState state) {
        return TEXTURE;
    }
}