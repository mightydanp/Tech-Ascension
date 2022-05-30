package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.holder.MCMaterialHolder;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RockBlock extends Block {
    public String stoneLayerBlock;

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)};

    public RockBlock(String stoneLayerBlockIn, Properties properties) {
        super(properties.noCollission().strength(0.0f));
        stoneLayerBlock = stoneLayerBlockIn;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[0];
    }
}
