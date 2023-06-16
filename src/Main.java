import service.FishService;

import java.util.Random;

import static constant.AnsiColorConstants.ANSI_CYAN;
import static constant.AnsiColorConstants.ANSI_RESET;

public class Main {
    public static void main(String[] args) {
        FishService fishService = new FishService();
        Random random = new Random();
        int countDefaultFishInTheAquarium = random.nextInt(10, 21);
        var fishMap = fishService.separateByGenderAndPutAll(countDefaultFishInTheAquarium);

        Runnable run = fishService::meetFishWithEachOther;
        new Thread(run).start();

        var tuple2 = fishService.getCountFishSeparately();
        System.out.println(ANSI_CYAN + "Akvariumdagi baliqlar soni: " + fishMap.size());
        System.out.println("Erkak baliqlar: " + tuple2.getFirst());
        System.out.println("Urg'ochi baliqlar: " + tuple2.getSecond());
        System.out.println(ANSI_RESET + "----------------------------------------------------");
        fishService.putFishInTheAquarium(fishMap);
    }
}