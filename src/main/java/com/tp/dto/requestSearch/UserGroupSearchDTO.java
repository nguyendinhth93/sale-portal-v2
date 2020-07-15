package com.tp.dto.requestSearch;


/**
 * Created by user on 21/08/2017.
 */
public class UserGroupSearchDTO extends RequestSearchDTO {
    private String userName;
    private Long groupId;

    public UserGroupSearchDTO(String userName, Long groupId) {
        this.userName = userName;
        this.groupId = groupId;
    }

    public UserGroupSearchDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
