package net.codesent.flintguns.features.Entities.Projectilies.Bullet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.codesent.flintguns.FlintGuns;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public class BulletModel extends EntityModel<BulletEntityRenderState> {
    public static final ModelLayerLocation MODEL_LAYER =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(FlintGuns.MODID,"flintguns_bullet"), "main");

    private final ModelPart Bullet;


    public BulletModel(ModelPart root) {
        super(root);
        Bullet = root.getChild("Bullet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        root.addOrReplaceChild("Bullet",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }


}
