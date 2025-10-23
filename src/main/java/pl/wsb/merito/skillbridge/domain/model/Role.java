package pl.wsb.merito.skillbridge.domain.model;

public enum Role {
    STUDENT("STUDENT"),
    TUTOR("TUTOR");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String toString(){
        return String.valueOf(value);
    }

    public static Role getRole(String givenRole) {
        if (givenRole == null) {
            return Role.STUDENT;
        }
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(givenRole)) {
                return role;
            }
        }
        return Role.STUDENT;
    }
}
