package jp.topse.bigdata.weka;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Practice1 {
    
    private static final String TRAIN_PATH= "src/main/resources/data1/train.arff";
    private static final String TEST_PATH= "src/main/resources/data1/test.arff";

    public static void main(String[] args) {
        Practice1 app = new Practice1();
        app.eval(TRAIN_PATH, TEST_PATH);
    }
    
    private void eval(String train, String test) {
        try {
            Instances trainData = loadData(train);
            Instances testData = loadData(test);
            if (trainData == null) {
                return;
            }
            
            J48 tree = new J48();
            String[] options = new String[] {
                "-U"
            };
            tree.setOptions(options);
            tree.buildClassifier(trainData);

            evalResult(tree, trainData, testData);

//            showResult(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Instances loadData(String arffPath) {
        try {
            Instances data = new Instances(new BufferedReader(new FileReader(arffPath)));
            data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void evalResult(J48 tree, Instances trainData, Instances testData) {
        try {
            Evaluation eval = new Evaluation(trainData);
            eval.evaluateModel(tree, testData);
            System.out.println(eval.toSummaryString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showResult(J48 tree) {
        try {
            TreeVisualizer visualizer = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
            
            JFrame frame = new JFrame("Results");
            frame.setSize(800, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            frame.getContentPane().add(visualizer);
            frame.setVisible(true);
            visualizer.fitToScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
