package app.responsability.models.component;

public enum Sex {
    MALE,
    FEMALE;

    public static Sex getSexByStr(String sex) {
        if(sex.toUpperCase().equals("MALE")) {
            return MALE;
        }
        else if(sex.toUpperCase().equals("FEMALE")) {
            return FEMALE;
        }
        return null;
    }
}
