package net.tardis.mod.common.screwdriver;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.tardis.mod.Tardis;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.tileentity.TileEntityEPanel;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.common.blocks.BlockEPanel;
import net.tardis.mod.util.common.helpers.PlayerHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = Tardis.MODID)
public class InteractionEpanel implements IScrew {
	
	private Method dispense = ReflectionHelper.findMethod(BlockDispenser.class, "dispense", "func_176439_d", World.class, BlockPos.class);
	
	private int Emode = 0;
	private int Max = 2;

	@Override
	public EnumActionResult performAction(World world, EntityPlayer player, EnumHand hand) {
		return EnumActionResult.FAIL;
	}
	
	@Override
	public EnumActionResult blockInteraction(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (world.isRemote) return EnumActionResult.FAIL;
		
		Block block = state.getBlock();
		
		if (block instanceof BlockEPanel) {
			if (Emode >= Max)
				Emode = 0;
			else
				Emode ++;

			if (Emode == 0) {
				world.setBlockState(pos, TBlocks.epanel_item.getDefaultState(), 2);
			} else if (Emode == 1) {
				world.setBlockState(pos, TBlocks.epanel_light.getDefaultState(), 2);
			} else if (Emode == 2) {
				world.setBlockState(pos, TBlocks.epanel_room.getDefaultState(), 2);
			}
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}
	
	@Override
	public boolean entityInteraction(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		return false;
	}
	
	private void markUpdate(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state, 10);
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Override
	public String getName() {
		return "screw.epanel";
	}
	
	@Override
	public int getCoolDownAmount() {
		return 20;
	}
	
	@Override
	public boolean causesCoolDown() {
		return true;
	}
	
	@Override
	public String getInfo() {
		return "screwdriver.info.epanel";
	}
	
	
}
