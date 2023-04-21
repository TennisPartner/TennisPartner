package com.tennisPartner.tennisP.user;

public enum UserGrade {

    COMMON("ROLE_COMMON");

    private final String grade;

    UserGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}
