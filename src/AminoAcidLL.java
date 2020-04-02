import javax.lang.model.element.AnnotationMirror;

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

  private void incrementCodons(String c){
    for(int i = 0; i < counts.length; i++){
      if(codons[i].equals(c)){
        counts[i]++;
      }
    }
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
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    //add all the elements in count
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
  //TODO

  /* Recursive method that finds the differences in **Amino Acid** counts. 
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList){
    /*
    
     */
    return 0;
  }

  /********************************************************************************************/
  //TODO

  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */
  //check for empty, null, what to do if they have the same count, if they don't have the smae count which one is greater and how can you tell
  public int codonCompare(AminoAcidLL inList){
    return 0;
  }


  /********************************************************************************************/
  //TODO

  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){
    /*
    combination of loops and recursion

     */
    //translate from linked list into array of characters
    if(next == null){
      return new char[aminoAcid];
    }
    char[] a = next.aminoAcidList();
    System.out.println(a);
    char [] b = new char[a.length + 1]; // add this node's amino acid
//    for(int i = b.length; i < b.length; i++){
//      a[i + 1] = b[i];
//    }

    return a;
  }

  /********************************************************************************************/
  //TODO

  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){
    return new int[]{};
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    if(next == null) {
      return true;
    }
    return (aminoAcid <= next.aminoAcid && next.isSorted());
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    String codon = inSequence.substring(0, 3);
    inSequence = inSequence.substring(codon.length());
    AminoAcidLL head = new AminoAcidLL(codon);

    while(inSequence.length() != 0){
      codon = inSequence.substring(0, 3);
      inSequence = inSequence.substring(codon.length());
      head.addCodon(codon);
    }

    return head;
  }


  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  public static AminoAcidLL sort(AminoAcidLL inList){
    if(inList == null || inList.next == null){
      return inList;
    }

    AminoAcidLL middle = middleNode(inList);
    AminoAcidLL rightOfMiddle = middle.next;
    middle.next = null;

    return mergeSorted(sort(inList), sort(rightOfMiddle));
  }

  /********************************************************************************************/
  /*
  Helper method that functions as the merging method for the sort (merge sort) method
   */
  private static AminoAcidLL mergeSorted(AminoAcidLL a, AminoAcidLL b) {
    AminoAcidLL temp = new AminoAcidLL();
    AminoAcidLL sortedList = temp;

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