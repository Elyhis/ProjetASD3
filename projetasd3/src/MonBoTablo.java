import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MonBoTablo {
    public static void main(String[] args) throws IOException {

        //File Management
        File inputFile = new File(args[1]);
        String fileName = inputFile.getName();
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        String outputDirectoryName = args[2];
        File outputDirectory = new File(args[2]);
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        //Pktree Management
        ArrayList<Center> centerTab = new ArrayList<>(); //Creation of required Arrays to read the file
        ArrayList<Recolor> recolorTab = new ArrayList<>();
        ArrayList<String> directionTab = new ArrayList<>();
        //1st variant---------------------------------------------------------------------------------------------------------------------------------
        if(args[0].equals("1")){
            //Before Recolor
            PQuadtree tree = new PQuadtree(); //Create an empty PQuadTree
            Image im = tree.readFile(inputFile, centerTab, recolorTab, directionTab); //Create an Image and fill the tabs required to build the PQuadTree
            tree.buildQTree(centerTab); //Build the PQuadTree
            tree.toImage(im); //Fill the image with the content of the PQuadTree
            im.save(outputDirectoryName + "/MorineauBarbe" + fileName + "_B.png"); //Save the 1st picture
            String text1 = tree.toText("");
            FileWriter textFile1 = new FileWriter(outputDirectoryName + "/MorineauBarbe" + fileName + "_B.txt",true);
            textFile1.write(text1);
            textFile1.flush();
            textFile1.close();

            //After Recolor
            for (Recolor recolor : recolorTab) { //Recolor the PQuadTree
                tree.reColor(recolor.getPoint(), recolor.getColor());
            }
            tree.toImage(im);
            im.save(outputDirectoryName + "/MorineauBarbe" + fileName + "_R.png"); //Save the 1st picture
            String text2 = tree.toText("");
            FileWriter textFile2 = new FileWriter(outputDirectoryName + "/MorineauBarbe" + fileName + "_R.txt");
            textFile2.write(text2);
            textFile2.flush();
            textFile2.close();
        }
        //2nd variant---------------------------------------------------------------------------------------------------------------------------------
        if(args[0].equals("2")){
            //Before Recolor
            P3Tree tree = new P3Tree(); //Create an empty P3tree
            Image im = tree.readFile(inputFile, centerTab, recolorTab, directionTab); //Create an Image and fill the tabs required to build the P3tree
            tree.buildQTree(centerTab, directionTab); //Build the P3tree
            tree.toImage(im); //Fill the image with the content of the P3tree
            im.save(outputDirectoryName + "/MorineauBarbe" + fileName + "_B.png"); //Save the 1st picture
            String text1 = tree.toText("");
            FileWriter textFile1 = new FileWriter(outputDirectoryName + "/MorineauBarbe" + fileName + "_B.txt",true);
            textFile1.write(text1);
            textFile1.flush();
            textFile1.close();

            //After Recolor
            for (Recolor recolor : recolorTab) { //Recolor the P3tree
                tree.reColor(recolor.getPoint(), recolor.getColor());
            }
            tree.toImage(im);
            im.save(outputDirectoryName + "/MorineauBarbe" + fileName + "_R.png"); //Save the 1st picture
            String text2 = tree.toText("");
            FileWriter textFile2 = new FileWriter(outputDirectoryName + "/MorineauBarbe" + fileName + "_R.txt");
            textFile2.write(text2);
            textFile2.flush();
            textFile2.close();
        }


    }
}