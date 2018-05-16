package jp.topse.bigdata.weka;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConvertCsvToARFF {

    static final String INPUT_PATH = "./src/main/resources/data1/train.csv";
    static final String OUTPUT_PATH = "./src/main/resources/data1/train.arff";

    public static void main(String[] args) {
        ConvertCsvToARFF app = new ConvertCsvToARFF();
        app.create(INPUT_PATH, OUTPUT_PATH);
    }

    private void create(String input, String output) {
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("p1"));
        attributes.addElement(new Attribute("p2"));
        attributes.addElement(new Attribute("p3"));
        attributes.addElement(new Attribute("p4"));
        attributes.addElement(new Attribute("p5"));
        attributes.addElement(new Attribute("p6"));
        attributes.addElement(new Attribute("p7"));
        attributes.addElement(new Attribute("p8"));
        attributes.addElement(new Attribute("p9"));
        attributes.addElement(new Attribute("p10"));
        FastVector classValues = new FastVector();
        classValues.addElement("F");
        classValues.addElement("T");
        attributes.addElement(new Attribute("class", classValues));

        Instances data = new Instances("data1", attributes, 0);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(input)));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",", 0);
                double[] values = new double[data.numAttributes()];
                for (int i = 1; i <= 10; ++i) {
                    values[i - 1] = Double.parseDouble(items[i]);
                }
                values[10] = (int) Double.parseDouble(items[11]);
                data.add(new Instance(1.0, values));
            }
            reader.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(data);
            arffSaver.setFile(new File(output));
            arffSaver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
