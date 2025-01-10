import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * The level generator class produces 1600 int long int array which serve as the randomized map
 * for the excavation level. It creates these maps as a random selection of 16 rooms from the room
 * data stored below.
 */
public class LevelGenerator {
    private GameController gameController;

    private Random rand = new Random();

    // The arrays for each room that can appear in a level
    private int[] Room1 = new int[]{
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,1,0,1,
            1,0,0,1,0,0,1,0,0,1,
            1,0,0,0,1,1,0,0,0,1,
            1,0,0,0,1,1,0,0,0,1,
            1,0,0,1,0,0,1,0,0,1,
            1,0,1,0,0,0,0,1,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1
    };

    private int[] Room2 = new int[]{
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,1,1,1,1,1,0,0,0,1,
            0,0,0,0,0,0,0,1,0,1,
            0,0,0,0,0,1,0,1,0,0,
            0,0,0,1,0,1,0,0,0,0,
            0,1,0,1,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1
    };

    private int[] Room3 = new int[]{
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1

    };

    private int[] Room4 = new int[]{
            1,1,0,0,0,0,0,0,1,1,
            0,0,0,0,1,1,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,1,1,0,0,1,1,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,1,1,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,1,1,0,0,1,1,0,0,
            0,0,0,0,0,0,0,0,0,0,
            1,1,0,0,0,0,0,0,1,1
    };

    private int[] Room5 = new int[]{
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,0,0,1,1,1,0,0,1,
            1,0,0,1,1,1,1,1,0,0,
            0,0,0,1,1,1,1,1,0,0,
            0,0,1,1,1,1,1,1,1,0,
            0,0,1,1,1,1,1,1,1,0,
            1,1,1,1,1,1,1,1,1,1
    };

    private int[] Room6 = new int[]{
            1,1,1,1,1,1,1,1,1,1,
            1,1,0,1,0,1,0,1,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,1,1,
            1,0,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1
    };
    private int[] Room7 = new int[]{
            1,0,0,0,0,0,0,0,0,0,
            1,1,1,1,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,1,1,1,1,
            0,0,0,0,0,0,0,0,0,0,
            1,1,1,0,0,1,0,0,0,0,
            0,0,0,0,0,0,0,1,0,0,
            0,0,0,0,0,0,0,1,0,0,
            0,1,0,0,1,0,0,1,0,0,
            1,1,1,1,1,1,1,1,1,1
    };

    private int[] Room8 = new int[]{
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,1,0,1,
            1,0,1,0,0,0,0,1,0,1,
            1,0,1,0,1,1,0,1,0,1,
            1,0,1,0,1,1,0,1,0,1,
            1,0,1,0,0,0,0,1,0,1,
            1,0,1,1,1,1,1,1,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1
    };

    private int[] Room9 = new int[]{
            1,1,0,1,1,1,1,0,1,1,
            1,0,0,0,1,1,0,0,0,1,
            0,0,0,0,0,0,0,0,0,0,
            0,0,1,0,0,0,0,1,0,0,
            1,1,0,0,1,1,0,0,1,1,
            1,1,1,0,1,1,0,1,1,1,
            0,0,0,0,0,0,0,0,0,0,
            1,0,0,1,0,0,1,0,0,1,
            1,1,0,0,1,1,0,0,1,1,
            1,1,1,0,1,1,0,1,1,1
    };

    private int[] Room10 = new int[]{
            0,1,0,0,1,0,1,1,0,0,
            1,1,1,1,0,1,1,1,1,1,
            1,1,0,0,1,1,1,1,0,1,
            0,1,1,1,0,0,0,1,0,1,
            0,1,0,1,1,0,0,1,0,1,
            1,0,0,0,0,1,1,1,0,1,
            1,1,1,1,1,1,1,1,1,1,
            0,1,0,0,0,1,1,1,1,0,
            0,1,1,0,1,1,1,1,1,1,
            1,0,1,1,1,0,1,1,0,1
    };

    //array of all rooms
    private ArrayList<int[]> tileLayouts = new ArrayList<int[]>();


    //add the room data to tilelayout on construction
    public LevelGenerator(GameController gc){
        this.gameController = gc;
        tileLayouts.add(Room1);
        tileLayouts.add(Room2);
        tileLayouts.add(Room3);
        tileLayouts.add(Room4);
        tileLayouts.add(Room5);
        tileLayouts.add(Room6);
        tileLayouts.add(Room7);
        tileLayouts.add(Room8);
        tileLayouts.add(Room9);
        tileLayouts.add(Room10);
        //makeRoomRow();
    }

    //create a row of 4 rooms, returns int array of 400 ints
    public int[] makeRoomRow(){


        int[] inds = new int[4];
        
        for(int i = 0;i < 4; i++){
            inds[i] = (rand.nextInt(tileLayouts.size()));
        }


        int[] Row = new int[400];
        for(int x = 0; x< 10;x++){

            for(int y = 0; y< 4;y++){
                int room = inds[y];
                for(int z = 0; z< 10;z++){
                    Row[(x*40)+(y*10)+z] = tileLayouts.get(room)[(x*10)+z];

                }

            }

        }



        return Row;

    }


    //create a map comprised of 4 rows with player spawn area and indestruble border, returns int array of 1600 ints
    public int[] makeMap(){
        int[] row1 = makeRoomRow();
        int[] row2 = makeRoomRow();
        int[] row3 = makeRoomRow();
        int[] row4 = makeRoomRow();
        //create the map
        row1 =  IntStream.concat(Arrays.stream(row1), Arrays.stream(row2)).toArray();
        row2 = IntStream.concat(Arrays.stream(row3), Arrays.stream(row4)).toArray();
        row1 = IntStream.concat(Arrays.stream(row1), Arrays.stream(row2)).toArray();
        //add indestruble border
        for(int z = 0; z< 40;z++){
            row1[z] = 2;
            row1[1560+z] = 2;
            row1[z*40] = 2;
            if(z >0){
                row1[(z*40)-1] = 2;
            }
        }
        //add player spawn area
        row1[(40*5)+5] = 0;
        row1[(40*5)+6] = 0;
        row1[(40*6)+5] = 0;
        row1[(40*6)+6] = 0;
        row1[(40*7)+5] = 2;
        row1[(40*7)+6] = 2;

        //add bone where a diggable tile was
        boolean boned = false;
        while(!boned){
            int ind = rand.nextInt(1559);
            if(row1[ind] == 1){
                row1[ind] = 4;
                boned = true;
            }
        }


        return row1;

    }



}
