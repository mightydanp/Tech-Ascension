package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import java.util.List;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreVeinGenFeatureConfig implements IFeatureConfig {
    public String name;
    public Integer rarity;
    public int minHeight;
    public int maxHeight;
    public List<String> biomes;
    public List<Pair<String, Integer>> blocksAndChances;

    public static final Codec<SmallOreVeinGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter((a) -> a.rarity),
            Codec.INT.fieldOf("minHeight").forGetter((a) -> a.minHeight),
            Codec.INT.fieldOf("maxHeight").forGetter((a) -> a.maxHeight),
            Codec.STRING.listOf().fieldOf("biomes").forGetter((a) -> a.biomes),
            Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("blocks_and_chances").forGetter((a) -> a.blocksAndChances)
    ).apply(p_236568_0_, SmallOreVeinGenFeatureConfig::new));



    public SmallOreVeinGenFeatureConfig(String nameIn, int rarityIn, int minHeightIn, int maxHeightIn, List<String> biomesIn, List<Pair<String, Integer>> blocksAndChancesIn) {
        name = nameIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        biomes = biomesIn;
        blocksAndChances = blocksAndChancesIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}