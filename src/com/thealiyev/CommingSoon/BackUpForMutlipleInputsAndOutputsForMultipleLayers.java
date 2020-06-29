package com.thealiyev;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;

import static jxl.Workbook.getWorkbook;


//Page 37|59
public class BackUpForMutlipleInputsAndOutputsForMultipleLayers {
    private static final String path = "resources/BackUpForMutlipleInputsAndOutputsForMultipleLayers.xls";

    private double learningRate;
    private ArrayList<ArrayList<Double>> parameters = null;
    int theNumberOfOutputNeurons;
    //private int theNumberOfHiddenLayers; add this future later

    public static void main(String[] args) {
        BackUpForMutlipleInputsAndOutputsForMultipleLayers backUp = new BackUpForMutlipleInputsAndOutputsForMultipleLayers();

        ArrayList<ArrayList<Double>> dataSet = new ArrayList<>();
        ArrayList<Double> dataPoint = new ArrayList<>();
        ArrayList<ArrayList<Double>> hiddenLayers = new ArrayList<>();
        ArrayList<Double> inpuptLayer = new ArrayList<>();
        ArrayList<Double> hiddenLayer = new ArrayList<>();
        ArrayList<ArrayList<Double>> models = new ArrayList<>();
        ArrayList<Double> model = new ArrayList<>();

        try {
            File file = new File(path);
            Workbook workbook = getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);
            for (int firstCounter = 0; firstCounter < sheet.getRows(); firstCounter = firstCounter + 1) {
                for (int secondCounter = 0; secondCounter < sheet.getColumns(); secondCounter = secondCounter + 1) {
                    Cell cell = sheet.getCell(secondCounter, firstCounter);
                    dataPoint.add(Double.parseDouble(cell.getContents()));
                }
                dataSet.add(dataPoint);
                dataPoint = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.add(0.1);
        model.add(0.2);
        model.add(-0.1);
        models.add(model);
        model = new ArrayList<>();

        model.add(-0.1);
        model.add(0.1);
        model.add(0.9);
        models.add(model);
        model = new ArrayList<>();

        model.add(0.1);
        model.add(0.4);
        model.add(0.1);
        models.add(model);
        model = new ArrayList<>();

        inpuptLayer.add(dataSet.get(0).get(0));
        inpuptLayer.add(dataSet.get(0).get(1));
        inpuptLayer.add(dataSet.get(0).get(2));

        hiddenLayer = backUp.NeuralNetwork(inpuptLayer, models);
        hiddenLayers.add(hiddenLayer);
        System.out.println(hiddenLayers.get(0));

        models = new ArrayList<>();

        model.add(0.3);
        model.add(1.1);
        model.add(-0.3);
        models.add(model);
        model = new ArrayList<>();

        model.add(0.1);
        model.add(0.2);
        model.add(0.0);
        models.add(model);
        model = new ArrayList<>();

        model.add(0.0);
        model.add(1.3);
        model.add(0.1);
        models.add(model);
        model = new ArrayList<>();

        hiddenLayer = backUp.NeuralNetwork(hiddenLayer, models);
        hiddenLayers.add(hiddenLayer);
        System.out.println(hiddenLayers.get(1));
    }

    public ArrayList<Double> NeuralNetwork(ArrayList<Double> inputs, ArrayList<ArrayList<Double>> models) {
        ArrayList<Double> layer = new ArrayList<>();
        double output;

        for (int stCounter = 0; stCounter < models.size(); stCounter = stCounter + 1) {
            output = 0;
            for (int ndCounter = 0; ndCounter < inputs.size(); ndCounter = ndCounter + 1) {
                output = output + inputs.get(ndCounter) * models.get(stCounter).get(ndCounter);
            }
            layer.add(output);
        }

        return layer;
    }

    public void setTheNumberOfOutputNeurons(int theNumberOfOutputNeurons) {
        this.theNumberOfOutputNeurons = theNumberOfOutputNeurons;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setParameters(ArrayList<ArrayList<Double>> parameters) {
        this.parameters = parameters;
    }
}
