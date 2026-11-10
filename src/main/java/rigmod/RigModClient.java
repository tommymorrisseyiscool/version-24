package rigmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RigModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding key = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.rigmod.rig", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.rigmod"));
        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            while (key.wasPressed()) ClientPlayNetworking.send(new RigPayload());
        });
    }
}
