package io.github.jadedchara.ace_lib.common.item;

import io.github.jadedchara.ace_lib.client.render.FlagItemRenderer;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipConfig;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.component.DataComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Color;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Formatting;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class PrideFlag extends BlockItem implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public PrideFlag(Block block,Item.Settings settings) {
		super(block,settings);
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
			private GeoItemRenderer<?> renderer = null;
			@Override
			public BuiltinModelItemRenderer getGeoItemRenderer() {
				if(this.renderer == null) {

					this.renderer = new FlagItemRenderer();
				}

				return this.renderer;
			}
		});
	}

	//~~~~~


	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipConfig config) {
		stack.set(
			DataComponentTypes.ITEM_NAME,
			stack.getName().copy().formatted(Formatting.LIGHT_PURPLE)
		);
		tooltip.add(Text.translatable("flagdescription.ace_lib."+
				stack.getComponents().getOrDefault(DataComponentRegistry.FLAG_TYPE,"classic"))
			.formatted(Formatting.GRAY,Formatting.ITALIC));
		//tooltip.
		//stack.
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return "flagtype.ace_lib."+
			stack.getComponents().getOrDefault(DataComponentRegistry.FLAG_TYPE,"classic");
	}
}
