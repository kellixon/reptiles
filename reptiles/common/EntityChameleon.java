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

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import java.util.UUID;

public final class EntityChameleon extends EntityLizard {

    public EntityChameleon(World world) {
        super(world);
        setSize(0.25F, 0.25F);
        setTamed(false);
    }

    @Override
    public EntityAnimal spawnBabyAnimal(EntityAgeable entityageable) {
        EntityChameleon c = new EntityChameleon(worldObj);
        UUID uuid = this.getOwnerId();

        if (uuid != null) {
            c.setOwnerId(uuid);
            c.setTamed(true);
        }
        Reptiles.proxy.info("Spawned entity of type " + getClass().toString());
        return c;
    }

}
