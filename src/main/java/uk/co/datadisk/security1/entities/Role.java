package uk.co.datadisk.security1.entities;

public class Role {

    private String roleName;

    public Role() { }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
