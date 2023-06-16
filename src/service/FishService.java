package service;

import enumeration.FishGender;
import model.Fish;
import model.Tuple2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static constant.AnsiColorConstants.*;
import static constant.FishConstants.FISH;
import static constant.FishConstants.FISH_GENDER_MAP;

public class FishService {
    private static final Random random = new Random();
    public static Map<Long, Fish> fishMap = new ConcurrentHashMap<>();
    public static AtomicLong atomicLong = new AtomicLong(1L);

    public Map<Long, Fish> separateByGenderAndPutAll(int countFish) {
        for (int i = 0; i < countFish; i++) {
            int indexGender = random.nextInt(2);
            int lifetime = random.nextInt(20, 30);
            int fishLocation = random.nextInt(0, 6);

            Fish fish = new Fish()
                    .fishID(atomicLong.getAndIncrement())
                    .fishGender(FISH_GENDER_MAP.get(indexGender))
                    .fishLifetime(lifetime)
                    .fishLocation(fishLocation)
                    .currentLifetime(0);
            fishMap.put(fish.getFishID(), fish);
        }
        return fishMap;
    }

    public void meetFishWithEachOther() {
        TimerTask fishMeetingTask = new TimerTask() {
            @Override
            public void run() {
                List<Fish> fishList = new ArrayList<>(fishMap.values());
                for (Fish firstFish: fishList) {

                    // check another fish by filter criteria for inheritance
                    var optionalFish = findFishByFilter(firstFish);
                    if (optionalFish.isPresent()) {
                        Fish anotherFish = optionalFish.get();
                        System.out.printf(
                                ANSI_PURPLE + "%s va %s raqamli baliqlar nasl qoldirish uchun uchrashdi\n",
                                firstFish.getFishID(), anotherFish.getFishID()
                        );
                        System.out.println(ANSI_RESET + "----------------------------------------------------");
                        int countNewChildFish = random.nextInt(1, 10);

                        System.out.println("Tug'ilishdan oldin: " + fishMap.size());
                         var newChildFish = separateByGenderAndPutAll(countNewChildFish);
                         System.out.printf(
                                 ANSI_GREEN + "%s va %s raqamli baliqlardan %d ta baliq tug'ildi!\n",
                                 firstFish.getFishID(), anotherFish.getFishID(), countNewChildFish
                         );
                        System.out.println("Tug'ilgandan keyin: " + newChildFish.size());
                        System.out.println(ANSI_RESET + "----------------------------------------------------");
                        putFishInTheAquarium(newChildFish);

                        // stop moving a fish for 5 seconds after meeting another fish
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };

        Timer timer = new Timer();
        // run every 5 second
        timer.schedule(fishMeetingTask, 0, 5000);
    }

    private Optional<Fish> findFishByFilter(Fish fish) {
        List<Fish> fishList = new ArrayList<>(fishMap.values());

        return fishList.stream()
                // the life of a fish must be greater than 3 to meet another fish
                .filter(anotherFish -> anotherFish.getCurrentLifetime() > 3)
                .filter(anotherFish -> !anotherFish.getFishID().equals(fish.getFishID()))
                .filter(anotherFish -> !anotherFish.getFishGender().equals(fish.getFishGender()))
                .filter(anotherFish -> anotherFish.getFishLocation().equals(fish.getFishLocation()))
                .findFirst();
    }

    public void putFishInTheAquarium(Map<Long, Fish> fishMap) {
        fishMap
                .forEach((fishID, fish) -> {
                    System.out.printf(ANSI_GREEN + "%s raqamli baliq %d nuqtadan harakatni boshladi\n", fishID, fish.getFishLocation());
                    System.out.println(ANSI_RESET + "----------------------------------------------------");
                    Runnable runnable = () -> fishLifeCycle(fishID, fish);
                    new Thread(runnable).start();
                    // so that all the fish are not in one place
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void fishLifeCycle(Long fishID, Fish fish) {
        TimerTask fishTask = new TimerTask() {
            @Override
            public void run() {
                int newFishLocation = random.nextInt(0, 6);
                System.out.printf(ANSI_BLUE +
                        "%s raqamli baliq %d nuqtadan %d nuqtaga harakatlandi\n",
                        fish.getFishID(), fish.getFishLocation(), newFishLocation
                );

                fish.setCurrentLifetime(fish.getCurrentLifetime()+1);
                fish.setFishLocation(newFishLocation);
                fishMap.put(fishID, fish);
                System.out.println(ANSI_RESET + "----------------------------------------------------");
            }
        };

        Timer timer = new Timer();
        // wait 1 second and run every 5 second
        timer.schedule(fishTask, 0, 5000);

        // wait until the fish is alive
        try {
            Thread.sleep(1000L * fish.getLifetime());
        } catch (InterruptedException e) {
            timer.cancel();
            timer.purge();
        }

        // stop fish life and runnable task
        timer.cancel();
        timer.purge();

        System.out.println(ANSI_RESET + "O'chishdan oldin: " + fishMap.size());
        fishMap.remove(fish.getFishID());

        System.out.printf(ANSI_RED + "%s raqamli %s baliq vafot etdi!\n", fishID, FISH.get(fish.getFishGender()));
        if (fishMap.size() == 0) {
            System.out.println("Akvariumda baliqlar qolmadi!");
            System.exit(0);
        }
        System.out.println(ANSI_RESET + "Qolganlar baliqlar umumiy soni: " + fishMap.size());
        System.out.println("----------------------------------------------------");

        Thread.currentThread().interrupt();
    }

    public Tuple2<Long, Long> getCountFishSeparately() {
        AtomicLong countMasculines = new AtomicLong();
        AtomicLong countFeminines = new AtomicLong();
        fishMap.
                forEach((fishId, fish) -> {
                    if (fish.getFishGender().equals(FishGender.MASCULINE)) countMasculines.incrementAndGet();
                    else countFeminines.incrementAndGet();
                });
        return new Tuple2<>(countMasculines.get(), countFeminines.get());
    }
}
