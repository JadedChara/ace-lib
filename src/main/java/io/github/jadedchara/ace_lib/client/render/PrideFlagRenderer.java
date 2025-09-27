package io.github.jadedchara.ace_lib.client.render;


import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.model.block.FlagPoleModel;
import io.github.jadedchara.ace_lib.client.render.layer.FlagLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonBlockModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.api.client.render.BlockSubRenderer;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import io.github.jadedchara.ace_lib.client.model.block.PrideFlagModel;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;


/**
 * Implementation of {@link DynamicGeoBlockRenderer} that instead uses {@link GeoRenderLayer} to handle things,
 * because the default approach is annoyingly buggy.
 * <br></br>This is done via 3 classes, being:
 * <br></br>
 * 	<br></br>-{@link AddonBlockModelRenderLayer} which is the actual template {@link FlagLayer} extends.
 * 	<br></br>-{@link AdjustableGeoBlockModel} which serves as the basis for {@link FlagPoleModel} and {@link PrideFlagModel}.
 * 	<br></br>-{@link BlockSubRenderer} which provides us an easy input renderer that takes a model as input.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class PrideFlagRenderer extends DynamicGeoBlockRenderer<PrideFlagBlockEntity> {

	//public
	public static Identifier TEST = Identifier.of("minecraft","textures/block/sand.png");
	public static Identifier FLAG = Identifier.of(AceLib.MOD_ID, "textures/flag/classic.png");

	public PrideFlagRenderer(BlockEntityRendererFactory.Context context){

		super(new FlagPoleModel(Identifier.of(AceLib.MOD_ID,"flag_pole")));
		addRenderLayer(new FlagLayer(new PrideFlagModel(),FLAG));
	}

}
