import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String getName(String userReply){
        String results;
        results = userReply.substring(userReply.lastIndexOf(" ")+1);
        return  results;
    }

    public static String getDice(String userReply){
        String results;
        char[] chars = userReply.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                sb.append(c);
            }
        }
        results = String.valueOf(sb);
        return results;
    }

    public static void main(String[] args) {
        Player playerOne = null;
        Player playerTwo = null;
        int firstDice;
        int secondDice;
        String userReply;
        String dice;
        Scanner sc = new Scanner(System.in);

        int loopController = 0;
        do {
            userReply = sc.nextLine();
            if(playerOne == null){
                playerOne = new Player(getName(userReply));
            }else if( userReply.contains(playerOne.getName())){
                System.out.println( playerOne.getName() + ": already existing player");
            }else{

                playerTwo = new Player(getName(userReply));
                System.out.println("players: "+ playerOne.getName() + ", " + playerTwo.getName());
                loopController = 1;

            }

        }while (loopController == 0);


        while(true){
            userReply = sc.nextLine();
            dice = getDice(userReply);
            if(dice.length() > 0){
                firstDice = Integer.parseInt(String.valueOf(dice.charAt(0)));
                secondDice = Integer.parseInt(String.valueOf(dice.charAt(1)));
            }else {
                firstDice = (int)(Math.random()*(6-1+1)+1); ;
                secondDice = (int)(Math.random()*(6-1+1)+1); ;
            }

            if(userReply.contains(playerOne.getName())){
                if( (playerOne.getLastPosition() + firstDice + secondDice) == playerTwo.getLastPosition()){
                    playerTwo.setLastPosition(playerOne.prank(firstDice,secondDice, playerTwo.getLastPosition(), playerTwo.getName()));
                }else {
                    playerOne.play(firstDice, secondDice);
                }
            }else{
                if( (playerTwo.getLastPosition() + firstDice + secondDice) == playerOne.getLastPosition()){
                    playerOne.setLastPosition(playerTwo.prank(firstDice,secondDice, playerOne.getLastPosition(), playerOne.getName()));
                }else {
                    playerTwo.play(firstDice, secondDice);
                }
            }

            if(playerOne.getLastPosition() == 63 || playerTwo.getLastPosition() == 63){
                break;
            }

        }
    }
}

class Player{

    private String name;
    private int lastPosition;



    Player(String name){
        this.name = name;
        this.lastPosition = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public void play(int firstDice, int secondDice) {
        String position;
        if (lastPosition == 0) {
            position = "start";
        } else {
            position = String.valueOf(lastPosition);
        }
        this.lastPosition = this.lastPosition + firstDice + secondDice;
        String display = this.name + " rolls " + firstDice + ", " + secondDice + ". "
                + this.name + " moves from " + position + " to " + this.lastPosition;
        if (this.lastPosition > 63) {
            this.lastPosition = 63 - (this.lastPosition - 63);
            display = this.name + " rolls " + firstDice + ", " + secondDice + ". "
                    + this.name + " moves from " + position + " to " + 63 + ". " + this.name + " Bounces! " + this.name + " returns to " + this.lastPosition;
        } else if (this.lastPosition == 63) {
            display = display + ". " + this.name + " wins!!";
        } else if( this.lastPosition == 6){
            display = this.name + " rolls " + firstDice + ", " + secondDice + ". "
                    + this.name + " moves from " + position + " to The Bridge. " + this.name + " jumps to 12";
            this.lastPosition = 12;
        }else{
            if(this.lastPosition == 5 || this.lastPosition ==  9 || this.lastPosition ==  14 ||this.lastPosition ==  18 || this.lastPosition ==  23 ||this.lastPosition ==  27) {
                this.lastPosition = this.lastPosition + firstDice + secondDice;
                display = display + ", The Goose. " + this.name + " moves again and goes to " + this.lastPosition ;

                if(this.lastPosition == 5 || this.lastPosition ==  9 || this.lastPosition ==  14 ||this.lastPosition ==  18 || this.lastPosition ==  23 ||this.lastPosition ==  27) {
                    this.lastPosition = this.lastPosition + firstDice + secondDice;
                    display = display + ", The Goose. " + this.name + " moves again and goes to " + this.lastPosition ;
                }
            }
        }
        System.out.println(display);
    }

    public  int prank(int firstDice, int secondDice, int playerPosition, String playerName){
        int position = this.lastPosition;
        this.lastPosition = this.lastPosition + firstDice + secondDice;
        String display = this.name + " rolls " + firstDice + ", " + secondDice + ". "
                + this.name + " moves from " + position + " to " + this.lastPosition +". On " + playerPosition + " there is "+ playerName +", who returns to " + position;
        System.out.println(display);
        return position;
    }


}