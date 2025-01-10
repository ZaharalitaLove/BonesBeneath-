import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * handles getting keyboard inputs and changing internal booleans for other classes namely
 * GamePlayer to read from and get keyboard inputs.
 */
public class KeyBoardHandler implements KeyListener {

   private boolean up, right, left, escape, larrow, darrow, rarrow, uarrow;

   private boolean one,two,three,four,five,six,seven,eight;
    public void keyTyped(KeyEvent e){
    //TODO track distcete inputs
    }
public void keyPressed(KeyEvent e){
//Method records if a key is being presseed in the apprirate booleans
    int code = e.getKeyCode();

    if(code == KeyEvent.VK_W) {
        up = true;
    }
    if(code == KeyEvent.VK_A) {
        left = true;
    }
    if(code == KeyEvent.VK_D) {
        right = true;
    }
    if(code == KeyEvent.VK_SPACE) {
        up = true;
    }
    if(code == KeyEvent.VK_ESCAPE) {
       escape = true;
    }
    if(code == KeyEvent.VK_LEFT) {
        larrow = true;

    }
    if(code == KeyEvent.VK_RIGHT) {
        rarrow = true;

    }
    if(code == KeyEvent.VK_DOWN) {
        darrow = true;

    }
    if(code == KeyEvent.VK_UP) {
        uarrow = true;

    }
    if(code == KeyEvent.VK_1) {
        one = true;

    }
    if(code == KeyEvent.VK_2) {
        two = true;

    }
    if(code == KeyEvent.VK_3) {
        three = true;

    }
    if(code == KeyEvent.VK_4) {
        four = true;

    }
    if(code == KeyEvent.VK_5) {
        five = true;

    }
    if(code == KeyEvent.VK_6) {
        six = true;

    }
    if(code == KeyEvent.VK_7) {
        seven= true;

    }
    if(code == KeyEvent.VK_8) {
        eight = true;

    }


}

public void keyReleased(KeyEvent e){
////Method records if a key has been released in the apprirate booleans
    int code = e.getKeyCode();

    if(code == KeyEvent.VK_W) {
        up = false;
    }
    if(code == KeyEvent.VK_A) {
        left = false;
    }
    if(code == KeyEvent.VK_D) {
        right = false;
    }
    if(code == KeyEvent.VK_SPACE) {
        up = false;
    }
    if(code == KeyEvent.VK_LEFT) {
        larrow = false;

    }
    if(code == KeyEvent.VK_RIGHT) {
        rarrow = false;

    }
    if(code == KeyEvent.VK_DOWN) {
        darrow = false;

    }
    if(code == KeyEvent.VK_ESCAPE) {
        escape = false;
    }
    if(code == KeyEvent.VK_UP) {
        uarrow = false;

    }
    if(code == KeyEvent.VK_1) {
        one = false;

    }
    if(code == KeyEvent.VK_2) {
        two = false;

    }
    if(code == KeyEvent.VK_3) {
        three = false;

    }
    if(code == KeyEvent.VK_4) {
        four = false;

    }
    if(code == KeyEvent.VK_5) {
        five = false;

    }
    if(code == KeyEvent.VK_6) {
        six = false;

    }
    if(code == KeyEvent.VK_7) {
        seven= false;

    }
    if(code == KeyEvent.VK_8) {
        eight = false;

    }
}

//getters for each boolean

    public boolean isUp() {
        return up;
    }

    public void setEscape(boolean esc){escape = esc;}

    public boolean isEscape() {
        return escape;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLarrow(){return larrow;}

    public boolean isDarrow() {
        return darrow;
    }

    public boolean isRarrow() {
        return rarrow;
    }

    public boolean isUarrow() {
        return uarrow;
    }

    public boolean isOne() {
        return one;
    }

    public boolean isTwo() {
        return two;
    }

    public boolean isThree() {
        return three;
    }

    public boolean isFour() {
        return four;
    }

    public boolean isFive() {
        return five;
    }

    public boolean isSix() {
        return six;
    }

    public boolean isSeven() {
        return seven;
    }

    public boolean isEight() {
        return eight;
    }

    public void noInput(){
        up = false;
        right = false;
        left = false;
        escape = false;
         larrow = false;
         darrow = false;
         rarrow = false;
         uarrow = false;
         one = false;
         two = false;
         three = false;
         four = false;
    }
}