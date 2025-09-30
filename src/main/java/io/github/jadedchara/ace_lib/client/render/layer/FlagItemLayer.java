package io.github.jadedchara.ace_lib.client.render.layer;

import io.github.jadedchara.ace_lib.common.api.client.render.AddonItemModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoItemModel;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import net.minecraft.util.Identifier;

public class FlagItemLayer extends AddonItemModelRenderLayer<PrideFlag> {
	//FlagItemClothRenderer renderer;


	public FlagItemLayer(AdjustableGeoItemModel<PrideFlag> m, Identifier t) {
		super(new FlagItemClothRenderer(m),m, t);
		this.MODEL = m;
		this.TEXTURE = t;

		//this.RENDERER = g;

	}

}
