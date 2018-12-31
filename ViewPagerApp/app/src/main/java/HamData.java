import com.evan.viewpagerapp.R;
import com.evan.viewpagerapp.VeggieData;

public class HamData {

    private String name;
    private int ImageResId;

    private HamData(String name, int imageResId) {
        this.name = name;
        this.ImageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return ImageResId;
    }

    public static final HamData[] veggies = {
            new HamData("Broccoli", R.drawable.broccoli),
            new HamData("Brussel Sprouts", R.drawable.brussels_sprouts)
    };
}
