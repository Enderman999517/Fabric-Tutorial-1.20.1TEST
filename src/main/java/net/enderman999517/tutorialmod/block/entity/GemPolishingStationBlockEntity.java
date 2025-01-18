package net.enderman999517.tutorialmod.block.entity;

import net.enderman999517.tutorialmod.recipe.GemPolishingRecipe;
import net.enderman999517.tutorialmod.screen.GemPolishingScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// custom recipe by koal4git on GitHub and the help of the kaupenjoe discord

public class GemPolishingStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public GemPolishingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemPolishingStationBlockEntity.this.progress;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemPolishingStationBlockEntity.this.progress = value;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public ItemStack getRenderStack() {
        if(this.getStack(OUTPUT_SLOT).isEmpty()) {
            return this.getStack(INPUT_SLOT);
        } else {
            return this.getStack(OUTPUT_SLOT);
        }
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Gem Polishing Station");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("gem_polishing_station.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("gem_polishing_station.progress");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GemPolishingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private void craftItem() {
        Optional<GemPolishingRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getOutput(null);

        this.removeStack(INPUT_SLOT, 1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().getOutput(null).getCount()));
    }

    private boolean hasRecipe() {
        Optional<GemPolishingRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getOutput(getWorld().getRegistryManager());

        return canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<GemPolishingRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i=0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        return getWorld().getRecipeManager().getFirstMatch(GemPolishingRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}







//import net.enderman999517.tutorialmod.item.ModItems;
//import net.enderman999517.tutorialmod.recipe.GemPolishingRecipe;
//import net.enderman999517.tutorialmod.screen.GemPolishingScreenHandler;
//import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.Inventories;
//import net.minecraft.inventory.SimpleInventory;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.registry.DynamicRegistryManager;
//import net.minecraft.screen.PropertyDelegate;
//import net.minecraft.screen.ScreenHandler;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.text.Text;
//import net.minecraft.util.collection.DefaultedList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.*;
//
//public class GemPolishingStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
//    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
//
//    private static final int INPUT_SLOT = 0;
//    private static final int OUTPUT_SLOT = 1;
//
//    protected final PropertyDelegate propertyDelegate;
//    private int progress;
//    private int maxProgress = 72;
//
//    public GemPolishingStationBlockEntity(BlockPos pos, BlockState state) {
//        super(ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY, pos, state);
//        this.propertyDelegate = new PropertyDelegate() {
//            @Override
//            public int get(int index) {
//                return switch (index) {
//                    case 0 -> GemPolishingStationBlockEntity.this.progress;
//                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress;
//                    default -> 0;
//                };
//            }
//
//            @Override
//            public void set(int index, int value) {
//                switch (index) {
//                    case 0 -> GemPolishingStationBlockEntity.this.progress = value;
//                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress = value;
//                }
//            }
//
//            @Override
//            public int size() {
//                return 2;
//            }
//        };
//    }
//
//    @Override
//    public DefaultedList<ItemStack> getItems() {
//        return inventory;
//    }
//
//    @Override
//    protected void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        Inventories.writeNbt(nbt, inventory);
//        nbt.putInt("gem_polishing_station.progress", progress);
//    }
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        super.readNbt(nbt);
//        Inventories.readNbt(nbt, inventory);
//        progress = nbt.getInt("gem_polishing_station.progress");
//    }
//
//    @Override
//    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
//        packetByteBuf.writeBlockPos(this.pos);
//    }
//
//    @Override
//    public Text getDisplayName() {
//        return Text.literal("Gem Polishing Station");
//    }
//
//    @Override
//    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
//        return new GemPolishingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
//    }
//
//    public void tick(World world, BlockPos pos, BlockState state) {
//        if (world.isClient()) {
//            return;
//        }
//
//        if (isOutputSlotEmptyOrReceivable()) {
//            if (this.hasRecipe(this)) {
//                this.increaseCraftProgress();
//                markDirty(world, pos, state);
//
//                if (hasCraftingFinished()) {
//                    this.craftItem(this);
//                    this.resetProgress();
//                }
//            } else {
//                this.resetProgress();
//            }
//        } else {
//            this.resetProgress();
//            markDirty(world, pos, state);
//        }
//    }
//
//    private void resetProgress() {
//        this.progress = 0;
//    }
//
//    private static void craftItem(GemPolishingStationBlockEntity entity) {
//        SimpleInventory inventory = new SimpleInventory(entity.size());
//        for (int i = 0; i < entity.size(); i++) {
//            inventory.setStack(i, entity.getStack(i));
//        }
//
//        Optional<GemPolishingRecipe> recipe = entity.getWorld().getRecipeManager()
//                .getFirstMatch(GemPolishingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        if (hasRecipe(entity)) {
//            entity.removeStack(INPUT_SLOT, 1);
//
//
//            // TESTING START
//
//            //entity.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(new DynamicRegistryManager.Entry<Recipe>
//            //        (new GemPolishingRecipe(new Identifier("tutorialmod.rawRubyToRuby"),
//            //                new ItemStack(ModItems.RUBY),
//            //                DefaultedList))).getItem(), //?????
//            //        entity.getStack(OUTPUT_SLOT).getCount() + 1 ));
//
//            //entity.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(
//            //        new DynamicRegistryManager.Entry<GemPolishingRecipe>
//            //                (RegistryKey.ofRegistry(Identifier.of("tutorialmod", "tutorialmod/recipe/GemPolishingRecipe")),
//            //                        Registry.registerReference(
//            //                                new GemPolishingRecipe(
//            //                                        new Identifier("tutorialmod.rawRubyToRuby"),
//            //                                        new ItemStack(ModItems.RUBY),
//            //                                DefaultedList.ofSize(1, Ingredient.ofItems(new ItemStack(ModItems.RAW_RUBY)))), 1))).getItem(), //?????
//            //        entity.getStack(OUTPUT_SLOT).getCount() + 1 ));
//
//
//            for (Map.Entry<ItemStack, ItemStack> entry : gemPolishingRecipesMap().entrySet()) {
//                //Object ingredient = entry.getKey();
//                Object output = entry.getValue();
//
//                entity.setStack(OUTPUT_SLOT,
//                        new ItemStack(GemPolishingStationBlockEntity.gemPolishingRecipesMap().get(output).getItem(),
//                                entity.getStack(OUTPUT_SLOT).getCount() + 1));
//
//            }
//
//           // List ingredients = new ArrayList(gemPolishingRecipesMap().keySet());
//           // for (int i = 0; i < ingredients.size(); i++) {
//           //     Object ingredient = ingredients.get(i);
//           // }
//           //
//           // List outputs = new ArrayList(gemPolishingRecipesMap().set());
//           // for (int i = 0; i < ingredients.size(); i++) {
//           //     Object ingredient = ingredients.get(i);
//           // }
//            //
//           // gemPolishingRecipesMap().
//           //
//           // for (int j = 0; j < GemPolishingStationBlockEntity.gemPolishingRecipesMap().size(); j++) {
//           //
//           //     Object ingredient =
//           //     if (Objects.equals(entity.getStack(INPUT_SLOT), new ItemStack(GemPolishingStationBlockEntity.gemPolishingRecipesMap().get(j).getItem()))) {
////
//           //         entity.setStack(OUTPUT_SLOT,
//           //                 new ItemStack(GemPolishingStationBlockEntity.gemPolishingRecipesMap().get(j).getItem(),
//           //                         entity.getStack(OUTPUT_SLOT).getCount() + 1));
////
//           //     } else return;
//           // }
//
//            entity.resetProgress();
//        }
//    }
//
//   // final DynamicRegistryManager entry = world.getRegistryManager();
////
//   // public DynamicRegistryManager getEntry() {
//   //     return entry;
//   // }
//
//    // TESTING END
//
//    public static LinkedHashMap<ItemStack, ItemStack> gemPolishingRecipesMap() {
//
//        //TO USE
//        // duplicate line with first itemstack being the input and the second being the output
//
//        gemPolishingRecipesMap().put(new ItemStack(ModItems.RAW_RUBY), new ItemStack(ModItems.RUBY));
//
//        return gemPolishingRecipesMap();
//    }
//
//
//
//    private static boolean hasRecipe(GemPolishingStationBlockEntity entity) {
//
//        SimpleInventory inventory = new SimpleInventory(entity.size());
//
//        Optional<GemPolishingRecipe> match = entity.getWorld().getRecipeManager()
//                .getFirstMatch(GemPolishingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
//                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput(DynamicRegistryManager.EMPTY).getItem()); //?????
//    }
//
//    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
//        return inventory.getStack(OUTPUT_SLOT).getItem() == output || inventory.getStack(OUTPUT_SLOT).isEmpty();
//    }
//
//    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
//       return inventory.getStack(OUTPUT_SLOT).getMaxCount() > inventory.getStack(OUTPUT_SLOT).getCount();
//    }
//
//    private boolean isOutputSlotEmptyOrReceivable() {
//        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
//    }
//
//    private boolean hasCraftingFinished() {
//        return progress >= maxProgress;
//    }
//
//    private void increaseCraftProgress() {
//        progress++;
//    }
//}

