//Name: Adesh Rai
//Course: CSC103
//Date:  February 24, 2021
// File: DoubleArraySeq.java

// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

/******************************************************************************
 * @author: Michael Main This class is a homework assignment; A DoubleArraySeq
 *          is a collection of double numbers. The sequence can have a special
 *          "current element," which is specified and accessed through four
 *          methods that are not available in the bag class (start, getCurrent,
 *          advance and isCurrent).
 *
 * @note (1) The capacity of one a sequence can change after it's created, but
 *       the maximum capacity is limited by the amount of free memory on the
 *       machine. The constructor, addAfter, addBefore, clone, and concatenation
 *       will result in an OutOfMemoryError when free memory is exhausted.
 *       <p>
 *       (2) A sequence's capacity cannot exceed the maximum integer
 *       2,147,483,647 (Integer.MAX_VALUE). Any attempt to create a larger
 *       capacity results in a failure due to an arithmetic overflow.
 *
 * @note This file contains only blank implementations ("stubs") because this is
 *       a Programming Project for my students.
 *
 * @see <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java"> Java
 *      Source Code for this class
 *      (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
 *      </A>
 *
 * @version March 5, 2002
 ******************************************************************************/
public class DoubleArraySeq implements Cloneable {

   // Invariant of the DoubleArraySeq class:
   // 1. The number of elements in the sequences is in the instance variable
   // manyItems.
   // 2. For an empty sequence (with no elements), we do not care what is
   // stored in any of data; for a non-empty sequence, the elements of the
   // sequence are stored in data[0] through data[manyItems-1], and we
   // dont care whats in the rest of data.
   // 3. If there is a current element, then it lies in data[currentIndex];
   // if there is no current element, then currentIndex equals manyItems.
   private double[] data;
   private int manyItems;
   private int currentIndex;

   /**
    * Initialize an empty sequence with an initial capacity of 10. Note that the
    * addAfter and addBefore methods work efficiently (without needing more memory)
    * until this capacity is reached.
    * 
    * @parammeter - none
    * @postcondition This sequence is empty and has an initial capacity of 10.
    * @exception OutOfMemoryError Indicates insufficient memory for: new
    *                             double[10].
    **/
   public DoubleArraySeq() {
      final int NUM = 10;
      manyItems = 0;
      currentIndex = 0;
      data = new double[NUM];
   }

   /**
    * Initialize an empty sequence with a specified initial capacity. Note that the
    * addAfter and addBefore methods work efficiently (without needing more memory)
    * until this capacity is reached.
    * 
    * @param initialCapacity the initial capacity of this sequence
    * @precondition initialCapacity is non-negative.
    * @postcondition This sequence is empty and has the given initial capacity.
    * @exception IllegalArgumentException Indicates that initialCapacity is
    *                                     negative.
    * @exception OutOfMemoryError         Indicates insufficient memory for: new
    *                                     double[initialCapacity].
    **/
   public DoubleArraySeq(int initialCapacity) {
      if (initialCapacity > 0) {
         manyItems = 0;
         currentIndex = 0;
         data = new double[initialCapacity];
      } else {
         throw new IllegalArgumentException("InitialCapacity is negative: " + initialCapacity);

      }
   }

   /**
    * Add a new element to this sequence, after the current element. If the new
    * element would take this sequence beyond its current capacity, then the
    * capacity is increased before adding the new element.
    * 
    * @param element the new element that is being added
    * @postcondition A new copy of the element has been added to this sequence. If
    *                there was a current element, then the new element is placed
    *                after the current element. If there was no current element,
    *                then the new element is placed at the end of the sequence. In
    *                all cases, the new element becomes the new current element of
    *                this sequence.
    * @exception OutOfMemoryError Indicates insufficient memory for increasing the
    *                             sequence's capacity.
    * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
    *       the sequence to fail with an arithmetic overflow.
    **/
   public void addAfter(double element) {
      ensureCapacity(manyItems + 1);
      for (int i = manyItems; i > currentIndex; i--)
         data[i] = data[i - 1];
      data[currentIndex + 1] = element;
      manyItems++;
      getCurrent();

   }

   /**
    * Add a new element to this sequence, before the current element. If the new
    * element would take this sequence beyond its current capacity, then the
    * capacity is increased before adding the new element.
    * 
    * @param element the new element that is being added
    * @postcondition A new copy of the element has been added to this sequence. If
    *                there was a current element, then the new element is placed
    *                before the current element. If there was no current element,
    *                then the new element is placed at the start of the sequence.
    *                In all cases, the new element becomes the new current element
    *                of this sequence.
    * @exception OutOfMemoryError Indicates insufficient memory for increasing the
    *                             sequence's capacity.
    * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
    *       the sequence to fail with an arithmetic overflow.
    **/
   public void addBefore(double element) {
      if (manyItems == data.length) {
         ensureCapacity(manyItems * 2 + 1);
      }
      if (!isCurrent())
         currentIndex = 0;
      for (int i = manyItems; i > currentIndex; i--)
         data[i] = data[i - 1];
      data[currentIndex] = element;
      manyItems++;
      advance();

   }

   /**
    * Place the contents of another sequence at the end of this sequence.
    * 
    * @param addend a sequence whose contents will be placed at the end of this
    *               sequence
    * @precondition The parameter, addend, is not null.
    * @postcondition The elements from addend have been placed at the end of this
    *                sequence. The current element of this sequence remains where
    *                it was, and the addend is also unchanged.
    * @exception NullPointerException Indicates that addend is null.
    * @exception OutOfMemoryError     Indicates insufficient memory to increase the
    *                                 size of this sequence.
    * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
    *       an arithmetic overflow that will cause the sequence to fail.
    **/
   public void addAll(DoubleArraySeq addend) {
      ensureCapacity(manyItems + addend.manyItems);

      System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
      manyItems += addend.manyItems;
   }

   /**
    * Move forward, so that the current element is now the next element in this
    * sequence.
    * 
    * @parameter - none
    * @precondition isCurrent() returns true.
    * @postcondition If the current element was already the end element of this
    *                sequence (with nothing after it), then there is no longer any
    *                current element. Otherwise, the new element is the element
    *                immediately after the original current element.
    * @exception IllegalStateException Indicates that there is no current element,
    *                                  so advance may not be called.
    **/
   public void advance() {
      currentIndex++;
   }

   /**
    * Generate a copy of this sequence.
    * 
    * @parameter - none
    * @return The return value is a copy of this sequence. Subsequent changes to
    *         the copy will not affect the original, nor vice versa.
    * @exception OutOfMemoryError Indicates insufficient memory for creating the
    *                             clone.
    **/

   @Override
   public DoubleArraySeq clone() { // This is written by book author Michael Main
      DoubleArraySeq answer;

      try {
         answer = (DoubleArraySeq) super.clone();
      } catch (CloneNotSupportedException e) { // This exception should not occur. But if it does, it would probably
                                               // indicate a programming error that made super.clone unavailable.
                                               // The most common error would be forgetting the "Implements Cloneable"
                                               // clause at the start of this class.
         throw new RuntimeException("This class does not implement Cloneable");
      }

      answer.data = data.clone();

      return answer;
   }

   /**
    * Create a new sequence that contains all the elements from one sequence
    * followed by another.
    * 
    * @param s1 the first of two sequences
    * @param s2 the second of two sequences
    * @precondition Neither s1 nor s2 is null.
    * @return a new sequence that has the elements of s1 followed by the elements
    *         of s2 (with no current element)
    * @exception NullPointerException Indicates that one of the arguments is null.
    * @exception OutOfMemoryError     Indicates insufficient memory for the new
    *                                 sequence.
    * @note An attempt to create a sequence with a capacity beyond
    *       Integer.MAX_VALUE will cause an arithmetic overflow that will cause the
    *       sequence to fail.
    **/
   public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) {
      DoubleArraySeq s3 = new DoubleArraySeq(s1.manyItems + s2.manyItems);
      System.arraycopy(s1, 0, s3, 0, s1.manyItems);
      System.arraycopy(s2, 0, s3, s1.manyItems, s2.manyItems);
      s3.manyItems = (s1.manyItems + s2.manyItems);
      s3.currentIndex = s3.manyItems;
      return s3;
   }

   /**
    * Change the current capacity of this sequence.
    * 
    * @param minimumCapacity the new capacity for this sequence
    * @postcondition This sequence's capacity has been changed to at least
    *                minimumCapacity. If the capacity was already at or greater
    *                than minimumCapacity, then the capacity is left unchanged.
    * @exception OutOfMemoryError Indicates insufficient memory for: new
    *                             int[minimumCapacity].
    **/
   public void ensureCapacity(int minimumCapacity) {
      double[] biggerArray;
      if (data.length < minimumCapacity) {
         biggerArray = new double[minimumCapacity];
         System.arraycopy(data, 0, biggerArray, 0, manyItems);
         data = biggerArray;
      }
   }

   /**
    * Accessor method to get the current capacity of this sequence. The add method
    * works efficiently (without needing more memory) until this capacity is
    * reached.
    * 
    * @parameter - none
    * @return the current capacity of this sequence
    **/
   public int getCapacity() {
      return data.length - manyItems;
   }

   /**
    * Accessor method to get the current element of this sequence.
    * 
    * @param - none
    * @precondition isCurrent() returns true.
    * @return the current element of this sequence
    * @exception IllegalStateException Indicates that there is no current element,
    *                                  so getCurrent may not be called.
    **/
   public double getCurrent() {
      if (isCurrent())
         return data[currentIndex];
      else
         throw new IllegalStateException("There is no current element");
   }

   /**
    * Accessor method to determine whether this sequence has a specified current
    * element that can be retrieved with the getCurrent method.
    * 
    * @param - none
    * @return true (there is a current element) or false (there is no current
    *         element at the moment)
    **/
   public boolean isCurrent() {
      boolean answer = false;
      if (currentIndex <= data.length)
         answer = true;

      return answer;
   }

   /**
    * Remove the current element from this sequence.
    * 
    * @param - none
    * @precondition isCurrent() returns true.
    * @postcondition The current element has been removed from this sequence, and
    *                the following element (if there is one) is now the new current
    *                element. If there was no following element, then there is now
    *                no current element.
    * @exception IllegalStateException Indicates that there is no current element,
    *                                  so removeCurrent may not be called.
    **/
   public void removeCurrent() {
      if (isCurrent()) {
         for (int i = currentIndex; i < manyItems; i++) {
            data[i] = data[i + 1];
         }
         manyItems--;
      } else {
         throw new IllegalStateException("There is no current element");
      }
   }

   /**
    * Determine the number of elements in this sequence.
    * 
    * @param - none
    * @return the number of elements in this sequence
    **/
   public int size() {
      return manyItems;
   }

   /**
    * Set the current element at the front of this sequence.
    * 
    * @param - none
    * @postcondition The front element of this sequence is now the current element
    *                (but if this sequence has no elements at all, then there is no
    *                current element).
    * @exception IllegalArgumentException Indicates empty sequence
    **/
   public void start() {
      if (manyItems == 0)
         throw new IllegalArgumentException("There is nothing in the sequence");
      currentIndex = 0;
   }

   /**
    * Reduce the current capacity of this sequence to its actual size (i.e., the
    * number of elements it contains).
    * 
    * @param - none
    * @postcondition This sequence's capacity has been changed to its current size.
    * @exception OutOfMemoryError Indicates insufficient memory for altering the
    *                             capacity.
    **/
   public void trimToSize() { // This is written by book author Michael Mian
      double[] trimmedArray;

      if (data.length == manyItems) {
         throw new OutOfMemoryError("There is not enough memory to trim size");

      }
      trimmedArray = new double[manyItems];
      System.arraycopy(data, 0, trimmedArray, 0, manyItems);
      data = trimmedArray;

   }

   /**
    * Adds a number to the front of the sequence
    * 
    * @param - frontNum, the new element to be added to the sequence
    * @postcondition The new element has been added to the front of the sequence
    *                and if the sequence was too short the size has been increased
    *                by 1.
    * 
    **/
   public void addFront(double frontNum) {

      if (size() == data.length - 1)
         ensureCapacity(manyItems * 2 + 1); // increase the capacity

      if (size() > 0) {

         for (int i = manyItems; i >= 1; i--) {
            data[i] = data[i - 1]; // moves one element down in the sequence
         }
         data[0] = frontNum;
         manyItems++;
      } else {
         data[0] = frontNum;
         manyItems++;
      }
   }

   /**
    * Remove the front element of the sequence
    * 
    * @param - none
    * @postcondition The front element has been removed and the sequence contains
    *                one less element
    * @exception IllegalArgumentException Indecates sequence is empty
    **/
   public void removeFront() {
      if (size() == 0) {
         throw new IllegalArgumentException("Sorry, there is nothing in the sequence");

      }
      currentIndex = 0;
      removeCurrent();
      data[manyItems] = 0;
   }

   /**
    * Add a number to the end of the sequence
    * 
    * @param - endNum, the number to be added
    * @postcondition The number has been added to the end of the sequence and if
    *                the sequence was too short the size has been increased by 1.
    * @exception OutOfMemoryError Indicates insufficient memory for altering the
    *                             capacity.
    **/
   public void addEnd(double endNum) {
      try {
         if (size() >= data.length)
            ensureCapacity(manyItems * 2 + 1);
         data[manyItems] = endNum; // add's number to the end
         manyItems++;

      } catch (IllegalArgumentException e) {
         System.out.println("This is not a number");
      }
   }

   /**
    * Set the current index to the last element of the sequence
    * 
    * @param - none
    * @postcondition The current element is now the last element
    * @exception IllegalStateException Indicates the sequence is empty or does not
    *                                  contain any elements.
    **/
   public void setCurrentLast() {

      if (manyItems != 0)
         currentIndex = manyItems - 1;
      else {
         throw new IllegalStateException("The sequence is empty or does not contain any elements");
      }
   }

   /**
    * Set the current index to n and return the element at n
    * 
    * @param - n the index to be retrived
    * @precondition n is not negative and the sequence contains at least n elements
    * @postcondition The current element is now n
    * @exception IllegalStateException Indicates the sequence is empty or does not
    *                                  contain any elements.
    **/
   public double getElement(int n) {
      if (manyItems == 0)
         throw new IllegalArgumentException("There is nothing in the array");
      if (n < manyItems) {
         currentIndex = n;
         return data[n];
      } else {
         throw new IllegalStateException(
               "The element cannot be retrived because the sequence is empty or does not contain that many elements.");
      }
   }

   /**
    * Sets the current index to location.
    * 
    * @param - location, the new current index
    * @postcondition The current index is set to location.
    * @exception IllegalStateException Indicates the sequence is empty or location
    *                                  is larger than the sequence length
    **/
   public void setCurrent(int location) {

      if (location > manyItems || location < 0) {
         throw new IllegalStateException("setCurrent could not complete because " + ""
               + "the sequence is empty or the index specified is smaller/larger than the sequence length.");
      }
      currentIndex = location;
   }

   /**
    * Return true if both object are equal
    * 
    * @param - obj is a general object
    * @poscondition If both object are equal it will return true
    * @return - true if equal or false if not equal
    */
   public boolean equals(Object obj) {
      boolean areEqual = true;
      if (obj instanceof DoubleArraySeq) // Checking if obj is DoubleArraySeq
      {
         DoubleArraySeq temp = (DoubleArraySeq) obj; // Typecasting obj as DoubleArraySeq
         if (this.manyItems == temp.manyItems) {
            for (int i = 0; i < manyItems; i++) {
               if (this.data[i] != temp.data[i])
                  areEqual = false;
            } // for loop

         } // end if
      } // end if
      return areEqual;
   }// equal method

   /**
    * Overrides the method from Object class and outputs sequense as string
    * 
    * @param
    * @postcondition It returns message as String
    * @exception IllegalStateException Indicates no current element
    */
   public String toString() {
      String message = " "; // create a new string that will be our message
      if (size() <= 0)
         message += "Sorry, your sequence is empty!";
      else {
         for (int i = 0; i < manyItems; i++) { // for each of the elements in the sequence
            message += data[i] + " "; // add its value to the message separated by a comma
         }
         message += "\nNumber of items in the sequence: " + manyItems; // add a little more information about the size

         try {
            message += "\nCurrent element in the sequence: " + getCurrent();
         } catch (IllegalStateException e) {
            message += "\nThere isn't any current element in the sequence ";
         }

      }
      return message; // return the message as a string

   }

}
