

1. Ask to play(1) or forfeit(0) the game

  if (play)  if(forfeit)-exit the program
   2. Start Game (Goes for 7 round each player with 7 throw altervatively)
   3.  Display the scores
      Round 1: (In each round each player will have 3 chances) No. of throws-3
       Player 1 throw 5 dice (This throw could go to 3 chances)
         Ask player1 to chose category or defer
           if(defer chosen){
               No. of thrown changes to 2 
               option to throw the dice again
           }
           if(chosen category){
               chose the category from the 7 options and set aside the number or if sequence chosen set aside sequence
               set as side the category and decrease the No of dice for the next chance(eg if 5 chosen and there were 2 fives the next No of dice will be 3)
               no of thrown changes to 2
           }
           go until 3 chances
           Add all the number accumaulated from the dice thrown and give it a scores
        
        Update the score board with the score of the first player

      Player 2 : Ask to throw dice 
          repeat the same as the player 1 process and update the score board with player 2's final score and go to round 2  
        

for 7 rounds{

 
 player 1:
 playRound();
 displayScore();

 player2:
 playRound();
 displayScore();

 7 rounds ends
}
compare the total score and find winner


     
     when the user select to throw (t) or forfeit (f){

         numOfTurns = 3;
         numOfDice = 5;
         int score=0;
         totalOccurence = 0;
                                    occurence method to get the number of times the category occured
                                    score here holds the toltal score through out the loop
         1. throw dice;
         2. ask to select category  
         int category = lets say 2 ask to = 2
          score = occurence(category,dice) * category;    
          totalOccurence = occurence;

         for(loops for 2 times){
              ask to throw dice;
              dice is shown but the number of dice is numOfDice - totalOccurence
              int category = lets say 2 ask to = 2
              score = score + occurence(category,dice) * category;    
              totalOccurence = occurence;
              
         }
         updateScore(score);    
     }

     