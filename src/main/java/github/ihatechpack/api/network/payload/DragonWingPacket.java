package github.ihatechpack.api.network.payload;

import github.ihatechpack.api.Res;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:01
 */
public record DragonWingPacket(boolean enable) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DragonWingPacket> TYPE = new CustomPacketPayload.Type<>(Res.rl("dragon_wing_packet"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, DragonWingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            DragonWingPacket::enable,
            DragonWingPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
