package util;

import java.util.Date;

public class IntervalCycle {
    private String cycle;
    private Date max;
    private Date min;

    public IntervalCycle(){}

    public IntervalCycle(String cycle,Date max, Date min) {
        this.cycle = cycle;
        this.max = max;
        this.min = min;
    }

    public Date getMax() {
        return max;
    }

    public void setMax(Date max) {
        this.max = max;
    }

    public Date getMin() {
        return min;
    }

    public void setMin(Date min) {
        this.min = min;
    }
}
