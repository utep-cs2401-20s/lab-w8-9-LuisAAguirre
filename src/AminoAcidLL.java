/*
 *
 *
 Sources:
 https://www.geeksforgeeks.org/merge-sort-for-linked-list/
 Used to help understand how merge sort would work applied to linked lists
 *
 *
 */


class AminoAcidLL{
  char aminoAcid;
  String[] codons;
  int[] counts;
  AminoAcidLL next;

  AminoAcidLL(){

  }


  /********************************************************************************************/
  /* Creates a new node, with a given amino acid/codon 
   * pair and increments the codon counter for that codon.
   * NOTE: Does not check for repeats!! */
  AminoAcidLL(String inCodon){
    aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
    codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
    counts = new int[codons.length];
    incrementCodons(inCodon);
    next = null;
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops, 
   * if not passes the task to the next node. 
   * If there is no next node, add a new node to the list that would contain the codon. 
   */
  private void addCodon(String inCodon){
    if(aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)){
      incrementCodons(inCodon);
      return;
    }
    else if(next == null){
      next = new AminoAcidLL(inCodon);
    }
    else {
      next.addCodon(inCodon);
    }
  }
  /********************************************************************************************/
  /*
  Helper method that increments counts array value if an amino acid is already present in the linked list.
   */
  private void incrementCodons(String c){
    for(int i = 0; i < counts.length; i++){
      if(codons[i].equals(c)){
        counts[i]++;
      }
    }
  }


  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    int sum = 0;
    for(int i = 0; i < counts.length; i++){
      sum += counts[i];
    }
    return sum;
  }

  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int totalDiff(AminoAcidLL inList){
    return Math.abs(totalCount() - inList.totalCount());
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int codonDiff(AminoAcidLL inList){
    int diff = 0;
    for(int i=0; i<codons.length; i++){
      diff += Math.abs(counts[i] - inList.counts[i]);
    }
    return diff;
  }

  /********************************************************************************************/
  /* Recursive method that finds the differences in **Amino Acid** counts. 
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList){
    // Base case. Will return the value of totalDiff when both next and inList.next are null and there are no more values to compare
    if(next == null && inList.next == null){
      return totalDiff(inList);
    }

    // If next is null but inList.next still has values to compare, an empty node will be created and passed in the recursion to avoid null pointer errors
    if(next == null){
      return totalDiff(inList) + new AminoAcidLL("").aminoAcidCompare(inList.next);
    }

    // If inList.next is null but next still has values to compare, an empty node will be created and passed in the recursion to avoid null pointer errors
    if(inList.next == null){
      return totalDiff(inList) + next.aminoAcidCompare(new AminoAcidLL(""));
    }

    // Recursive step
    return totalDiff(inList) + next.aminoAcidCompare(inList.next);
  }

  /********************************************************************************************/
  /* Same as above, but counts the codon usage differences
   * Must be sorted. */
  //check for empty, null, what to do if they have the same count, if they don't have the same count which one is greater and how can you tell
  public int codonCompare(AminoAcidLL inList){

    // If codons array's length is greater than inList counts length an array will be created for inList that is the same length as codons
    if(codons.length > inList.codons.length){
      int[] a = new int[counts.length];
      for(int i = 0; i < inList.counts.length; i++){
        a[i] = inList.counts[i];
      }
      inList.counts = a;
    }

    // If inList codons array's length is greater than counts length an array will be created for codons that is the same length as inList codons array
    if(codons.length < inList.codons.length){
      int[] b = new int[inList.counts.length];
      for(int i = 0; i < counts.length; i++){
        b[i] = counts[i];
      }
      counts = b;
    }

    // Base case. If both are null return last difference in codons
    if(next == null && inList.next == null){
      return codonDiff(inList);
    }

    // If next is null but inList.next still has values to compare, an empty node will be created and passed in the recursion to avoid null pointer errors
    if(next == null){
      return codonDiff(inList) + new AminoAcidLL("").codonCompare(inList.next);
    }

    // If inList.next is null but next still has values to compare, an empty node will be created and passed in the recursion to avoid null pointer errors
    if(inList.next == null){
      return codonDiff(inList) + next.codonCompare(new AminoAcidLL(""));
    }

    // Recursive step
    return codonDiff(inList) + next.codonCompare(inList.next);
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){

    // Base case
    if(next == null){
      return new char[]{aminoAcid};
    }

    char[] a = next.aminoAcidList();      // Recursive step
    char [] b = new char[a.length + 1];

    // for loop arranges values in correct order
    for(int i = 0; i < b.length; i++){
      if(i == 0){
        b[i] = this.aminoAcid;
      } else{
        b[i] = a[i - 1];
      }
    }

    return b;
  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){

    // Base case
    if(next == null){
      return new int[]{totalCount()};
    }

    int[] a = next.aminoAcidCounts();     // Recursive step
    int [] b = new int[a.length + 1];

    // for loop arranges values in correct order
    for(int i = 0; i < b.length; i++){
      if(i == 0){
        b[i] = this.totalCount();
      } else{
        b[i] = a[i - 1];
      }
    }

    return b;
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){

    //Base case
    if(next == null) {
      return true;
    }

    return (aminoAcid < next.aminoAcid && next.isSorted());
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){

    AminoAcidLL head;

    // Help avoid errors when dividing strings that have length less than 3 and do not form codon
    if(inSequence.length() < 3){
      head = new AminoAcidLL(inSequence);     // Creates linked list's head
    } else {
      head = new AminoAcidLL(inSequence.substring(0, 3));     // Creates linked list's head
      inSequence = inSequence.substring(3);     /// Creates codon from which amino acids will be determined
    }

    // Creates linked list nodes
    while(inSequence.length() >= 3){
      head.addCodon(inSequence.substring(0, 3));      /// Creates codon from which amino acids will be determined
      inSequence = inSequence.substring(3);
    }

    return head;
  }


  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  public static AminoAcidLL sort(AminoAcidLL inList){

    // Base case
    if(inList == null || inList.next == null){
      return inList;
    }

    AminoAcidLL middle = middleNode(inList);      // Finds left side of linked list for merge sort
    AminoAcidLL rightOfMiddle = middle.next;      // Finds right side of linked list for merge sort
    middle.next = null;

    return mergeSorted(sort(inList), sort(rightOfMiddle));      // Recursive call for merge sort
  }

  /********************************************************************************************/
  /*
  Helper method that functions as the merging method for the sort (merge sort) method
   */
  private static AminoAcidLL mergeSorted(AminoAcidLL a, AminoAcidLL b) {
    AminoAcidLL temp = new AminoAcidLL();
    AminoAcidLL sortedList = temp;

    // Loop merges linked lists nodes in the correct order
    while(a != null && b != null) {
      if (a.aminoAcid < b.aminoAcid) {
        temp.next = a;
        a = a.next;
      } else {
        temp.next = b;
        b = b.next;
      }
      temp = temp.next;
    }

    //
    if(a == null) {
      temp.next = b;
    } else{
      temp.next = a;
    }

    return sortedList.next;
  }

  /********************************************************************************************/
  /*
  Helper method that finds the middle node on a linked list. Used in sort method (merge sort)
   */
  public static AminoAcidLL middleNode(AminoAcidLL inList){
    if(inList == null){
      return null;
    }

    AminoAcidLL a = inList;
    AminoAcidLL b = inList.next;

    while(b != null && b.next != null){
      a = a.next;
      b = b.next.next;
    }

    return a;
  }
}