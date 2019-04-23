package weatherapp.cccevan.com.evan.fitness12;

public class User {
    private int dailyDistance;


    private String email;
    private String password;
    private int totalDistance;
    private boolean overThousand;

    public boolean isOverThousand() {
        return overThousand;
    }

    public void setOverThousand(boolean overThousand) {
        this.overThousand = overThousand;
    }

    public int getPreviousTotal() {
        return previousTotal;
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }

    private int previousTotal;

    public User() {
    }

    public User(int dailyDistance, String email, String password, int totalDistance,boolean overThousand,int previousTotal) {
        this.dailyDistance = dailyDistance;
        this.email = email;
        this.password = password;
        this.totalDistance = totalDistance;
        this.overThousand = overThousand;
        this.previousTotal = previousTotal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getDailyDistance() {
        return dailyDistance;
    }

    public void setDailyDistance(int dailyDistance) {
        this.dailyDistance = dailyDistance;
    }
}
