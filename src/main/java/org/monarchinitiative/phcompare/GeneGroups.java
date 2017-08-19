package org.monarchinitiative.phcompare;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * GeneGroups reads from a file of gene information to create two sets of gene names that will determine the two
 * sets of patients to be compared by {@link PhenoCompare}. Gene names are Strings. The file of gene names
 * contains two lines, one for each set of gene names. The gene name file is in tsv format.
 * @author Hannah Blau (blauh)
 * @version 0.0.1
 * @since 14 Aug 2017
 */
class GeneGroups {
    // names of the genes in the early part of the biochemical pathway
    private Set<String> earlyGenes = new TreeSet<>();
    // names of the genes in the late part of the biochemical pathway
    private Set<String> lateGenes = new TreeSet<>();

    /**
     * Reads two lists of gene names from specified file and creates sets of early, late genes
     * @param path              file containing two lists of genes (for the two groups of patients)
     * @throws IOException      if file cannot be found
     * @throws EmptyGroupException  if after reading file, one or both gene groups is/are empty
     */
    GeneGroups(String path) throws IOException, EmptyGroupException {
        File genesFile = new File(path);

        if (!genesFile.exists()) {
            throw new IOException("[GeneGroups.GeneGroups] Cannot find genes file " + path +
                    System.lineSeparator());
        }
        Scanner scan = new Scanner(genesFile);
        if (scan.hasNextLine())
            readGeneNames(scan.nextLine(), earlyGenes);
        if (scan.hasNextLine())
            readGeneNames(scan.nextLine(), lateGenes);
        scan.close();
        if (earlyGenes.isEmpty() || lateGenes.isEmpty()) {
            throw new EmptyGroupException("[GeneGroups.GeneGroups] Empty group of genes from file " +
                    genesFile + System.lineSeparator());
        }
    }

    /**
     * @return    Set of Strings, the names of the genes in the early part of the biochemical pathway
     */
    Set<String> getEarlyGenes() {
        return earlyGenes;
    }

    /**
     * @return    Set of Strings, the names of the genes in the late part of the biochemical pathway
     */
    Set<String> getLateGenes() {
        return lateGenes;
    }

    /**
     * @param geneName   name of gene to be tested
     * @return    true if geneName belongs to the set of early genes in this GeneGroup; false otherwise
     */
    boolean isEarlyGene(String geneName) {
        return earlyGenes.contains(geneName);
    }

    /**
     * @param geneName   name of gene to be tested
     * @return    true if geneName belongs to the set of late genes in this GeneGroup; false otherwise
     */
    boolean isLateGene(String geneName) {
        return lateGenes.contains(geneName);
    }

    /**
     * Reads a line containing multiple gene names separated by tab characters, adds each gene name to
     * the specified set.
     * @param line      line of input to be parsed
     * @param geneSet   set of gene names
     */
    private void readGeneNames(String line, Set<String> geneSet) {
        Scanner scn = new Scanner(line).useDelimiter("\\t");

        while (scn.hasNext()) {
            geneSet.add(scn.next());
        }
        scn.close();
    }
}