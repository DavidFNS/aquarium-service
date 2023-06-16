package constant;

import enumeration.FishGender;

import java.util.List;
import java.util.Map;

public class FishConstants {
    public static final Map<Integer, FishGender> FISH_GENDER_MAP = Map.of(
            0, FishGender.MASCULINE,
            1, FishGender.FEMININE
    );
    public static final Map<FishGender, String> FISH = Map.of(
            FishGender.MASCULINE, "Erkak",
            FishGender.FEMININE, "Ayol"
    );
}
