package net.tardis.mod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.tardis.mod.common.tileentity.TileEntityInvislight;
import net.tardis.mod.common.blocks.interfaces.IRenderBox;
import net.tardis.mod.common.blocks.BlockTileBase;
import net.minecraft.util.EnumBlockRenderType;

import java.util.Random;

public class BlockInvislight extends BlockTileBase implements IRenderBox {
	public BlockInvislight() {
		super(Material.GLASS, TileEntityInvislight::new);		
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 15;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean shouldRenderBox() {
		return false;
	}

	public Class<TileEntityInvislight> getTileEntityClass() {
		return TileEntityInvislight.class;
	}
    

	public TileEntityInvislight createTileEntity(World world, IBlockState state) {
		return new TileEntityInvislight();
	}

}