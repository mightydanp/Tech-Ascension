package mightydanp.techcore.common.jsonconfig.trait.item;

import mightydanp.techcore.common.jsonconfig.icons.DefaultTextureIcon;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.resources.ResourceLocation;

public enum DefaultItemTrait implements IItemTrait{
    stick(new ResourceLocation("stick").toString(), 0x854F2B, 16, DefaultTextureIcon.WOOD.getName(), 0.852D, null, null, null),
    plant_fiber(new ResourceLocation(Ref.mod_id, "plant_fiber").toString(), 0x3c5817, 8, DefaultTextureIcon.LEAF.getName(), 0.0003, null, 0.3048, null),
    diamond(new ResourceLocation("diamond").toString(), 0x854F2B, 10, DefaultTextureIcon.DIAMOND.getName(), 0.852D, null, null, null)
    ;

    final String registry;
    final int color;
    final int maxDamage;
    final String textureIcon;
    final Double pounds;
    final Double kilograms;
    final Double meter;
    final Double yard;

    DefaultItemTrait(String registry, int color, int maxDamage, String textureIcon, Double pounds, Double kilograms, Double meter, Double yard){
        this.registry = registry;
        this.color = color;
        this.maxDamage = maxDamage;
        this.textureIcon = textureIcon;
        this.pounds = pounds;
        this.kilograms = kilograms;
        this.meter = meter;
        this.yard = yard;
    }

    @Override
    public String getRegistry() {
        return registry;
    }

    @Override
    public Double getPounds() {
        return pounds;
    }

    @Override
    public Double getKilograms() {
        return kilograms;
    }

    @Override
    public Double getMeters() {
        return meter;
    }

    @Override
    public Double getYards() {
        return yard;
    }

    @Override
    public Integer getColor() {
        return color;
    }

    @Override
    public Integer getMaxDamage() {
        return maxDamage;
    }

    @Override
    public String getTextureIcon() {
        return textureIcon;
    }
}