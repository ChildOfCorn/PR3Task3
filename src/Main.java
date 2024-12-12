import java.util.concurrent.*;
import java.util.Scanner;

class PR3Task3 {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ConcurrentHashMap<Integer, Boolean> results = new ConcurrentHashMap<>();

        System.out.print("Введіть числа через пробіл для перевірки на первинність: ");
        String input = scanner.nextLine();
        String[] inputNumbers = input.split(" ");

        for (String numStr : inputNumbers) {
            try {
                int number = Integer.parseInt(numStr);
                executor.submit(() -> results.put(number, isPrime(number)));
            } catch (NumberFormatException e) {
                System.out.println("Неправильне введення: " + numStr + " не є дійсним номером.");
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        results.forEach((key, value) ->
                System.out.println("Чи є номер " + key + " кращим? " + value));
    }

    private static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
