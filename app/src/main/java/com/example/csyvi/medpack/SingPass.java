package com.example.csyvi.medpack;

/**
 * The type Sing pass.
 */
public class SingPass {
    private String loginId;
    private String password;
    private String token;

    /**
     * Instantiates a new Sing pass.
     *
     * @param loginId  the login id
     * @param password the password
     * @param token    the token
     */
    public SingPass(String loginId, String password, String token) {
        this.loginId = loginId;
        this.password = password;
        this.token = token;
    }

    /**
     * Gets login id.
     *
     * @return the login id
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets login id.
     *
     * @param loginId the login id
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
