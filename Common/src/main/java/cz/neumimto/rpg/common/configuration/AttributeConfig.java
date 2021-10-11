package cz.neumimto.rpg.common.configuration;

import com.electronwill.nightconfig.core.conversion.Conversion;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.PreserveNotNull;
import cz.neumimto.rpg.common.configuration.adapters.PropertiesMapAdapter;

import java.util.Map;

public class AttributeConfig {

    @Path("Id")
    private String id;

    @Path("Name")
    private String name;

    @Path("MaxValue")
    @PreserveNotNull
    private int maxValue;

    @Path("Hidden")
    @PreserveNotNull
    private boolean hidden;

    @Path("Properties")
    @Conversion(PropertiesMapAdapter.class)
    private Map<Integer, Float> propBonus;

    @Path("ItemType")
    private String itemType;

    @Path("Description")
    private String description;

    @Path("HexColor")
    private String hexColor;

    @Path("Model")
    private Integer model;

    public AttributeConfig(String id, String name, int maxValue, boolean hidden, Map<Integer, Float> propBonus, String itemType, String description) {
        this.id = id;
        this.name = name;
        this.maxValue = maxValue;
        this.hidden = hidden;
        this.propBonus = propBonus;
        this.itemType = itemType;
        this.description = description;
    }

    public AttributeConfig() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Map<Integer, Float> getPropBonus() {
        return propBonus;
    }

    public String getItemType() {
        return itemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }
}
