package net.superscary.fluxmachines.api.data;

import com.google.common.base.Preconditions;
import net.superscary.fluxmachines.core.util.block.FMBlockStates;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public interface DataLinkInteract {

    ArrayList<PropertyComponent<?>> getLinkedData ();

    void setLinkedData (ArrayList<PropertyComponent<?>> data);

    default ArrayList<PropertyComponent<?>> allowedData (@Nullable ArrayList<PropertyComponent<?>> extendedData) {
        return new ArrayList<>() {
            {
                if (extendedData != null) addAll(extendedData);
            }
        };
    }

    default void addLinkedData (PropertyComponent<?>... data) {
        addLinkedData(new ArrayList<>(Arrays.asList(data)));
    }

    default void addLinkedData (ArrayList<PropertyComponent<?>> data) {
        Preconditions.checkArgument(getLinkedData().stream().anyMatch(data::contains), "Data cannot contain existing data.");

        var list = PropertyComponent.createEmptyList();
        for (int i = 0; i < data.size(); i++) {
            if (!allowedData(null).contains(data.get(i))) {
                list.add(data.get(i));
            }
        }

        var linkedData = getLinkedData();
        linkedData.addAll(list);
        setLinkedData(linkedData);
    }

}
