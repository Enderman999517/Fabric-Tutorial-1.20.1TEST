package net.enderman999517.tutorialmod.entity.custom;

import net.enderman999517.tutorialmod.block.ModBlocks;
import net.enderman999517.tutorialmod.block.custom.DiceBlock;
import net.enderman999517.tutorialmod.entity.ModEntities;
import net.enderman999517.tutorialmod.item.ModItems;
import net.enderman999517.tutorialmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class DiceProjectileEntity extends ThrownItemEntity {
    public DiceProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DiceProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.DICE_PROJECTILE, livingEntity, world);
    }

    //don't technically have to do this but is a good idea in case
    @Override
    protected Item getDefaultItem() {
        return ModItems.DICE;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.getWorld().setBlockState(getBlockPos(), ((DiceBlock) ModBlocks.DICE_BLOCK).getRandomBlockState(), 3);
        }
        this.discard();
        super.onBlockHit(blockHitResult);
    }
}
