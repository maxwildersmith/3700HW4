package Independence;

import com.google.cloud.translate.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;

public class Translator {
    public static String singleThread(String sourcePath, String language){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        try {
            String source = listToString(Files.readAllLines(Paths.get(sourcePath)));
            Translation translation = translate.translate(source, Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.format("text"));
            return translation.getTranslatedText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String listToString(List<String> list){
        String out = "";
        for(String s:list)
            out+=s;
        return out;
    }

    public static String multiThread(String sourcePath, String language, int numThreads){
        ExecutorService exec = Executors.newFixedThreadPool(numThreads);
        Future<String>[] outputs = new Future[numThreads];
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        String out = "";

        try {
            List<String> lines = Files.readAllLines(Paths.get(sourcePath));

            int size = lines.size()/(numThreads-1);
            for(int i=0;i<numThreads;i++){
                int start = i;
                outputs[i] = exec.submit(() -> translate.translate(listToString(lines.subList(start*size,Math.min((start+1)*size, lines.size()))), Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.format("text")).getTranslatedText());
            }
            exec.shutdown();

            for(Future<String> result: outputs)
                out+=result.get();
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
