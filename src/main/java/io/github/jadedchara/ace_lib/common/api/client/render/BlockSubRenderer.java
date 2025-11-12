package io.github.jadedchara.ace_lib.common.api.client.render;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;


/**
 * Implementation of {@link DynamicGeoBlockRenderer} for use with {@link AddonBlockModelRenderLayer}.
 * <br></br>
 * This gets called in the super of AddonBlockModelRenderLayer, so it doesn't need to be referenced anywhere else.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class BlockSubRenderer<T extends BlockEntity & GeoAnimatable> extends DynamicGeoBlockRenderer<T> {

	public T anim;
	public float partialTick;
	public MatrixStack poseStack;
	public VertexConsumerProvider bufferSource;
	public int packedLight;
	public int packedOverlay;


	public BlockSubRenderer(AdjustableGeoBlockModel<T> model, T an) {
		super(model);
		this.animatable = an;
		//this.anim
		//this.animatable = a;
	}

	@Override
	public void render(T anim, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource,
					   int packedLight, int packedOverlay) {
		try{
			super.render(this.anim, this.partialTick,this.poseStack, this.bufferSource, this.packedLight,this.packedOverlay);
		}catch(Exception e){
			super.render(anim, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
		}
	}

	public void setFallbacks(T a,
							 float pt,
							 MatrixStack ps,
							 VertexConsumerProvider bs,
							 int pl,
							 int po
	){
		this.anim = a;
		this.animatable = this.anim;

		this.partialTick = pt;
		this.poseStack = ps;
		this.bufferSource = bs;
		this.packedLight = pl;
		this.packedOverlay = po;
	}
}
