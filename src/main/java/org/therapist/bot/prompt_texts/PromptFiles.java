package org.therapist.bot.prompt_texts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Prompts {

    public String readEnglishPrompts() throws FileNotFoundException {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\JAVA\\MyOwnTherapist\\emotion_responses_english"));
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readRussianPrompts() throws FileNotFoundException{
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\JAVA\\MyOwnTherapist\\emotion_responses_russian"));
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
