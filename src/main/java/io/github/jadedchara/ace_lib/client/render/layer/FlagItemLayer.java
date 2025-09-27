package io.github.jadedchara.ace_lib.client.render.layer;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.model.item.FlagItemClothModel;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonItemModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoItemModel;
import io.github.jadedchara.ace_lib.common.api.client.render.ItemSubRenderer;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

public class FlagItemLayer extends AddonItemModelRenderLayer<PrideFlag> {
	FlagItemClothRenderer renderer;

	public FlagItemLayer(AdjustableGeoItemModel<PrideFlag> m, Identifier t, DynamicGeoItemRenderer g) {
		super(new FlagItemClothRenderer(m),m, t);
		//this.renderer = new FlagItemClothRenderer(m);
		this.MODEL = m;
		this.TEXTURE = t;
		this.RENDERER = g;
		//this.MODEL.setModel(Identifier.of(AceLib.MOD_ID,"geo/item/pride_flag_cloth.json"));
	}

	@Deprecated
	@Override
	public void updateTexture(PrideFlag e){
		//((FlagItemClothModel)this.getGeoModel().s)
		try {
			String type = e
				.getComponents()
				.getOrDefault(
					DataComponentRegistry.FLAG_TYPE,
					"classic"
				).replaceAll("\"", "");

			this.TEXTURE = Identifier.of(
				AceLib.MOD_ID,
				"textures/flag/" + type + ".png");
			this.MODEL.setTexture(this.TEXTURE);
		}catch(Exception ex){
			return;
		}
	}
	public void setTexture(){

	}
}
