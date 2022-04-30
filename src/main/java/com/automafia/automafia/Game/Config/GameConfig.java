package com.automafia.automafia.Game.Config;

import javax.persistence.*;

@Entity
public class GameConfig {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    private int usersCount;
    private boolean isManiacExist;
    private boolean isDoctorExist;

    //    TODO: THIS IS NOT WORK
    private boolean isMafiaUnanimousDecision;

    public GameConfig() {}

    public GameConfig init(boolean isDoctorExist, boolean isManiacExist, boolean isMafiaUnanimousDecision,
                           int usersCount) {
        this.isDoctorExist = isDoctorExist;
        this.isManiacExist = isManiacExist;
        this.isMafiaUnanimousDecision = isMafiaUnanimousDecision;
        this.usersCount = usersCount;
        return this;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public void setManiacExist(boolean maniacExist) {
        isManiacExist = maniacExist;
    }

    public void setDoctorExist(boolean doctorExist) {
        isDoctorExist = doctorExist;
    }

    public void setMafiaUnanimousDecision(boolean mafiaUnanimousDecision) {
        isMafiaUnanimousDecision = mafiaUnanimousDecision;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public boolean isManiacExist() {
        return isManiacExist;
    }

    public boolean isDoctorExist() {
        return isDoctorExist;
    }

    public boolean isMafiaUnanimousDecision() {
        return isMafiaUnanimousDecision;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
