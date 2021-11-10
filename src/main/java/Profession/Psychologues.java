package Profession;

import Profession.Profession;

public class Psychologues extends Profession {
    public final String cycle = "2018-2023";
    public final int heureMin = 90;

    @Override
    public String getCycle(){ return cycle; }

    @Override
    public String getCycle2() { return null; }

    @Override
    public String getCycle3() { return null; }

    @Override
    public int getHeureMin(){ return heureMin; }
}
