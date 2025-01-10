import java.io.IOException;

/** Factory creates new Entity class instances for enemy's
 */
public class EnemyFactory {
    private GameController gameController;

    /** Construcotr takes in game controller to pass to enemy objects.
     * @param gameController - GameController instance
     */
    public EnemyFactory(GameController gameController) {
        this.gameController = gameController;
    }

    /** Method instantiates the correct enemy instance based on the parameter type.
     * @param type - type of enemy to spawn
     * @return - Entity enemy of correct type
     * @throws IOException - thrown if file error occurs
     */
    public Entity spawnEnemy(String type) throws IOException {
        if(type.equals("Snake")){
            return new Snake(gameController);
        }
        else if(type.equals("Scorpion")){
            return new Scorpion(gameController);
        }
        return null;
    }
}
