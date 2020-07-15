package com.tp.dto;

import java.util.List;

public class TreeNodeDTO {

    private String label;
    private String id;
    private String parentId;
    private List<TreeNodeDTO> childObject;

    public TreeNodeDTO(String label, String id, List<TreeNodeDTO> childObject){
        this.label = label;
        this.id = id;
        this.childObject = childObject;
    }
    public TreeNodeDTO(String label, String id, String parentId, List<TreeNodeDTO> childObject){
        this.label = label;
        this.id = id;
        this.parentId = parentId;
        this.childObject = childObject;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNodeDTO> getChildObject() {
        return childObject;
    }

    public void setChildObject(List<TreeNodeDTO> childObject) {
        this.childObject = childObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
