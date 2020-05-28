package net.tardis.mod.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.tardis.mod.common.blocks.BlockInvislight;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.tileentity.TileEntityTardis;

public class TileEntityInvislight extends TileEntity {


	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityTardis tardis = new TileEntityTardis();
        if(tardis.getArtron() >= 1) {
            return 10;
        }else {
            return 0;
        }
	}


    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityInvislight();
    }


    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        world.removeTileEntity(pos);
    }


    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
}