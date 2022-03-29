package com.example.map524_assignment3_jaehyunahn;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileStorageManager {
    static String File_Answers = "Answers.txt";
    static String File_totalNumQuestion = "TotalNumberOfQuestions.txt";
    FileOutputStream fos;
    FileInputStream fis;

    public void saveNewAnswers(Activity activity, int answers) {
        try{
            fos = activity.openFileOutput(File_Answers, activity.MODE_APPEND);
            fos.write((Integer.toString(answers)).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveNewTotal(Activity activity, int numOfQuestions) {
        try{
            fos = activity.openFileOutput(File_totalNumQuestion, activity.MODE_APPEND);
            fos.write((Integer.toString(numOfQuestions)).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNewToFile(Activity activity,int answers, int numOfQuestions){
        saveNewAnswers(activity,answers);
        saveNewTotal(activity,numOfQuestions);
    }

    public void removeAllAnswers(Activity activity) {
        try{
            fos = activity.openFileOutput(File_Answers, activity.MODE_PRIVATE);
            fos.write(("0").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeAllTotal(Activity activity) {
        try{
            fos = activity.openFileOutput(File_totalNumQuestion, activity.MODE_PRIVATE);
            fos.write(("0").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeAll(Activity activity){
        removeAllAnswers(activity);
        removeAllTotal(activity);
    }

    public String loadFromAnswers(Activity activity){
        String data = "";
        StringBuffer strBuff = new StringBuffer();

        try{
            fis = activity.openFileInput(File_Answers);
            int read = 0;

            while((read = fis.read()) != -1){
                strBuff.append((char) read);
            }
            data = strBuff.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public String loadFromTotal(Activity activity){
        String data = "";
        StringBuffer strBuff = new StringBuffer();

        try{
            fis = activity.openFileInput(File_totalNumQuestion);
            int read = 0;
            while((read = fis.read()) != -1){
                strBuff.append((char) read);
            }
            data = strBuff.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
