package com.thealiyev;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;

import static jxl.Workbook.getWorkbook;

public class BackUpForMutlipleInputsAndOutputs {
    private static final String path = "resources/BackUpForMutlipleInputsAndOutputs.xls";
    public double alpha = 0.00001;

    private double learningRate;
    private ArrayList<ArrayList<Double>> parameters = null;
    int theNumberOfOutputNeurons;
    //private int theNumberOfHiddenLayers; add this future later

    public static void main(String[] args) {
        BackUpForMutlipleInputsAndOutputs test = new BackUpForMutlipleInputsAndOutputs();

        ArrayList<ArrayList<Double>> dataSet = new ArrayList<>();
        ArrayList<Double> dataPoint = new ArrayList<>();
        ArrayList<ArrayList<Double>> hiddenLayers = new ArrayList<>();
        ArrayList<Double> hiddenLayer = new ArrayList<>();
        ArrayList<Double> inpuptLayer = new ArrayList<>();
        ArrayList<Double> targets = new ArrayList<>();
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
        model.add(0.1);
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

        double prediction;
        double target;
        double error;
        ArrayList<Double> errors = new ArrayList<>();
        for (int counter = 0; counter < dataSet.size(); counter = counter + 1) {
            inpuptLayer.add(dataSet.get(counter).get(0));
            inpuptLayer.add(dataSet.get(counter).get(1));
            inpuptLayer.add(dataSet.get(counter).get(2));
            targets.add(dataSet.get(counter).get(3));
            targets.add(dataSet.get(counter).get(4));
            targets.add(dataSet.get(counter).get(5));

            for (int ndCounter = 0; ndCounter < targets.size(); ndCounter = ndCounter + 1) {
                prediction = test.HypothesisFunction(inpuptLayer, models).get(ndCounter);
                target = targets.get(ndCounter);
                error = (prediction - target);
                System.out.println(Math.pow(error, 2));
            }

            System.out.println(test.iterate(inpuptLayer, models, targets));
            inpuptLayer = new ArrayList<>();
            targets = new ArrayList<>();
        }
    }

    public ArrayList<Double> HypothesisFunction(ArrayList<Double> inpuptLayer, ArrayList<ArrayList<Double>> models) {
        ArrayList<Double> layer = new ArrayList<>();
        double output;

        for (int stCounter = 0; stCounter < models.size(); stCounter = stCounter + 1) {
            output = 0;
            for (int ndCounter = 0; ndCounter < models.get(stCounter).size(); ndCounter = ndCounter + 1) {
                output = output + inpuptLayer.get(ndCounter) * models.get(stCounter).get(ndCounter);
            }
            layer.add(output);
        }

        return layer;
    }

    public ArrayList<Double> calculateDerivateTerm(ArrayList<Double> inpuptLayer, ArrayList<ArrayList<Double>> models, ArrayList<Double> targets) {
        ArrayList<Double> derivativeTerms = new ArrayList<>();

        double derivativeTerm;

        for (int stCounter = 0; stCounter < targets.size(); stCounter = stCounter + 1) {
            derivativeTerm = (HypothesisFunction(inpuptLayer, models).get(stCounter) - targets.get(stCounter));
            derivativeTerms.add(derivativeTerm);
        }

        return derivativeTerms;
    }

    public ArrayList<ArrayList<Double>> iterate(ArrayList<Double> inpuptLayer, ArrayList<ArrayList<Double>> models, ArrayList<Double> targets) {
        ArrayList<Double> derivativeTerms = calculateDerivateTerm(inpuptLayer, models, targets);
        ArrayList<Double> modelDerivates = new ArrayList<>();
        double model;

        for (int stCounter = 0; stCounter < inpuptLayer.size(); stCounter = stCounter + 1) {
            modelDerivates.add(inpuptLayer.get(stCounter) * derivativeTerms.get(0));
        }

        for (int stCounter = 0; stCounter < models.size(); stCounter = stCounter + 1) {
            for (int ndCounter = 0; ndCounter < models.get(stCounter).size(); ndCounter = ndCounter + 1) {
                for (int rdCounter = 0; rdCounter < derivativeTerms.size(); rdCounter = rdCounter + 1) {
                    model = models.get(stCounter).get(ndCounter);
                    model = model - alpha * modelDerivates.get(ndCounter);
                    models.get(stCounter).set(ndCounter, model);
                }
            }
        }

        return models;
    }

    private static ArrayList<ArrayList<Double>> calculateCostFunction(ArrayList<ArrayList<Double>> outputMatrixArrayList,
                                                                      ArrayList<ArrayList<Double>> targetMatrixArrayList) {
        ArrayList<ArrayList<Double>> errorMatrixArrayList = new ArrayList<>();
        ArrayList<Double> errorVectorArrayList = new ArrayList<>();

        for (int firstCounter = 0; firstCounter < outputMatrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < outputMatrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                errorVectorArrayList.add(Math.pow((outputMatrixArrayList.get(firstCounter).
                        get(secondCounter) - targetMatrixArrayList.get(firstCounter).get(secondCounter)), 2));
            }
            errorMatrixArrayList.add(errorVectorArrayList);
            errorVectorArrayList = new ArrayList<>();
        }
        return errorMatrixArrayList;
    }
}
