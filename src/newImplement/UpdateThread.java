package newImplement;

public class UpdateThread extends Thread{
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            PlayOptions.getPlayOptions().update();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopRunning() {
        running = false;
        this.interrupt();
    }
}