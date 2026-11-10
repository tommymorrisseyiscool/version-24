package rigmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class RigMod implements ModInitializer {
    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(RigPayload.ID, RigPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RigPayload.ID, (payload, ctx) -> rig(ctx.player()));
    }

    private static void rig(ServerPlayerEntity p) {
        var world = p.getServerWorld();
        List<DispenserBlockEntity> found = new ArrayList<>();
        for (BlockPos pos : BlockPos.iterate(p.getBlockPos().add(-6, -4, -6), p.getBlockPos().add(6, 4, 6))) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof DispenserBlockEntity d) found.add(d);
        }
        if (found.size() < 2) { p.sendMessage(Text.literal("Need 2 dispensers nearby"), true); return; }

        found.sort(Comparator.comparingDouble(d -> d.getPos().getSquaredDistance(p.getPos())));
        double yaw = Math.toRadians(p.getYaw());
        double rx = -Math.cos(yaw), rz = -Math.sin(yaw); // player's right-hand direction
        List<DispenserBlockEntity> two = new ArrayList<>(found.subList(0, 2));
        two.sort(Comparator.comparingDouble(d -> d.getPos().getX() * rx + d.getPos().getZ() * rz));
        DispenserBlockEntity left = two.get(0), right = two.get(1);

        int r = 1 + world.random.nextInt(9);                    // right: 1-9
        int l = (r == 1) ? 1 : 1 + world.random.nextInt(r - 1); // left: 1..r-1, or 1 if r == 1

        for (int i = 0; i < 9; i++) { left.setStack(i, ItemStack.EMPTY); right.setStack(i, ItemStack.EMPTY); }
        left.setStack(0, new ItemStack(Items.DIAMOND, l));
        for (int i = 0; i < r; i++) right.setStack(i, new ItemStack(Items.TOTEM_OF_UNDYING, 1));
        left.markDirty(); right.markDirty();

        p.sendMessage(Text.literal("Rigged: left " + l + " | right " + r), true);
    }
}
