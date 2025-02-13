import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {
        private class Card {
            String value;
            String type;

            Card(String value, String type){
                this.value = value;
                this.type = type;
            }

            public String toString(){
                return value + "-" + type;
            }
            public int getValue(){
                String headCardCharacters = "AJQK";

                if (headCardCharacters.contains(value)){
                    if (value.equals("A")){
                        return 11;
                    }

                    return 10;
                }
                return Integer.parseInt(value); //2-10
            }

            public boolean isAce(){
                return value.equals("A");
            }

            public String getImagePath(){
                String imageName = toString();

                return "./Cards/" + imageName + ".png";
            }
        }
        BlackJack(){
            startGame();

            frame.setVisible(true);
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            gamePanel.setLayout(new BorderLayout());
            gamePanel.setBackground(new Color(53, 101, 77));
            frame.add(gamePanel);

            hitButton.setFocusable(false);
            buttonPanel.add(hitButton);
            stayButton.setFocusable(false);
            buttonPanel.add(stayButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    ArrayList<Card> deck; // Hey there's going be an ArrayList of type Card called deck(declaration)
    Random random = new Random();

    //Dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //Player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //Window
    int boardWidth = 600;
    int boardHeight = 600;

    int cardWidth = 110; //ratio should be 1:1.14
    int cardHeight = 154;

    JFrame frame = new JFrame("BlackJack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                //draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./Cards/BACK.png")).getImage();
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //draw dealer's hand
                for (int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");


    public void startGame(){
        //Deck
        buildDeck();
        shuffleDeck();

        //Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;


        hiddenCard = deck.remove(deck.size()-1); //remove card at last index
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("Dealer: ");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        //Player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("Player: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

    }
    public void buildDeck(){
        deck = new ArrayList<Card>(); // Hey now allocate the memory for the arraylist(initialization)
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++){
            for (int j = 0; j < values.length; j++){
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("Build Deck: ");
        System.out.println(deck);
    }

    public void shuffleDeck(){
        for (int i = 0; i < deck.size(); i++){
            int j = random.nextInt(deck.size()); // random.nextInt is a method from the random class that generates a random number from 0(inclusive) to a bound which is the parameter(exclusive) in this case it is from 0-51 which we are going to track as indices
            Card currCard = deck.get(i); // current card index
            Card randomCard = deck.get(j); // random index
            deck.set(i, randomCard); // replace card at index i with card at index j
            deck.set(j, currCard); // replace the card at index j with card at index i
            /* When we get our random index and retrieve its values we swap its values to the different index
            so now the first index value is switched with the second random index value.
            Without swapping the deck would have 2 indices with the same value.
             */
        }
        System.out.println("After Shuffle: ");
        System.out.println(deck);
    }
}
