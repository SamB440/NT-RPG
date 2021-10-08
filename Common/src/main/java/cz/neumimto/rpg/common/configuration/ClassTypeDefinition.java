package cz.neumimto.rpg.common.configuration;

import com.electronwill.nightconfig.core.conversion.Path;
import com.typesafe.config.Optional;

import java.util.Objects;

public class ClassTypeDefinition implements Comparable<ClassTypeDefinition> {

    @Path("PrimaryColor")
    private String primaryColor;

    @Path("SecondaryColor")
    private String secondaryColor;

    @Path("DyeColor")
    private String dyeColor;

    @Path("Changeable")
    private boolean changeable;

    @Path("Order")
    private int order;

    @Path("DefaultClass")
    @Optional
    private String defaultClass;

    @Path("Model")
    @Optional
    private Integer modelId;


    public ClassTypeDefinition(String primaryColor, String secondaryColor, String dyeColor, boolean changeable, int order, int modelId) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.changeable = changeable;
        this.dyeColor = dyeColor;
        this.order = order;
        this.modelId = modelId;
    }

    public ClassTypeDefinition() {
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public String getDyeColor() {
        return dyeColor;
    }

    public int getOrder() {
        return order;
    }

    public String getDefaultClass() {
        return defaultClass;
    }

    public Integer getModelId() {
        return modelId;
    }

    @Override
    public int compareTo(ClassTypeDefinition o) {
        return order - o.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTypeDefinition that = (ClassTypeDefinition) o;
        return changeable == that.changeable &&
                order == that.order &&
                primaryColor.equals(that.primaryColor) &&
                secondaryColor.equals(that.secondaryColor) &&
                Objects.equals(dyeColor, that.dyeColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryColor, secondaryColor, dyeColor, changeable, order);
    }
}
