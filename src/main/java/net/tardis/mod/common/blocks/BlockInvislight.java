package net.tardis.mod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.common.tileentity.TileEntityInvislight;
import net.tardis.mod.common.blocks.BlockTileBase;

import java.util.Random;

public class BlockInvislight extends BlockTileBase {

	public BlockInvislight() {
		super(Material.GLASS, TileEntityInvislight::new);		
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, float hitX, float hitY, float hitZ) {
    //    TileEntityTardis tardis = new TileEntityTardis();
    //    if (tardis.getArtron() >= 1) {

    //    } else {

    //    }
    return true;
    }

	public Class<TileEntityInvislight> getTileEntityClass() {
		return TileEntityInvislight.class;
	}
    

	public TileEntityInvislight createTileEntity(World world, IBlockState state) {
		return new TileEntityInvislight();
	}

}