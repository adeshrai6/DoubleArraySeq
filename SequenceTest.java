//Name: Adesh Rai
//Course: CSC103
//Date:  February 24, 2021

import java.util.*;

class SequenceTestJava {
   public static void main(String[] args) {
      Scanner key = new Scanner(System.in);

      boolean flag = true;

      DoubleArraySeq sequenceA = new DoubleArraySeq();
      DoubleArraySeq sequenceB = new DoubleArraySeq();

      while (flag) {
         try {
            info();
            System.out.print("Please choose a number from 1-12: ");
            int num = intInputs(key);
            switch (num) {
               case 1:
                  System.out.println("\nSequence A: " + sequenceA);
                  System.out.println("\nSequence B: " + sequenceB);
                  break;

               case 2:
                  System.out.println("Capacity of Sequence A: " + sequenceA.getCapacity());
                  System.out.println("Capacity of Sequence B: " + sequenceB.getCapacity());
                  break;

               case 3: // Set the current element location
                  System.out.print("Set a current element location: ");
                  int currentLocation = intInputs(key);
                  sequenceA.setCurrent(currentLocation);
                  sequenceB.setCurrent(currentLocation);
                  System.out.println(sequenceA.getCurrent() + " is new current.");
                  System.out.println(sequenceB.getCurrent() + " is new current.");
                  break;

               case 4:
                  System.out.println("Please enter a number that you want to add in sequence");
                  double inputedNum = doubleInputs(key);
                  sequenceA.addFront(inputedNum);
                  sequenceB.addFront(inputedNum);
                  System.out.print("Number has been added to the front \n");
                  break;

               case 5:
                  System.out.println("Please enter a number that you want add to the end");
                  double endNum = doubleInputs(key);
                  sequenceA.addEnd(endNum);
                  sequenceB.addEnd(endNum);
                  System.out.println(endNum + " is added at the end of sequence.");
                  break;
               case 6:
                  System.out.println("Please enter a number that you want to add before current:");
                  double beforeNum = doubleInputs(key);
                  sequenceA.addBefore(beforeNum);
                  sequenceB.addBefore(beforeNum);
                  System.out.println(beforeNum + " is added before current ");
                  break;

               case 7:
                  System.out.println("Please enter a number that you want to add after current:");
                  double afterNum = doubleInputs(key);
                  sequenceA.addAfter(afterNum);
                  sequenceB.addAfter(afterNum);
                  System.out.println(afterNum + " is added after current: " + sequenceA.getCurrent());
                  break;

               case 8:
                  sequenceA.removeFront();
                  System.out.println(sequenceA.getElement(0) + " is removed from sequence");
                  sequenceB.removeFront();
                  System.out.println(sequenceB.getElement(0) + " is removed from sequence");
                  break;

               case 9:
                  System.out.println("Enter a number to remove at index: ");
                  int removeNum = intInputs(key);
                  sequenceA.setCurrent(removeNum);
                  sequenceB.setCurrent(removeNum);
                  System.out.println(
                        "In index " + removeNum + " the data " + sequenceA.getCurrent() + " is removed from sequence.");
                  sequenceA.removeCurrent();
                  sequenceB.removeCurrent();
                  break;

               case 10:
                  System.out.println("Please enter a location index, where you want to see data");
                  int locNum = intInputs(key);
                  System.out.println("In the index " + locNum + ", data is " + sequenceA.getElement(locNum));
                  System.out.println("In the index " + locNum + ", data is " + sequenceB.getElement(locNum));
                  break;

               case 11:
                  sequenceA.setCurrentLast();
                  System.out.println("Last element in the Sequence A is: " + sequenceA.getCurrent());
                  sequenceB.setCurrentLast();
                  System.out.println("Last element in the Sequence B is: " + sequenceB.getCurrent());

                  break;

               case 12:
                  System.out.println("Quiting..");
                  flag = false;
                  break;
               default:
                  System.out.println("Please enter 1 to 17 only");

            }
         } catch (InputMismatchException e) {
            System.out.println("You have to put a number");
         } catch (Exception e) {
            System.out.println("Something went wrong. ");

         }

      }

   } // Main

   public static void info() {
      System.out.println();
      System.out.println("1.   Print the sequences.");
      System.out.println("2.   Report the capacity");
      System.out.println("3.   Set the current element location");
      System.out.println("4.   Add a number to the front of a sequence.");
      System.out.println("5.   Add a number to the end of the sequence.");
      System.out.println("6.   Add a number before current.");
      System.out.println("7.   Add a number after current.");
      System.out.println("8.   Delete the first number from the sequence.");
      System.out.println("9.   Delete a number at a location");
      System.out.println("10.  Display a number at a location.");
      System.out.println("11.  Display the last element in the sequence.");
      System.out.println("12.  Quit");
      System.out.println();

   }

   public static double doubleInputs(Scanner key) {
      double input = 0;
      boolean isTrue = true;

      while (isTrue) {
         try {
            input = key.nextDouble();
            isTrue = false;
         } catch (InputMismatchException e) {
            System.out.println("Invalid entry, Enter a number: ");
            key.next();
         } catch (Exception e) {
            System.out.println("Please enter valid number, not letter or characters. ");

         }
      }
      return input;
   }

   public static int intInputs(Scanner key) {
      int input = 0;
      boolean isTrue = true;

      while (isTrue) {
         try {
            input = key.nextInt();
            isTrue = false;
         } catch (InputMismatchException e) {
            System.out.println("Invalid entry, Enter a number: ");
            key.next();
         } catch (Exception e) {
            System.out.println("Please enter valid number, not letter or characters.");
         }
      }
      return input;
   }

} // Class