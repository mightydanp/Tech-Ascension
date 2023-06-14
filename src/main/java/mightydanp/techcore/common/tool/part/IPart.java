package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconCodec;
import net.minecraft.world.item.crafting.Ingredient;

public interface IPart {
    //Map<> getPart();
    Ingredient getIngredient();
    Pair<String, TextureIconCodec> getTextureFlag();
    Integer getColor();
    Integer getDurability();
    String getElement();
    Float getWeight();
    Integer getTemperature();
    Integer getMeltingPoint();
    Integer getBoilingPoint();
}
