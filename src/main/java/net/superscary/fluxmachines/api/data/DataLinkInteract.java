package net.superscary.fluxmachines.api.data;

import java.util.List;

public interface DataLinkInteract {

    List<DataComponent> getLinkedData();

    void setLinkedData (List<DataComponent> data);

}
