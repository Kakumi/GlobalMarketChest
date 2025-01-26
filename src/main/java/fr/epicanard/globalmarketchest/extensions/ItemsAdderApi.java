package fr.epicanard.globalmarketchest.extensions;

import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

@Getter
public class ItemsAdderApi {
    private final boolean enabled;

    public ItemsAdderApi(JavaPlugin plugin) {
        this.enabled = plugin.getServer().getPluginManager().getPlugin("ItemsAdder") != null;

        if (this.enabled) {
            plugin.getLogger().info("<aqua>ItemsAdder: Enabled");
        } else {
            plugin.getLogger().info("ItemsAdder: Not found");
        }
    }

    public @Nullable ItemStack getItem(String id) {
        if (!this.enabled) {
            return null;
        }

        CustomStack customStack = CustomStack.getInstance(id);
        if (customStack == null) {
            return null;
        }

        return customStack.getItemStack();
    }

    public @Nullable String getNamespaceId(ItemStack itemStack) {
        if (!this.enabled) {
            return null;
        }

        CustomStack customStack = CustomStack.byItemStack(itemStack);
        if (customStack == null) {
            return null;
        }

        return customStack.getNamespacedID();
    }
}
