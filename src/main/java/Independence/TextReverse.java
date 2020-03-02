package Independence;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("ALL")
public class TextReverse {

    public static void singleThreadReverse(String inputFilePath, String outputFilePath){
        try {
            Scanner input = new Scanner(new File(inputFilePath));
            input.useDelimiter(" ");
            Stack<String> stack = new Stack<>();
            while(input.hasNext()){
                stack.push(input.next());
            }
            FileOutputStream output = new FileOutputStream(outputFilePath);
            while(!stack.empty())
                output.write((stack.pop()+(stack.empty()?"":" ")).getBytes());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] reverseByteSeq(byte[][] seq, int start, int end){
        String tmp = "";
        if(end>seq.length)
            end = seq.length;
        for(int i=end-1;i>=start;i--){
            tmp+=new String(seq[i])+" ";
        }
        return tmp.getBytes();
    }

    public static void multiThreadReverse(String inputFilePath, String outputFilePath, int numThreads){
        try {
            byte[] data = new FileInputStream(inputFilePath).readAllBytes();
            List<byte[]> words = new ArrayList<>();
            String tmp = "";
            for(byte b: data){
                if(b==' '){
                    words.add(tmp.getBytes());
                    tmp = "";
                } else
                    tmp+=(char)b;
            }

            Future<String>[] reversed = new Future[numThreads];
            ExecutorService exec = Executors.newFixedThreadPool(numThreads);

            final int section = words.size()/(numThreads-1);
            for(int i=0;i<numThreads;i++){
                int finalI = i;
                reversed[i] = exec.submit(() -> new String(reverseByteSeq(words.toArray(new byte[words.size()][]), finalI*section,(finalI+1)*section)));
            }
            exec.shutdown();
            String str = "";
            for(int i=reversed.length-1;i>=0;i--)
                str+=reversed[i].get()+" ";

            FileOutputStream output = new FileOutputStream(outputFilePath);
            output.write(str.getBytes());
            output.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
