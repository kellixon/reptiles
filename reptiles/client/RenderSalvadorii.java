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
package com.reptiles.client;

import com.reptiles.common.EntitySalvadorii;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSalvadorii<T extends EntitySalvadorii> extends RenderLiving<T> {

    private static final ResourceLocation skin = new ResourceLocation("reptilemod", "textures/entity/reptiles/salvadorii.png");

    public RenderSalvadorii(RenderManager rm) {
        super(rm, new ModelSalvadorii(), 0.0f);
    }

    @Override
    protected void preRenderCallback(T entityliving, float f) {
        float scaleFactor = entityliving.getScaleFactor();
        GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
        super.preRenderCallback(entityliving, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(T t) {
        return skin;
    }
}
