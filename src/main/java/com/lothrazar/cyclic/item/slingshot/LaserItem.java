package com.lothrazar.cyclic.item.slingshot;

import com.lothrazar.cyclic.item.ItemBaseCyclic;
import com.lothrazar.cyclic.util.UtilItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class LaserItem extends ItemBaseCyclic {

  public LaserItem(Properties properties) {
    super(properties.defaultDurability(1024 * 4));
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.NONE;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
    ItemStack itemstack = player.getItemInHand(handIn);
    //    playerIn.startUsingItem(handIn);
    shootMe(world, player, new LaserEntity(player, world), 0, ItemBaseCyclic.VELOCITY_MAX * 1.4F);
    // TODO: RF POWER NOT DURAB
    UtilItemStack.damageItem(player, itemstack);
    return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
  }
}
