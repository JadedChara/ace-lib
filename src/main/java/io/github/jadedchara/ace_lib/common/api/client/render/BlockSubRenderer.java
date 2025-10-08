package io.github.jadedchara.ace_lib.common.api.client.render;

import net.minecraft.block.entity.BlockEntity;
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
	public BlockSubRenderer(AdjustableGeoBlockModel<T> model, T a) {
		super(model);
		//this.anim
		this.animatable = a;
	}



}
