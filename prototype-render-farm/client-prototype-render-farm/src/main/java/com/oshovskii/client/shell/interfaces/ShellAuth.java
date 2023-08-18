package com.oshovskii.client.shell.interfaces;

public interface ShellAuth {
    String login(String username, String password);
    String logout();
    String getCurrentAccessToken();

    String getCurrentRefreshToken();
}
