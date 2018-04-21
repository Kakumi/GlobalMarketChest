package fr.epicanard.globalmarketchest.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.epicanard.globalmarketchest.utils.Utils;

public class InterfacesLoader {
  private static InterfacesLoader INSTANCE;
  private Map<String, ItemStack[]> interfaces;

  private InterfacesLoader() {
  }

  public static InterfacesLoader getInstance() {
    if (INSTANCE == null)
      INSTANCE = new InterfacesLoader();
    return INSTANCE;
  }

  /**
   * Get interfaces already loaded
   * 
   * @return
   */
  public Map<String, ItemStack[]> getInterfaces() {
    return this.interfaces;
  }

  /**
   * Get list ItemStack for one interface
   * 
   * @param interfaceName
   * @return
   */
  public ItemStack[] getInterface(String interfaceName) {
    return (this.interfaces != null) ? this.interfaces.get(interfaceName) : null;
  }

  /**
   * Load interfaces Create a map, where the key is the name of the interface
   * and the value a list of ItemStack Fill empty space with background item
   * 
   * @param interfaceConfig
   * @param reload
   * @return
   */
  public Map<String, ItemStack[]> loadInterfaces(YamlConfiguration interfaceConfig, Boolean reload) {
    if (reload == false && this.interfaces != null)
      return this.interfaces;

    this.interfaces = new HashMap<String, ItemStack[]>();
    Set<String> interfacesName = interfaceConfig.getKeys(false);

    for (String name : interfacesName) {
      ItemStack[] itemsStack = new ItemStack[54];
      Map<Integer, String> items = this.parseItems(interfaceConfig.getConfigurationSection(name + ".Items").getValues(false));

      for (int i = 0; i < 54; i++)
        itemsStack[i] = Utils.getInstance().getButton(items.get(i));
      this.interfaces.put(name, itemsStack);
    }
    return this.interfaces;
  }

  /**
   * Overload "loadInterfaces" method to set param reload default to false
   * 
   * @param interfaceConfig
   * @return
   */
  public Map<String, ItemStack[]> loadInterfaces(YamlConfiguration interfaceConfig) {
    return this.loadInterfaces(interfaceConfig, false);
  }

  /**
   * Parse items defined for the interface (ex: "2-5" to [2, 3, 4, 5] of the
   * current item) Generate map, to each position number assign an item
   * 
   * @param its
   * @return
   */
  private Map<Integer, String> parseItems(Map<String, Object> its) {
    Map<Integer, String> items = new HashMap<Integer, String>();

    its.forEach((key, value) -> {
      try {
        items.put(Integer.parseInt(key), (String) value);
      } catch (NumberFormatException e) {
        String[] nums = key.split("-");
        for (Integer i = Integer.parseInt(nums[0]); i <= Integer.parseInt(nums[1]); i++)
          items.put(i, (String) value);
      }
    });
    return items;
  }

}
