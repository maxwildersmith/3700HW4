package Independence;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        System.out.println("\nPerforming part 3 of the assignment...\n");
        reverse();
        translate();
    }

    private static void reverse(){
        System.out.println("\nReversing the Declaration of Independence....");

        long sTime = System.currentTimeMillis();
        TextReverse.singleThreadReverse("DeclarationIndependence.txt","backwards.txt");
        System.out.println("Time for single threaded reversing: "+(System.currentTimeMillis()-sTime)+"ms");

        sTime = System.currentTimeMillis();
        TextReverse.multiThreadReverse("DeclarationIndependence.txt","backwards.txt",Runtime.getRuntime().availableProcessors());
        System.out.println("Time for multi threaded reversing: "+(System.currentTimeMillis()-sTime)+"ms");
    }

    private static void translate(){
        System.out.println("\nTranslating the Declaration of Independence to French....\n\t(make sure the GOOGLE_APPLICATION_CREDENTIALS env variable is properly configured)");
        String translated;
        long sTime = System.currentTimeMillis();
        Translator.singleThread("DeclarationIndependence.txt","fr");
        System.out.println("Time for single threaded translation: "+(System.currentTimeMillis()-sTime)+"ms");

        sTime = System.currentTimeMillis();
        translated = Translator.multiThread("DeclarationIndependence.txt","fr",Runtime.getRuntime().availableProcessors());
        System.out.println("Time for multi threaded translation: "+(System.currentTimeMillis()-sTime)+"ms");
        try {
            FileOutputStream out = new FileOutputStream("translated.txt");
            out.write(translated.getBytes());
            out.close();
            System.out.println("Wrote translated text to translated.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper function to ensure two files are the exact same
     * @param inFile First file to check against
     * @param outFile Second file to check
     * @return True if they are the same, false otherwise
     */
    public static boolean verifyCorrect(String inFile, String outFile){
        try {
            byte[] file1 = new FileInputStream(inFile).readAllBytes();
            byte[] file2 = new FileInputStream(outFile).readAllBytes();
            if(file1.length!=file2.length)
                return false;
            for(int i=0;i<file1.length;i++)
                if(file1[i]!=file2[i])
                    return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
