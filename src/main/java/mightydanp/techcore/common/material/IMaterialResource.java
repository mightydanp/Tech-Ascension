package mightydanp.techcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.flag.IMaterialFlag;

import java.util.List;

public interface IMaterialResource {
    void saveResources(TCMaterial material, List<TCMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag);
}