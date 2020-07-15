package com.tp.dto.admin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by truon on 19-07-2017.
 */

public class ResponseSSODTO {
    public List<String> messages;
    public Data data;

    public ResponseSSODTO(String message) {
        this.messages = Arrays.asList(message);
    }

    public ResponseSSODTO(List<String> messages) {
        this.messages = messages;
    }

    public ResponseSSODTO(List<MenuDTO> menu, UserDTO profile) {
        this.data = new Data(menu, profile);
    }

    public ResponseSSODTO(String message, List<MenuDTO> menu, UserDTO profile) {
        this.messages = Arrays.asList(message);
        this.data = new Data(menu, profile);
    }

    public ResponseSSODTO(List<String> messages, List<MenuDTO> menu, UserDTO profile) {
        this.messages = messages;
        this.data = new Data(menu, profile);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = new Data();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public class Data {
        private List<MenuDTO> menu;
        private UserDTO profile;

        public Data() {
        }

        public Data(List<MenuDTO> menu, UserDTO profile) {
            this.menu = menu;
            this.profile = profile;
        }

        public List<MenuDTO> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuDTO> menu) {
            this.menu = menu;
        }

        public UserDTO getProfile() {
            return profile;
        }

        public void setProfile(UserDTO profile) {
            this.profile = profile;
        }
    }
}
