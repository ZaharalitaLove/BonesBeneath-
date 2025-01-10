import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * checks whether an entity collides with a tile and adjusts that entities velocity to keep entity from going through
 * tiles with collision
 */
public class CollisionChecker {

    private  GameController gc;

    private MapModel map;

    private UserProfileModel upm;

    TileManager tileManager;

    public CollisionChecker(GameController gc, MapModel map,TileManager tileManager ){
        this.gc = gc;
        this.map = map;
        this.tileManager = tileManager;
        this.upm = gc.getUserProfile();

    }
    //checks wheter an entity collides with a tile and adjusts that entities velocity to keep entity from going through
// * tiles with collision
    public void checkTileNoCollect(Entity entity) throws IOException {

        //corners of the entities hit box
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        //int entityBottomWorldY2 = entity.worldY + entity.solidArea.y + entity.solidArea.height-5;

        // the location of the tiles which the entity is intersecting
        int entityLeftCol;// = entityLeftWorldX / gc.getTileSize();
        int entityLeftColIn = (entityLeftWorldX+2)/gc.getTileSize();
        int entityRightCol;// = entityRightWorldX / gc.getTileSize();
        int entityRightColIn = (entityRightWorldX-2)/gc.getTileSize();
        int entityTopRow = entityTopWorldY/gc.getTileSize();
        int entityBottomRow = entityBottomWorldY/gc.getTileSize();
        //int entityBottomRow2 = (entityBottomWorldY-5)/gp.tileSize;

        int tileNum1, tileNum2, tileNum3;

        //checks collsion from the bottom
        if(entity.yVelocity < 0) {
            entityTopRow = (entityTopWorldY - entity.yVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftColIn + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightColIn + (entityTopRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) == true ||tileManager.tilenumCollides(tileNum2) == true )
            {

                entity.colliding = true;
                //set yvelocity to 0 thus stopping upward movement
                entity.yVelocity = 0;


                //check if collding tile is a coin, if so destroy it and add its value to database
            }
            entityTopRow = entityTopWorldY/gc.getTileSize();
            //checks collsion from the top
        }else if(entity.yVelocity > 0) {
            entityBottomRow = (entityBottomWorldY + entity.yVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftColIn + (entityBottomRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightColIn + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {

                //sets grounded to true allowing player to jump
                entity.grounded = true;
                //set yvelocity to 0 thus stopping downward movement
                entity.yVelocity = 0;


                //check if collding tile is a coin, if so destroy it and add its value to database
            }
            entityBottomRow = entityBottomWorldY/gc.getTileSize();
        }
        //checks collsion from the left
        if(entity.xVelocity < 0) {
            entityLeftCol = (entityLeftWorldX + entity.xVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftCol + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityLeftCol + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {
                entity.colliding = true;
                //set xvelocity to a small positive number thus stopping leftward movement
                entity.xVelocity = 1;
                if(entity.grounded){
                    entity.xVelocity = 2;
                }

                //check if collding tile is a coin, if so destroy it and add its value to database
            }
            entityLeftCol = entityLeftWorldX/gc.getTileSize();
            //checks collsion from the right
        }else if(entity.xVelocity > 0) {
            entityRightCol = (entityRightWorldX + entity.xVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityRightCol + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightCol + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {
                entity.colliding = true;
                //set xvelocity to a small negative number thus stopping rightward movement
                entity.xVelocity = -1;
                if(entity.grounded){
                    entity.xVelocity = -2;
                }

                //check if collding tile is a coin, if so destroy it and add its value to database
            }

        }}

//checks wheter an entity collides with a tile and adjusts that entities velocity to keep entity from going through
// * tiles with collision
    public void checkTile(Entity entity) throws IOException {

        //corners of the entities hit box
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        //int entityBottomWorldY2 = entity.worldY + entity.solidArea.y + entity.solidArea.height-5;

        // the location of the tiles which the entity is intersecting
        int entityLeftCol;// = entityLeftWorldX / gc.getTileSize();
        int entityLeftColIn = (entityLeftWorldX+2)/gc.getTileSize();
        int entityRightCol;// = entityRightWorldX / gc.getTileSize();
        int entityRightColIn = (entityRightWorldX-2)/gc.getTileSize();
        int entityTopRow = entityTopWorldY/gc.getTileSize();
        int entityBottomRow = entityBottomWorldY/gc.getTileSize();
        //int entityBottomRow2 = (entityBottomWorldY-5)/gp.tileSize;

        int tileNum1, tileNum2, tileNum3;

        //checks collsion from the bottom
        if(entity.yVelocity < 0) {
            entityTopRow = (entityTopWorldY - entity.yVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftColIn + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightColIn + (entityTopRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) == true ||tileManager.tilenumCollides(tileNum2) == true )
            {

                entity.colliding = true;
                //set yvelocity to 0 thus stopping upward movement
                entity.yVelocity = 0;


                //check if collding tile is a coin, if so destroy it and add its value to database
            }else if(tileManager.tilenumValue(tileNum1) > 0){
                map.removeTile(entityLeftColIn,entityTopRow);
                gc.getCoins(tileManager.tilenumValue(tileNum1));
                if(tileManager.tilenumValue(tileNum1) == 1){
                    gc.bone();
                }
            }else if(tileManager.tilenumValue(tileNum2) > 0){
                map.removeTile(entityRightColIn,entityTopRow);
                gc.getCoins(tileManager.tilenumValue(tileNum2));
                if(tileManager.tilenumValue(tileNum2) == 1){
                    gc.bone();
                }
            }
            entityTopRow = entityTopWorldY/gc.getTileSize();
        //checks collsion from the top
        }else if(entity.yVelocity > 0) {
            entityBottomRow = (entityBottomWorldY + entity.yVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftColIn + (entityBottomRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightColIn + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {

                //sets grounded to true allowing player to jump
                entity.grounded = true;
                //set yvelocity to 0 thus stopping downward movement
                entity.yVelocity = 0;


                //check if collding tile is a coin, if so destroy it and add its value to database
            }else if(tileManager.tilenumValue(tileNum1) > 0){
                map.removeTile(entityLeftColIn,entityBottomRow);
                gc.getCoins(tileManager.tilenumValue(tileNum1));
                if(tileManager.tilenumValue(tileNum1) == 1){
                    gc.bone();
                }
            }else if(tileManager.tilenumValue(tileNum2) > 0){
                map.removeTile(entityRightColIn,entityBottomRow);
                gc.getCoins(tileManager.tilenumValue(tileNum2));
                if(tileManager.tilenumValue(tileNum2) == 1){
                    gc.bone();
                }
            }
            entityBottomRow = entityBottomWorldY/gc.getTileSize();
        }
        //checks collsion from the left
        if(entity.xVelocity < 0) {
            entityLeftCol = (entityLeftWorldX + entity.xVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityLeftCol + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityLeftCol + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {
                entity.colliding = true;
                //set xvelocity to a small positive number thus stopping leftward movement
                entity.xVelocity = 1;
                if(entity.grounded){
                    entity.xVelocity = 2;
                }

            //check if collding tile is a coin, if so destroy it and add its value to database
            }else if(tileManager.tilenumValue(tileNum1) > 0){
                map.removeTile(entityLeftCol,entityTopRow);
                gc.getCoins(tileManager.tilenumValue(tileNum1));
                if(tileManager.tilenumValue(tileNum1) == 1){
                    gc.bone();
                }
            }else if(tileManager.tilenumValue(tileNum2) > 0){
                map.removeTile(entityLeftCol,entityBottomRow);
                gc.getCoins(tileManager.tilenumValue(tileNum2));
                if(tileManager.tilenumValue(tileNum2) == 1){
                    gc.bone();
                }

            }
            entityLeftCol = entityLeftWorldX/gc.getTileSize();
            //checks collsion from the right
        }else if(entity.xVelocity > 0) {
            entityRightCol = (entityRightWorldX + entity.xVelocity)/gc.getTileSize();
            tileNum1 = map.accessMapData()[entityRightCol + (entityTopRow * gc.getMaxWorldCol())];
            tileNum2 = map.accessMapData()[entityRightCol + (entityBottomRow * gc.getMaxWorldCol())];
            if(tileManager.tilenumCollides(tileNum1) || tileManager.tilenumCollides(tileNum2))
            {
                entity.colliding = true;
                //set xvelocity to a small negative number thus stopping rightward movement
                entity.xVelocity = -1;
                if(entity.grounded){
                    entity.xVelocity = -2;
                }

                //check if collding tile is a coin, if so destroy it and add its value to database
            }else if(tileManager.tilenumValue(tileNum1) > 0){
                map.removeTile(entityRightCol,entityTopRow);
                gc.getCoins(tileManager.tilenumValue(tileNum1));
                if(tileManager.tilenumValue(tileNum1) == 1){
                    gc.bone();
                }
            }else if(tileManager.tilenumValue(tileNum2) > 0){
                map.removeTile(entityRightCol,entityBottomRow);
                gc.getCoins(tileManager.tilenumValue(tileNum2));
                if(tileManager.tilenumValue(tileNum2) == 1){
                    gc.bone();
                }
            }

        }}



    /** Method takes in two entities and checks if there is a collision between their hit boxes.
     * Depending on if the collision is from either side or from the top, the method will behave differently.
     * If an entity is hit from the side then it is being attacked , if that entity is hitting entity2
     * from the top then it is attacking.
     * @param entity - the first entity instance
     * @param entity2 - the second entity instance
     */
    public void checkEntityCollision(Entity entity,Entity entity2) throws IOException, SQLException {

        //corners of the entity's hit box
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        //corners of the entity2's hit box
        int entity2LeftWorldX = entity2.worldX + entity2.solidArea.x;
        int entity2RightWorldX = entity2.worldX + entity2.solidArea.x + entity2.solidArea.width;
        int entity2TopWorldY = entity2.worldY + entity2.solidArea.y;
        int entity2BottomWorldY = entity2.worldY + entity2.solidArea.y + entity2.solidArea.height;

            //checks if the bottom of entity's hit box collides with the top of enemy2's hit box
            if (entityBottomWorldY >= entity2TopWorldY && entityBottomWorldY < entity2BottomWorldY && entityLeftWorldX <= entity2RightWorldX && entityRightWorldX >= entity2LeftWorldX ) {
                entity2.setEnemyAlive(false);
                entity.yVelocity -= 20;
            }

            //checks if the right of entity's hit box collides with the left of enemy2's hit box
            else if (entity2LeftWorldX > entityLeftWorldX && entity2LeftWorldX < entityRightWorldX && entityTopWorldY < entity2BottomWorldY && entityBottomWorldY == entity2BottomWorldY){
                entity2.attack();
                entity.setRespondingToAttack(true);
                entity.xVelocity = -10;
                entity2.xVelocity = 10;
            }
            //checks if the left of entity's hit box collides with the right of enemy2's hit box
            else if (entity2RightWorldX < entityRightWorldX && entity2RightWorldX > entityLeftWorldX && entityTopWorldY < entity2BottomWorldY && entityBottomWorldY == entity2BottomWorldY){
                entity2.attack();
                entity.setRespondingToAttack(true);
                entity.xVelocity = 10;
                entity2.xVelocity = -10;
            }
    }
}
