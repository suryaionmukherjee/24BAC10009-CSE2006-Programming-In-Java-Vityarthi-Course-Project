public class MatchCountdownThread extends Thread {
    private int seconds;

    public MatchCountdownThread(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        System.out.println("\n[Match Thread] Preparing match start...");
        for (int i = seconds; i > 0; i--) {
            System.out.println("Match starts in: " + i);
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.out.println("Countdown interrupted: " + e.getMessage());
            }
        }
        System.out.println("[Match Thread] Match has started! Ready for performance entry.\n");
    }
}