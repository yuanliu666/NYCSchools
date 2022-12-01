package com.liu966.nycschools.data.domain;

/**
 * A simplified school class to use in app.
 */
public class School {

  public String dbn;
  public String school_name;

  public School(String dbn, String school_name) {
    this.dbn = dbn;
    this.school_name = school_name;
  }

  public String getDbn() {
    return dbn;
  }

  public void setDbn(String dbn) {
    this.dbn = dbn;
  }

  public String getSchool_name() {
    return school_name;
  }

  public void setSchool_name(String school_name) {
    this.school_name = school_name;
  }
}
