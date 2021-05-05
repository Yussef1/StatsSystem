package de.javaexceptions.statssystem.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(String displayName, Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
    }

    public ItemBuilder setLores(String... lores) {
        itemMeta.setLore(Arrays.asList(lores));
        return this;
    }

    public ItemBuilder setLores(List<String> lores) {
        itemMeta.setLore((lores));
        return this;
    }

    public ItemBuilder removeItemFlags() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}