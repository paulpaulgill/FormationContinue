public class Architectes extends Profession{
    private final String cycle = "2020-2022";
    private final String cycle2 = "2018-2020";
    private final String cycle3 = "2016-2018";
    private final int heureMin = 42;

    @Override
    public String getCycle(){ return cycle; }

    @Override
    public String getCycle2(){ return cycle2; }

    @Override
    public String getCycle3(){ return cycle3; }

    @Override
    public int getHeureMin(){ return heureMin; }
}
