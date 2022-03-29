package com.example.map524_assignment3_jaehyunahn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ProgressBar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        FragmentDialog.DialogClickListener {

    private ArrayList<Question> questionBank = new ArrayList<Question>();
    private ArrayList<QuestionFragment> qFragments;
    ArrayList<QuestionFragment> fragmentManager = new ArrayList<QuestionFragment>(0);

    private FragmentManager manager = getSupportFragmentManager();
    private FileStorageManager fileStorageManager;

    private Button truButton;
    private Button falButton;
    private ProgressBar progressBar;

    private int bar_progress=0;
    private int numOfAnswers;
    private int totalNumOfQuestions;
    private int originalNumOfQuestions;

    //adding questions to question bank
    public void addQuestions() {
        questionBank.add(new Question(R.string.qTxt_1,false,R.color.teal));
        questionBank.add(new Question(R.string.qTxt_2,true,R.color.dark_violet));
        questionBank.add(new Question(R.string.qTxt_3,true,R.color.dark_green));
        questionBank.add(new Question(R.string.qTxt_4,false,R.color.red));
        questionBank.add(new Question(R.string.qTxt_5,true,R.color.purple));
        questionBank.add(new Question(R.string.qTxt_6,false,R.color.blue));
        questionBank.add(new Question(R.string.qTxt_7,true,R.color.navy));
        questionBank.add(new Question(R.string.qTxt_8,false,R.color.green));
        questionBank.add(new Question(R.string.qTxt_9,false,R.color.maroon));
        questionBank.add(new Question(R.string.qTxt_10,false,R.color.black));
        originalNumOfQuestions = questionBank.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addQuestions();
        init();
        prepForQuestion();
    }

    @SuppressLint("ResourceAsColor")
    public void init(){
        truButton = findViewById(R.id.trueButton_main);
        truButton.setOnClickListener(this);
        falButton = findViewById(R.id.falseButton_main);
        falButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar_main);
        fileStorageManager = ((MyApp)getApplication()).fileStorageManager;
    }

    public void prepForQuestion(){
        qFragments = genQuestions(originalNumOfQuestions);
        totalNumOfQuestions = originalNumOfQuestions;
        manager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        manager.beginTransaction().add(R.id.container_main, qFragments.get(0)).commit();
    }


    @Override
    public void onClick(View view) {

        boolean isAnswer = qFragments.get(bar_progress).getAnswer();

        String correctAnswer = getResources().getString(R.string.correct);
        String incorrectAnswer = getResources().getString(R.string.incorrect);

        switch(view.getId()) {

            case R.id.trueButton_main:
                if(isAnswer) {
                    numOfAnswers+=1;
                    bar_progress+=1;
                    Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
                }
                else {
                    bar_progress+=1;
                    Toast.makeText(this, incorrectAnswer, Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.falseButton_main:
                if(!isAnswer){
                    numOfAnswers++;
                    bar_progress+=1;
                    Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
                }
                else {
                    bar_progress+=1;
                    Toast.makeText(this, incorrectAnswer, Toast.LENGTH_LONG).show();
                }
                break;
        }
        if(bar_progress == qFragments.size()) {
            openCurrentResultDialog();
            bar_progress=0;
        }
        else { manager.beginTransaction().replace(R.id.container_main, qFragments.get(bar_progress)).commit(); }
        progressBar.setProgress(bar_progress);
    }

    public ArrayList<QuestionFragment> genQuestions(int numOfQuestions) {
        progressBar.setMax(numOfQuestions);
        fragmentManager = new ArrayList<>(numOfQuestions);

        for(int i = 0; i < numOfQuestions; i++) {
            QuestionFragment question_fragment = QuestionFragment.newInstance(
                    questionBank.get(i).getQuestion(),
                    questionBank.get(i).getColour(),
                    questionBank.get(i).getAnswer());
            fragmentManager.add(question_fragment);
        }
        return fragmentManager;
    }

    public void openTotalResultDialog() {
        String resultAnswer;
        resultAnswer = fileStorageManager.loadFromAnswers(MainActivity.this);
        String resultTotal;
        resultTotal = fileStorageManager.loadFromTotal(MainActivity.this);

        String resultTxt = getResources().getString(R.string.showTotalResult);
        String resultForPrint = resultTxt + resultAnswer + "/" + resultTotal;

        new AlertDialog.Builder(this)
                .setMessage(resultForPrint)
                .setCancelable(true)
                .setPositiveButton(R.string.save_b, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }}).setNegativeButton(R.string.ok_b, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    public void openCurrentResultDialog() {

        String resultAnswer = fileStorageManager.loadFromAnswers(MainActivity.this);
        String resultTotal = fileStorageManager.loadFromTotal(MainActivity.this);

        int iresultAnswer = Integer.parseInt(resultAnswer);
        int iresultTotal = Integer.parseInt(resultTotal);

        int newNumOfAnswers = iresultAnswer + numOfAnswers;
        int newTotalNumOfQuestions = iresultTotal + totalNumOfQuestions;

        String yourScore = getResources().getString(R.string.yourScore);
        String outOf = getResources().getString(R.string.outOf);
        String saveBtnTxt = getResources().getString(R.string.save_b);
        String ignoreBtnTxt = getResources().getString(R.string.ignore_b);
        String result = getResources().getString(R.string.result);

        new AlertDialog.Builder(this)
                .setTitle(result)
                .setMessage(yourScore + numOfAnswers + outOf + totalNumOfQuestions)
                .setCancelable(false)
                .setPositiveButton(saveBtnTxt, (dialogInterface, i) -> {
                    fileStorageManager.removeAll(MainActivity.this);
                    fileStorageManager.saveNewToFile(MainActivity.this,newNumOfAnswers,newTotalNumOfQuestions);
                    manager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    manager.beginTransaction().replace(R.id.container_main, qFragments.get(0)).commit();
                }).setNegativeButton(ignoreBtnTxt, (dialogInterface, i) -> {
            manager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            manager.beginTransaction()
                    .replace(R.id.container_main, qFragments.get(0))
                    .commit();
        }).show();
        numOfAnswers = 0;
        bar_progress = 0;
        qFragments = genQuestions(totalNumOfQuestions);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {

            case R.id.average_menu:
                openTotalResultDialog();
                break;

            case R.id.numOfQuestion_menu:
                FragmentDialog newNumberOfQuestions = FragmentDialog.newInstance(originalNumOfQuestions);
                newNumberOfQuestions.listener= this;
                newNumberOfQuestions.show(manager, FragmentDialog.Tag);
                break;

            case R.id.reset_menu:
                fileStorageManager.removeAll(MainActivity.this);
                break;
        }
        return true;
    }

    @Override
    public void dialogListnerWithOk(int input) {
        if(input < 0 || input==0 ){
            AlertDialog.Builder db = new AlertDialog.Builder(MainActivity.this);
            new AlertDialog.Builder(this)
                    .setMessage(R.string.inputLessThan0)
                    .setCancelable(true)
                    .show();
        }
        if(input > 10){
            new AlertDialog.Builder(this).setMessage(R.string.inputLargerThan10).setCancelable(true).show();
        }
        else if(input > 0 && input<11) {
            totalNumOfQuestions = input;
            qFragments = genQuestions(input);
            manager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            manager.beginTransaction().replace(R.id.container_main, qFragments.get(0)).commit();
        }
        else{
            new AlertDialog.Builder(this).setMessage(R.string.invalidInput).setCancelable(true).show();
        }
    }

    @Override
    public void dialogListnerWithCancel() {}
}