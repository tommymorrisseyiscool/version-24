package rigmod;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RigPayload() implements CustomPayload {
    public static final Id<RigPayload> ID = new Id<>(Identifier.of("rigmod", "rig"));
    public static final PacketCodec<RegistryByteBuf, RigPayload> CODEC = PacketCodec.unit(new RigPayload());
    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
