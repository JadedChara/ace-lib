package io.github.jadedchara.ace_lib.common.item;

import io.github.jadedchara.ace_lib.client.render.FlagItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class PrideFlag extends BlockItem implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public PrideFlag(Block block,Item.Settings settings) {
		super(block,settings);

		//this.place();

	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private DynamicGeoItemRenderer<?> renderer = null;

			@Override
			public BuiltinModelItemRenderer getGeoItemRenderer() {
				if(this.renderer == null) {
					this.renderer = new FlagItemRenderer();
				}

				return this.renderer;
			}
		});
	}
}
