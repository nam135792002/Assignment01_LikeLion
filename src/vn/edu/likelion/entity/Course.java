package vn.edu.likelion.entity;

public class Course extends InfoGeneral{
    private int amount;
    private boolean status; // status is open or close
    private int registered;

    public Course(){}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }
}
