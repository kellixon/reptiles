//  
//  =====GPL=============================================================
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; version 2 dated June, 1991.
// 
//  This program is distributed in the hope that it will be useful, 
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
// 
//  You should have received a copy of the GNU General Public License
//  along with this program;  if not, write to the Free Software
//  Foundation, Inc., 675 Mass Ave., Cambridge, MA 02139, USA.
//  =====================================================================
//
//
//
package com.reptiles.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// base class for all turtles and tortoises
public class EntityTurtle extends EntityTameable {

    private static final DataParameter<Float> health = EntityDataManager.createKey(EntityTurtle.class, DataSerializers.FLOAT);
    private int turtleTimer;
    private EntityAIEatGrass plantEating;
    private final int maxHealth = 10;

    public EntityTurtle(World world) {
        super(world);
        setSize(0.5F, 0.5F);
        setPathPriority(PathNodeType.WATER, 0.0f);
        setTamed(false);
    }
    
    @Override
    protected void initEntityAI() {
		double moveSpeed = 0.75;
		plantEating = new EntityAIEatGrass(this);
        tasks.addTask(1, new EntityAISwimming(this));
        //tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        tasks.addTask(2, aiSit = new EntityAISit(this));
        tasks.addTask(3, new EntityAIMate(this, moveSpeed));
        tasks.addTask(4, new EntityAITempt(this, moveSpeed, Items.CARROT, false));
        if (ConfigHandler.getFollowOwner()) {
            tasks.addTask(5, new EntityAIFollowOwner(this, moveSpeed, 10.0F, 2.0F));
        }
        tasks.addTask(6, plantEating);
        tasks.addTask(7, new EntityAIWander(this, moveSpeed));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
	}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (isTamed()) {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth); // health
        } else {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0); // health
        }
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2); // move speed
    }

    @Override
    protected boolean canDespawn() {
        return ConfigHandler.shouldDespawn() && !isTamed();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(health, getHealth());
    }

    @Override
    protected void updateAITasks() {
        turtleTimer = plantEating.getEatingGrassTimer();
        dataManager.set(health, getHealth());
        super.updateAITasks();
    }

    // This MUST be overridden in the derived class
    public EntityAnimal spawnBabyAnimal(EntityAgeable entityageable) {
        Reptiles.proxy.error("[ERROR] Do NOT call this base class method directly!");
        return null;
    }

    private boolean isHardenedClay(BlockPos bp) {
        Block block = worldObj.getBlockState(bp).getBlock();
        return block == Blocks.HARDENED_CLAY;
    }

    private boolean isSandOrGrassBlock(BlockPos bp) {
        Block block = worldObj.getBlockState(bp).getBlock();
        return (block == Blocks.SAND || block == Blocks.GRASS);
    }

    @Override
    public boolean getCanSpawnHere() {
        AxisAlignedBB entityAABB = getEntityBoundingBox();
        if (worldObj.checkNoEntityCollision(entityAABB)) {
            if (worldObj.getCollisionBoxes(entityAABB).isEmpty()) {
                if (!worldObj.containsAnyLiquid(entityAABB)) {
                    int x = MathHelper.floor_double(posX);
                    int y = MathHelper.floor_double(entityAABB.minY);
                    int z = MathHelper.floor_double(posZ);
                    BlockPos bp = new BlockPos(x, y, z);
                    if (isHardenedClay(bp) || isSandOrGrassBlock(bp)) {
                        if (worldObj.getLight(bp) > 8) {
							Reptiles.proxy.info("Spawning turtle ***");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
    }

    @Override
    public void onLivingUpdate() {
        if (worldObj.isRemote) {
            turtleTimer = Math.max(0, turtleTimer - 1);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate() {
		super.onUpdate();
	}

    private boolean isFavoriteFood(ItemStack itemstack) {
        return (itemstack != null && itemstack.getItem() == Items.CARROT);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack != null && (itemStack.getItem() instanceof ItemFood && isFavoriteFood(itemStack));
    }

    // taming stuff //////////////////
    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand, ItemStack itemstack) {
        if (isTamed()) {
            if (itemstack != null) {
                if (itemstack.getItem() instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) itemstack.getItem();
                    if (isFavoriteFood(itemstack) && dataManager.get(health) < maxHealth) {
                        if (!entityplayer.capabilities.isCreativeMode) {
                            --itemstack.stackSize;
                        }

                        heal((float) itemfood.getHealAmount(itemstack));

						System.out.println("isTamed and isFavoriteFood");
                        return true;
                    }
                }
            }
			System.out.println("isTamed but not isFavoriteFood");
            if (isOwner(entityplayer) && !worldObj.isRemote && !isBreedingItem(itemstack)) {
                aiSit.setSitting(!isSitting());
                isJumping = false;
                navigator.clearPathEntity();
                setAttackTarget(null);
            }
        } else if (itemstack != null && itemstack.getItem() == Items.APPLE) {
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }

            if (!this.worldObj.isRemote) {
                if (rand.nextInt(3) == 0) {
                    setTamed(true);
                    navigator.clearPathEntity();
                    setAttackTarget(null);
                    aiSit.setSitting(true);
                    setHealth(maxHealth);
                    setOwnerId(entityplayer.getUniqueID());
                    playTameEffect(true);
                    worldObj.setEntityState(this, (byte) 7);
                } else {
                    playTameEffect(false);
                    worldObj.setEntityState(this, (byte) 6);
                }
            }
			System.out.println("taming a turtle");
            return true;
        }
		System.out.println("passing on to super");
        return super.processInteract(entityplayer, hand, itemstack);
    }

    @Override
    public boolean canMateWith(EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        } else if (!isTamed()) {
            return false;
        } else if (!(entityAnimal instanceof EntityTurtle)) {
            return false;
        } else {
            EntityTurtle t = (EntityTurtle) entityAnimal;
            return t.isTamed() && (!t.isSitting() && (isInLove() && t.isInLove()));
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable var1) {
        return this.spawnBabyAnimal(var1);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
        } else {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }
    }

}
