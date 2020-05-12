package net.tardis.mod.common.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.client.EnumExterior;
import net.tardis.mod.common.blocks.BlockTardis;
import net.tardis.mod.common.blocks.BlockTardisTop;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.entities.controls.ControlDoor;
import net.tardis.mod.common.enums.EnumFlightState;
import net.tardis.mod.common.tileentity.TileEntityDoor;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.util.TardisTeleporter;

public class EntityTardis extends Entity{

	public static final DataParameter<Integer> OPEN_STATE = EntityDataManager.createKey(EntityTardis.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> EXTERIOR = EntityDataManager.createKey(EntityTardis.class, DataSerializers.VARINT);
	private BlockPos consolePos = BlockPos.ORIGIN;
	private int ticksOnGround = 0;
	private NBTTagCompound doorTag;
	private IBlockState state;
	
	public EntityTardis(final World worldIn) {
		super(worldIn);
		state = TBlocks.tardis_top_tt.getDefaultState();
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(OPEN_STATE, EnumFlightState.CLOSED.ordinal());
		this.dataManager.register(EXTERIOR, Block.getStateId(TBlocks.tardis_top_tt.getDefaultState()));
	}

	@Override
	protected void readEntityFromNBT(final NBTTagCompound compound) {
		this.consolePos = BlockPos.fromLong(compound.getLong("console"));
		this.doorTag = compound.getCompoundTag("door_tag");
		this.state = Block.getStateById(compound.getInteger("exterior_state"));
	}

	@Override
	protected void writeEntityToNBT(final NBTTagCompound compound) {
		compound.setLong("console", this.consolePos.toLong());
		if(doorTag!= null && !doorTag.isEmpty())
			compound.setTag("door_tag", doorTag);
		compound.setInteger("exterior_state", Block.getStateId(state));
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		this.move();
	
		//Allows this to be driven
		if(this.getPassengers().size() > 0) {
			final Entity entity = this.getPassengers().get(0);
			if(entity instanceof EntityLivingBase)
				this.handleRider((EntityLivingBase)entity);
		}
		
		this.setNoGravity(!this.getPassengers().isEmpty());

		//Rotate the entity
		if(this.hasNoGravity())
			this.rotationYaw = (rotationYaw + 0.5F) % 360;

		if(!world.isRemote && !BlockPos.ORIGIN.equals(this.consolePos)) {
			final WorldServer tardisDimension = ((WorldServer)world).getMinecraftServer().getWorld(TDimensions.TARDIS_ID);
			if(tardisDimension != null) {
				final TileEntity te = tardisDimension.getTileEntity(this.getConsole());
				if(te instanceof TileEntityTardis) {
					final TileEntityTardis tardis = (TileEntityTardis)te;
					tardis.setLocation(this.getPosition());
					tardis.setDesination(this.getPosition(), tardis.destDim);
					if(tardis.getTardisEntity() != this)
						tardis.setTardisEntity(this);
					
					//Update door constantly
					final ControlDoor intDoor = tardis.getDoor();
					if(intDoor != null){
						if(this.ticksExisted % 20 == 0) {
							intDoor.getDataManager().set(ControlDoor.FACING, this.rotationYaw);
							intDoor.setBotiUpdate(true);
							intDoor.getDataManager().set(ControlDoor.MOTION, new Vec3d(this.motionX, this.motionY, this.motionZ));
						}
					}
					
					//Replace exterior
					if(this.onGround || this.getPassengers().isEmpty()) {
						++this.ticksOnGround;
						if(this.ticksOnGround > 20) {
							this.setDead();
							tardis.startFlight();
						}
					}
				}
			}
		}
		
		if(world.isRemote && this.isInsideOfMaterial(Material.WATER)) {
			for(int i = 0; i < 20; ++i) {
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + Math.sin(Math.toRadians(i * 18)), posY, posZ + Math.cos(Math.toRadians(i * 18)), 0, 0.5, 0, 0);
			}
		}
		
		if(world.isRemote && this.isInsideOfMaterial(Material.FIRE)) {
			for(int i = 0; i < 20; ++i) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + Math.sin(Math.toRadians(i * 18)), posY, posZ + Math.cos(Math.toRadians(i * 18)), 0, 0.5, 0, 0);
			}
		}
		
		//Update Exterior Enum
		if(world.isRemote && this.ticksExisted % 20 == 0)
			this.state = Block.getStateById(this.dataManager.get(EXTERIOR));
		
	}
	
	public void move() {
		if(!this.onGround && !this.hasNoGravity())
			motionY -= 1D;
		this.move(MoverType.SELF, (motionX * speed() + 0.05), (motionY * 5), (motionZ * speed()));
		motionX = motionZ = motionY = 0;
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
	}

	public void handleRider(final EntityLivingBase base) {
		final Vec3d look = base.getLookVec().scale(0.3D);

		if(world.isRemote) {
			//Auto camera change
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
			Minecraft.getMinecraft().gameSettings.fovSetting = 110F;
			
			TileEntityTardis tardis = new TileEntityTardis();
			tardis.RWF = true;
		}

		if(base.moveForward > 0) {
			this.rotationYaw = (rotationYaw + 30F) % 360;
			motionX = look.x;
			motionZ = look.z;
		}
		else if(base.moveForward < 0) {
			this.rotationYaw = (rotationYaw + 30F) % 360;
			motionX = -look.x;
			motionZ = -look.z;
		}
		if(base.moveStrafing > 0) {
			this.rotationYaw = (rotationYaw + 30F) % 360;
			final Vec3d move = look.rotateYaw(-80);
			motionX = move.x;
			motionZ = move.z;
		}
		else if(base.moveStrafing < 0) {
			this.rotationYaw = (rotationYaw + 30F) % 360;
			final Vec3d move = look.rotateYaw(80);
			motionX = move.x;
			motionZ = move.z;
		}
		
		if(world.isRemote)
			world.sendPacketToServer(new CPacketVehicleMove(this));
		
	}

	public int speed() {
		return 10;
	}

	public void setConsole(final BlockPos console) {
		this.consolePos = console;
	}
	
	public BlockPos getConsole() {
		return this.consolePos;
	}
	
	@SideOnly(Side.CLIENT)
	public EnumExterior getExteriorEnum() {
		return EnumExterior.getExteriorFromBlock(this.state.getBlock());
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}
	
	public void setOpenState(final EnumFlightState state) {
		this.dataManager.set(OPEN_STATE, state.ordinal());
	}
	
	public EnumFlightState getOpenState() {
		return EnumFlightState.values()[this.dataManager.get(OPEN_STATE)];
	}

	@Override
	public double getMountedYOffset() {
		return 0;
	}

	@Override
	public void updatePassenger(final Entity passenger) {
		super.updatePassenger(passenger);

		final double angle = Math.toRadians(-this.rotationYaw);
		//Player position in RWF
		final double offsetX = Math.sin(angle) / 100, offsetZ = Math.cos(angle) / 100;
		passenger.setPosition(posX + offsetX, posY + this.getMountedYOffset() + passenger.getYOffset(), posZ + offsetZ);
		passenger.fallDistance = 0;
	}

	@Override
	protected void removePassenger(final Entity pass) {
		super.removePassenger(pass);
		if(!world.isRemote) {
			if(this.getOpenState() == EnumFlightState.CLOSED) {
				((WorldServer)world).addScheduledTask(new Runnable() {
					@Override
					public void run() {
						final WorldServer tardisWorld = ((WorldServer)world).getMinecraftServer().getWorld(TDimensions.TARDIS_ID);
						if(tardisWorld != null) {
							final BlockPos pos = getConsole().south();
							//TP back in to tardis after RWF
							pass.setPosition(pos.getX() + 1.5, pos.getY(), pos.getZ() + 1.5);
							if(pass instanceof EntityPlayerMP)
								world.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)pass, TDimensions.TARDIS_ID, new TardisTeleporter());
						}
					}
				});
			}
			else {
				final double z = Math.cos(Math.toRadians(this.rotationYaw));
				pass.setPosition(this.posX + z, this.posY, this.posZ + z);
			}
		}
		if(world.isRemote) {
			//Auto camera change
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
			Minecraft.getMinecraft().gameSettings.fovSetting = 70F;
			
			TileEntityTardis tardis = new TileEntityTardis();
			tardis.RWF = false;
		}
	}
	
	public IBlockState getBlockState() {
		return this.state;
	}
	
	public void setBlockState(final IBlockState state) {
		this.state = state;
		this.dataManager.set(EXTERIOR, Block.getStateId(state));
	}
	
	public void setTag(final NBTTagCompound tag) {
		this.doorTag = tag;
	}
}
