package io.github.jadedchara.ace_lib.client.render.layer;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoItemModel;
import io.github.jadedchara.ace_lib.common.api.client.render.ItemSubRenderer;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class FlagItemClothRenderer extends ItemSubRenderer<PrideFlag> {

	public FlagItemClothRenderer(AdjustableGeoItemModel<PrideFlag> m) {
		super(m);
		//this.model = m;
	}


	@Override
	public void tweakModel(ItemStack s){

		String type = s
			.getComponents()
			.getOrDefault(DataComponentRegistry.FLAG_TYPE,"classic")
			.replaceAll("\"","");
		Identifier typeD = Identifier.of(AceLib.MOD_ID,"textures/flag/"+type+".png");
		System.out.println(typeD);
		((AdjustableGeoItemModel)this.getGeoModel()).setTexture(typeD);
	}
}
