package net.enderman999517.tutorialmod.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.enderman999517.tutorialmod.world.tree.ModFoliagePlacerTypes;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ChestnutFoliagePlacer extends FoliagePlacer {
    public static final Codec<ChestnutFoliagePlacer> CODEC = RecordCodecBuilder.create(chestnutFoliagePlacerInstance ->
            fillFoliagePlacerFields(chestnutFoliagePlacerInstance).and(Codec.intRange(0, 12).fieldOf("height")
                    .forGetter(instance -> instance.height)).apply(chestnutFoliagePlacerInstance, ChestnutFoliagePlacer::new));
    private final int height;

    public ChestnutFoliagePlacer(IntProvider radius, IntProvider offset,int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.CHESTNUT_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {


        /*
        radius is how many blocks into x and z direction
        y is how much offset in y direction from treeNode.getCenter()
        if y is dependent on i, also offsets each new layer in y direction
        */
        //places main trunk
        generateSquareWithHangingLeaves(world, placer, random, config, treeNode.getCenter().up(0), 3, -2, treeNode.isGiantTrunk(), 0.6f, 0.8f);

        //creates a cone of radius rad
        for (int rad = 3; rad > 0;) {
            for (int y = -2; y < 1; y++) {
                generateSquare(world, placer, random, config, treeNode.getCenter(), rad, y, treeNode.isGiantTrunk());
                rad--;
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
