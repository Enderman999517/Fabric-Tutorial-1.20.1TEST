package net.enderman999517.tutorialmod.world.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.enderman999517.tutorialmod.world.tree.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ChestnutTrunkPlacer extends TrunkPlacer {
    public static final Codec<ChestnutTrunkPlacer> CODEC = RecordCodecBuilder.create(chestnutTrunkPlacerInstance ->
            fillTrunkPlacerFields(chestnutTrunkPlacerInstance).apply(chestnutTrunkPlacerInstance, ChestnutTrunkPlacer::new));

    public ChestnutTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.CHESTNUT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {

        setToDirt(world, replacer, random, startPos.down(), config);
        int height_ = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 1) + random.nextBetween(secondRandomHeight - 5, secondRandomHeight + 5);

        for (int i = 0; i < height_; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);

            //sets branch length scale
            int branchLength = 4;
            float lengthScaleFactor = 0.04f;
            int lengthScale = Math.round (height_ * lengthScaleFactor * branchLength);

            if (i % 2 == 0 && random.nextBoolean()) {
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= lengthScale; x++) {
                        replacer.accept(startPos.up(i).offset(Direction.NORTH, x),(BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.NORTH, x)).with(PillarBlock.AXIS, Direction.Axis.Z)));
                        if (i > (height_ - 5)) break;
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= lengthScale; x++) {
                        replacer.accept(startPos.up(i).offset(Direction.SOUTH, x),(BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.SOUTH, x)).with(PillarBlock.AXIS, Direction.Axis.Z)));
                        if (i > (height_ - 5)) break;
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= lengthScale; x++) {
                        replacer.accept(startPos.up(i).offset(Direction.EAST, x),(BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.EAST, x)).with(PillarBlock.AXIS, Direction.Axis.X)));
                        if (i > (height_ - 5)) break;
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= lengthScale; x++) {
                        replacer.accept(startPos.up(i).offset(Direction.WEST, x),(BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.WEST, x)).with(PillarBlock.AXIS, Direction.Axis.X)));
                        if (i > (height_ - 5)) break;
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height_), 0, false));
    }
}
