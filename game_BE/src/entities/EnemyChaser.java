package entities;

public class EnemyChaser extends Entities {
    private double targetX, targetY;

    public EnemyChaser(String name, int hp, int atk, int def, int speed, int startX, int startY) {
        super(name, hp, atk, def, speed, startX, startY);
        this.targetX = x;
        this.targetY = y;
    }

    // Set the coordinates the enemy should chase (usually the player position)
    public void setTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void update(double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0.01) { // Prevent jittering when very close
            double moveDistance = speed * deltaTime;
            double nx = dx / distance; // Normalized direction X
            double ny = dy / distance; // Normalized direction Y

            x += nx * moveDistance;
            y += ny * moveDistance;
        }
    }
}