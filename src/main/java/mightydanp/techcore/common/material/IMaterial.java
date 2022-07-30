package mightydanp.techcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.flag.IMaterialFlag;

import java.util.List;

public interface IMaterial {
    void save(TCMaterial material, List<TCMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    void clientRenderLayerInit(TCMaterial material);

    void registerColorForBlock(TCMaterial material);

    void registerColorForItem(TCMaterial material);
}