package mightydanp.industrialcore.common.handler.generation;

import com.google.common.base.Preconditions;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.industrialcore.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.industrialcore.common.world.gen.feature.BlocksInWaterGenFeature;
import mightydanp.industrialcore.common.world.gen.feature.BlocksInWaterGenFeatureConfig;
import mightydanp.industrialcore.common.world.gen.feature.RandomSurfaceGenFeature;
import mightydanp.industrialcore.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 2/18/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlantGenerationHandler {
    public static final RegistryObject<Feature<BlocksInWaterGenFeatureConfig>> blockInWater = RegistryHandler.createFeature("block_in_water", () -> new BlocksInWaterGenFeature(BlocksInWaterGenFeatureConfig.CODEC));

    public static final RegistryObject<Feature<RandomSurfaceGenFeatureConfig>> randomSurface = RegistryHandler.createFeature("random_surface", () -> new RandomSurfaceGenFeature(RandomSurfaceGenFeatureConfig.CODEC));

    private static final Map<String, MapWrapper> blockInWaterGenerationList = new HashMap<>();
    private static final Map<String, MapWrapper> randomSurfaceGenList = new HashMap<>();

    public static void addRegistryBlockInWaterGenerate(BlocksInWaterGenFeatureConfig config){

        Holder<ConfiguredFeature<BlocksInWaterGenFeatureConfig, ?>> featureHolder = register(config.name, new ConfiguredFeature<>(blockInWater.get(), config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        //list.add(CountPlacement.of(config.rarity));


        //Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, featureHolder, list.toArray(new PlacementModifier[0]));
        ((BlocksInWaterRegistry)IndustrialTech.configSync.blocksInWater.getFirst()).register(config);
        blockInWaterGenerationList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.invalidBiomes, config.invalidBiomes));
        //Registry.register(registry, new ResourceLocation(Ref.mod_id, config.name), topWaterCropFeature);;
    }

    public static void addBlockInWaterGenerate(String nameIn, int rarityIn, int heightIn, boolean shallowWaterIn, boolean goesAboveWaterIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, List<Block>  validBlocksIn, Block topStateIn, Block bellowStateIn){
        List<String> validBlocks = new ArrayList<>();

        String topState = "";
        String bellowState = "";

        validBlocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                validBlocks.add(block.getRegistryName().toString());
            }
        });

        if (topStateIn.getRegistryName() != null) {
            topState = topStateIn.getRegistryName().toString();
        }

        if (bellowStateIn.getRegistryName() != null) {
            bellowState = bellowStateIn.getRegistryName().toString();
        }

        BlocksInWaterGenFeatureConfig config = new BlocksInWaterGenFeatureConfig(nameIn, rarityIn, heightIn, shallowWaterIn, goesAboveWaterIn, dimensions, validBiomes, invalidBiomes, validBlocks, topState, bellowState);

        Holder<ConfiguredFeature<BlocksInWaterGenFeatureConfig, ?>> featureHolder = register(config.name, new ConfiguredFeature<>(blockInWater.get(), config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, featureHolder, list.toArray(new PlacementModifier[0]));
        ((BlocksInWaterRegistry)IndustrialTech.configSync.blocksInWater.getFirst()).register(config);
        blockInWaterGenerationList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.invalidBiomes, config.invalidBiomes));
    }

    public static void addRegistryRandomSurfaceGenerate(RandomSurfaceGenFeatureConfig config){
        Holder<ConfiguredFeature<RandomSurfaceGenFeatureConfig, ?>> featureHolder = register(config.name, new ConfiguredFeature<>(randomSurface.get(), config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, featureHolder, list.toArray(new PlacementModifier[0]));
        ((RandomSurfaceRegistry)IndustrialTech.configSync.randomSurface.getFirst()).register(config);
        randomSurfaceGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    public static void addRandomSurfaceGenerate(String nameIn, int rarityIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, List<Block>  validBlocksIn, List<Block> blocksIn){
        List<String> validBlocks = new ArrayList<>();
        List<String> blocks = new ArrayList<>();

        validBlocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                validBlocks.add(block.getRegistryName().toString());
            }
        });

        blocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                blocks.add(block.getRegistryName().toString());
            }
        });

        RandomSurfaceGenFeatureConfig config = new RandomSurfaceGenFeatureConfig(nameIn, rarityIn, dimensions, validBiomes, invalidBiomes, blocks, validBlocks);

        Holder<ConfiguredFeature<RandomSurfaceGenFeatureConfig, ?>> oreVeinFeature = register(config.name, new ConfiguredFeature<>(randomSurface.get(), config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, oreVeinFeature, list.toArray(new PlacementModifier[0]));
        ((RandomSurfaceRegistry)IndustrialTech.configSync.randomSurface.getFirst()).register(config);
        randomSurfaceGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        if(blockInWaterGenerationList.size() > 0) {
            BiomeGenerationSettingsBuilder builder = event.getGeneration();
            blockInWaterGenerationList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        if(randomSurfaceGenList.size() > 0) {
            BiomeGenerationSettingsBuilder builder = event.getGeneration();
            randomSurfaceGenList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        return true;
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String id, ConfiguredFeature<FC, F> cf) {
        ResourceLocation realId = new ResourceLocation(Ref.mod_id, id);
        Preconditions.checkState(!BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(realId), "Duplicate ID: %s", id);
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, realId.toString(), cf);
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        ResourceLocation realID = new ResourceLocation(Ref.mod_id, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(realID))
            throw new IllegalStateException("Placed Feature ID: \"" + realID.toString() + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(Holder.hackyErase(feature), List.of(placementModifiers)));
    }

    record MapWrapper(Holder<PlacedFeature> feature, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes){}

}