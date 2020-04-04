import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.AnnotationMirror;

public class AminoAcidLLTester {

    @Test
    /*
    First case will test createFromRNASequence() method. A linked list will be manually created using the
    AminoAcidLL(String inCodon) constructor to ensure all nodes are directly created instead of having to go though
    other methods.
    As createdFromRNASequence() method calls the addCodon(String inCodon) method, addCodon will also be tested, ensuring
    nodes are being added correctly.
     */
    public void createFromRNASequenceTest1() {

        AminoAcidLL a1 = new AminoAcidLL("AAG");
        a1.next = new AminoAcidLL("ACC");
        a1.next.next = new AminoAcidLL("AUG");
        a1.next.next.next = new AminoAcidLL("CUU");
        a1.next.next.next.next = new AminoAcidLL("CCC");

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("AAGACCAUGCUUCCC");
        while (protein != null) {
            assertEquals(a1.aminoAcid, protein.aminoAcid);
            protein = protein.next;
            a1 = a1.next;
        }
    }

    @Test
    /*
    This case will be similar as the one above. This test will also test how the method handles extra letters
    that do not form a codon. The method will have to ignore this characters added at the end and create the same
    linked list as the linked list that will be created manually.
     */
    public void createFromRNASequenceTest2() {
        AminoAcidLL a1 = new AminoAcidLL("UGU");
        a1.next = new AminoAcidLL("UUC");
        a1.next.next = new AminoAcidLL("GAU");
        a1.next.next.next = new AminoAcidLL("AAG");
        a1.next.next.next.next = new AminoAcidLL("GGG");

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("UGUUUCGAUAAGGGGJU");
        while (protein != null) {
            assertEquals(a1.aminoAcid, protein.aminoAcid);
            protein = protein.next;
            a1 = a1.next;
        }
    }

    @Test
    /*
    This case will test aminoAcidList() method. A chr array of length 7 will be created with different "amino acids".
    A linked list of 7 nodes will then be created and the method must return an array with the same elements as the
    precious array created.
     */
    public void aminoAcidListTest1() {
        char[] test = {'A', 'I', 'W', 'T', 'Q', 'K', 'L'};

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("GCCAUCUGGACUCAAAAACUU");

        assertArrayEquals(test, protein.aminoAcidList());
    }

    @Test
    /*
    This case will test aminoAcidList() method. A chr array of length 8 will be created. A linked list of 8 nodes
    will then be created and the method must return an array with the same elements as the precious array created.
    This test will deal with different codons that create amino acids already present in the linked list.
    The code should ignore duplicate amino acids and will increase the count of that amino acid in the counts array.
    ***Array counts will not be tested here***
     */
    public void aminoAcidListTest2() {
        char[] test = {'A', 'P', 'I', 'W', 'T', 'Q', 'K', 'L'};

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("GCCCCCCCUAUCUGGACUCAAAAACUUUUGUUAAUAGCG");

        assertArrayEquals(test, protein.aminoAcidList());
    }

    @Test
    /*
    This case will test aminoAcidCounts(). An integer array of length 5 will be created with different values in each index.
    A linked list will be created to match the values within that array. The point of this test is to make sure that
    each duplicate amino acid is not created as a new node but instead the count for that amino acid is increased in the
    a node already created for that amino acid.
     */
    public void aminoAcidCountsTest() {
        char[] reference = {'E', 'D', 'K', 'F', 'G'};
        int[] countTest = {3, 2, 1, 5, 3};

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("GAGGAGGAGGACGACAAGUUCUUCUUCUUCUUCGGGGGGGGG");

        assertArrayEquals(countTest, protein.aminoAcidCounts());
    }

    @Test
    /*
    This case will test sort() method. A linked list of 5 nodes that is already sorted will be created.
    An unsorted linked list of the same length will be created using createFromRNASequence() then the sort() method will be
    applied to it and checked against the already sorted linked list to ensure the amino acids in them are arranged
    in the same order.
     */
    public void sortTest1() {
        AminoAcidLL sorted = AminoAcidLL.createFromRNASequence("GCCGACGAAAUGGUG");

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("GUUGAAGACAUGGCC");
        protein = AminoAcidLL.sort(protein);

        while (protein != null) {
            assertEquals(sorted.aminoAcid, protein.aminoAcid);
            protein = protein.next;
            sorted = sorted.next;
        }
    }

    @Test
    /*
    This case will test how the sort method deals with an already sorted linked list. A new node will be added to
    an already sorted array and the new linked list will be sorted again.
     */
    public void sortTest2() {
        AminoAcidLL sorted = AminoAcidLL.createFromRNASequence("CACAUACGGUCAACA");

        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("CACAUAUCAACA");
        AminoAcidLL temp = protein;
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = new AminoAcidLL("CGG");

        protein = AminoAcidLL.sort(protein);

        while (protein != null) {
            assertEquals(sorted.aminoAcid, protein.aminoAcid);
            protein = protein.next;
            sorted = sorted.next;
        }
    }

    @Test
    /*
    This case will test isSorted() method. An already sorted linked list will be created and isSorted() will be applied
    to it. Since the linked list is already sorted, the method should return true.
     */
    public void isSortedTest1() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("GCAGACGGGCAUAUCCUACAAUAC");

        assertTrue(protein.isSorted());
    }

    @Test
    /*
    This case will test isSorted() method. An unsorted linked list will be created and isSorted() will be applied
    to it. Since the linked list is not sorted assertFalse() will return true.
     */
    public void isSortedTest2() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("AGGCAAGGAGUAGCA");

        assertFalse(protein.isSorted());
    }

    @Test
    /*
    This case will test aminoAcidCompare(). Two linked lists of different sizes and composition will be created.
    Since the linked lists will be of different size this case will also test how the method handles null pointers.
     */
    public void aminoAcidCompareTest1() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("AUUAUUAUCGAAAUCAAGAUUCAAAGACGGCAG");
        AminoAcidLL protein2 = AminoAcidLL.createFromRNASequence("CUGCUCUUACUUGGGGGUGGUUGGGCUGCAGCAGCG");

        protein = AminoAcidLL.sort(protein);
        protein2 = AminoAcidLL.sort(protein2);

        /*
        protein:                         Protein 2:
        E I K Q R                        A G L W
        E: 0 1                           A: 1 2 0 1             |1 - 4| = 3
        I: 0 2 3                         G: 1 0 0 2             |5 - 3| = 2
        K: 1 0                           L: 1 0 1 1 0 1         |1 -4| =  3
        Q: 1 1                           W: 1                   |2 - 1 =  1
        R: 0 1 1 0 0 0                                          |2 - 0| = 2
                                                                        = 11
         */

        assertEquals(11, protein.aminoAcidCompare(protein2));

    }

    @Test
    /*
    This case will test aminoAcidCompare(). This test will ensure the method works properly by comparing a linked list
    against itself. The method should return 0 since there is no difference between them.
     */
    public void aminoAcidCompareTest2() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("CAUAAUAUGUGGGACGAUGAUCCGCCCGAGAAGAAA");
        AminoAcidLL protein2 = AminoAcidLL.createFromRNASequence("CAUAAUAUGUGGGACGAUGAUCCGCCCGAGAAGAAA");

        protein = AminoAcidLL.sort(protein);
        protein2 = AminoAcidLL.sort(protein2);

        assertEquals(0, protein.aminoAcidCompare(protein2));
    }

    @Test
    /*
    This case will test codonCompare(). This test will ensure the method works properly by comparing a linked list
    against itself. The method should return 0 since there is no difference between them.
     */
    public void codonCompareTest1() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("CAUAAUAUGUGGGACGAUGAUCCGCCCGAGAAGAAA");
        AminoAcidLL protein2 = AminoAcidLL.createFromRNASequence("CAUAAUAUGUGGGACGAUGAUCCGCCCGAGAAGAAA");

        protein = AminoAcidLL.sort(protein);
        protein2 = AminoAcidLL.sort(protein2);

        assertEquals(0, protein.codonCompare(protein2));
    }

    @Test
    /*
    This case will test codonCompare(). Two linked lists of different sizes and composition will be created.
    Since the linked lists will be of different size this case will also test how the method handles null pointers.
     */
    public void codonCompareTest2() {
        AminoAcidLL protein = AminoAcidLL.createFromRNASequence("CCGGACCCCCCCGGUUUGCUAAGUCGGCGACGAUCG");
        AminoAcidLL protein2 = AminoAcidLL.createFromRNASequence("UGCUUUUUUCACAAGAAA");

        protein = AminoAcidLL.sort(protein);
        protein2 = AminoAcidLL.sort(protein2);

        /*
        protein:                         Protein 2:
        C F H K                          D G L P R S
        C: 1 0                           D: 1 0
        F: 0 2                           G: 0 0 0 1
        H: 1 0                           L: 0 1 0 0 1 0
        K: 1 1                           P: 1 0 2 0
                                         R: 0 0 1 2 0 0
                                         S: 0 1 1 0 0 0
         */

        assertEquals(8, protein.aminoAcidCompare(protein2));

    }

}
