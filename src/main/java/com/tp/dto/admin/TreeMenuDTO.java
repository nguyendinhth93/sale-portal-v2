package com.tp.dto.admin;

import java.util.List;

import com.tp.dto.BaseDTO;

/**
 * Created by Nhat on 20/06/2017.
 */
public class TreeMenuDTO extends BaseDTO {
    private Long id;
    private String name;
    private String code;
    private String path;
    private Long parentId;
    private Integer stt;
    private String icon;

    private List<TreeMenuDTO> childMenus;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }


    public List<TreeMenuDTO> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<TreeMenuDTO> childMenus) {
        this.childMenus = childMenus;
    }

    public boolean getNull()
    {
        if(this.id==null&&this.name==null)
            return false;
        return true;
    }

}
