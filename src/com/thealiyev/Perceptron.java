package com.thealiyev;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static jxl.Workbook.getWorkbook;

/**
 * Framework of Perceptron model of Artificial Neural Networkss
 * Public class, user can initialize class and implement framework as shown in th Demo.java file.
 */
public class Perceptron {
    /**
     * Initialization of global variables, components of Artificial Neural Networkss
     */
    private ArrayList<ArrayList<Double>> dataSet = null;
    private int partOfDataSetForTesting, partOfDataSetForTraining;
    private int theNumberOfOutputs;
    private boolean linearRegression, logisticRegression;
    private double alpha;
    ArrayList<ArrayList<Double>> models = null;

    /**
     * Training the weights of models using given part of data set provided by user
     */
    public void train(int partOfDataSetForTraining) {
        this.partOfDataSetForTraining = partOfDataSetForTraining;
        if (partOfDataSetForTraining > 0)
            System.out.println("Training...");
        ArrayList<Double> inputs = new ArrayList<>();
        ArrayList<Double> targets = new ArrayList<>();
        double error;

        for (int stCounter = 0; stCounter < partOfDataSetForTraining; stCounter = stCounter + 1) {
            inputs.add(1.0);
            for (int ndCounter = 0; ndCounter < dataSet.get(stCounter).size(); ndCounter = ndCounter + 1) {
                if (ndCounter < (dataSet.get(stCounter).size() - theNumberOfOutputs)) {
                    inputs.add(dataSet.get(stCounter).get(ndCounter));
                } else {
                    targets.add(dataSet.get(stCounter).get(ndCounter));
                }
            }

            iterate(inputs, models, targets);

            for (int ndCounter = 0; ndCounter < targets.size(); ndCounter = ndCounter + 1) {
                error = Math.pow((predict(inputs, models).get(ndCounter) - targets.get(ndCounter)), 2);
                System.out.println("Data point: " + stCounter +
                        ", neuron: " + ndCounter + ", error: " + error);
            }

            inputs = new ArrayList<>();
            targets = new ArrayList<>();
        }
    }

    /**
     * Testing the weights of models using given part of data set provided by user after training session
     */
    public void test(int partOfDataSetForTesting) {
        this.partOfDataSetForTesting = partOfDataSetForTesting;
        if (partOfDataSetForTesting > 0)
            System.out.println("Testing...");
        ArrayList<Double> inputs = new ArrayList<>();
        ArrayList<Double> targets = new ArrayList<>();
        double error;

        for (int stCounter = partOfDataSetForTraining; stCounter < (partOfDataSetForTraining + partOfDataSetForTesting); stCounter = stCounter + 1) {
            inputs.add(1.0);
            for (int ndCounter = 0; ndCounter < dataSet.get(stCounter).size(); ndCounter = ndCounter + 1) {
                if (ndCounter < (dataSet.get(stCounter).size() - theNumberOfOutputs)) {
                    inputs.add(dataSet.get(stCounter).get(ndCounter));
                } else {
                    targets.add(dataSet.get(stCounter).get(ndCounter));
                }
            }

            for (int ndCounter = 0; ndCounter < targets.size(); ndCounter = ndCounter + 1) {
                error = Math.pow((predict(inputs, models).get(ndCounter) - targets.get(ndCounter)), 2);
                System.out.println("Data point: " + stCounter +
                        ", neuron: " + ndCounter + ", error: " + error);
            }

            inputs = new ArrayList<>();
            targets = new ArrayList<>();
        }
    }

    /**
     * Setting data set for training and testing sessions by user
     * Data set can be provided in 2 various type of path url format:
     * 1) As a absolute path. For example: "/Users/royalaliyev/Desktop/ANN/resources/DataSet.xls".
     * 2) Or Path from the content root format can be used like "resources/AdmissionPredictDataSet.xls".
     */
    public void setDataSet(String path) {
        dataSet = new ArrayList<>();
        ArrayList<Double> dataPoint = new ArrayList<>();

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
    }

    /**
     * Defining the number of neurons in the output layer of Neural Networks
     */
    public void setTheNumberOfOutputs(int theNumberOfOutputs) {
        this.theNumberOfOutputs = theNumberOfOutputs;
    }

    /**
     * To avoid noisy convergence data set can be normalized(in other word feature scaling)
     * Optional
     */
    public void normalizeDataSet() {
        ArrayList<Double> mins = new ArrayList<>();
        ArrayList<Double> maxs = new ArrayList<>();
        double xMin, xMax, x;

        for (int stCounter = 0; stCounter < dataSet.get(0).size() - theNumberOfOutputs; stCounter = stCounter + 1) {
            xMin = dataSet.get(0).get(stCounter);
            xMax = dataSet.get(0).get(stCounter);
            for (int ndCounter = 0; ndCounter < dataSet.size(); ndCounter = ndCounter + 1) {
                if (xMin > dataSet.get(ndCounter).get(stCounter)) {
                    xMin = dataSet.get(ndCounter).get(stCounter);
                }
                if (xMax < dataSet.get(ndCounter).get(stCounter)) {
                    xMax = dataSet.get(ndCounter).get(stCounter);
                }
            }
            mins.add(xMin);
            maxs.add(xMax);
        }

        for (int stCounter = 0; stCounter < dataSet.size(); stCounter = stCounter + 1) {
            for (int ndCounter = 0; ndCounter < mins.size(); ndCounter = ndCounter + 1) {
                xMin = mins.get(ndCounter);
                xMax = maxs.get(ndCounter);
                x = dataSet.get(stCounter).get(ndCounter);
                x = (x - xMin) / (xMax - xMin);
                dataSet.get(stCounter).set(ndCounter, x);
            }
        }
    }

    /**
     * Setting type of algorithm will be using in the neural networks, Linear Regression
     */
    public void linearRegression() {
        this.linearRegression = true;
    }

    /**
     * Setting type of algorithm and Activation Function will be using in the neural networks, Logistic Regression
     */
    public void logisticRegression(String activationFunction) {
        this.logisticRegression = true;
    }

    /**
     * Setting learning rate, alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Random initialization of weights to iterate
     */
    public void randomInitialization(double max, double min) {
        Random random = new Random();

        models = new ArrayList<>();
        ArrayList<Double> model = new ArrayList<>();

        for (int stCounter = 0; stCounter < theNumberOfOutputs; stCounter = stCounter + 1) {//+1 is for unit bias weight
            for (int ndCounter = 0; ndCounter < (dataSet.get(0).size() - theNumberOfOutputs + 1); ndCounter = ndCounter + 1) {
                model.add(min + (max - min) * random.nextDouble());
            }
            models.add(model);
            model = new ArrayList<>();
        }
    }

    /**
     * Iterating weights using (Full) Gradient Descent algorithm
     */
    private ArrayList<ArrayList<Double>> iterate(ArrayList<Double> inputs, ArrayList<ArrayList<Double>> models, ArrayList<Double> targets) {
        ArrayList<Double> derivativeTerms = calculateDerivativeTerm(inputs, models, targets);
        ArrayList<Double> modelDerivatives = new ArrayList<>();
        double parameter;

        for (int stCounter = 0; stCounter < inputs.size(); stCounter = stCounter + 1) {
            modelDerivatives.add(inputs.get(stCounter) * derivativeTerms.get(0));
        }

        for (int stCounter = 0; stCounter < models.size(); stCounter = stCounter + 1) {
            for (int ndCounter = 0; ndCounter < models.get(stCounter).size(); ndCounter = ndCounter + 1) {
                parameter = models.get(stCounter).get(ndCounter);
                parameter = parameter - alpha * modelDerivatives.get(ndCounter);
                models.get(stCounter).set(ndCounter, parameter);
            }
        }

        return models;
    }

    /**
     * Calculating derivative, (H(O) - y)
     */
    private ArrayList<Double> calculateDerivativeTerm(ArrayList<Double> inputs, ArrayList<ArrayList<Double>> models, ArrayList<Double> targets) {
        ArrayList<Double> derivativeTerms = new ArrayList<>();
        double derivativeTerm;

        for (int stCounter = 0; stCounter < targets.size(); stCounter = stCounter + 1) {
            derivativeTerm = (predict(inputs, models).get(stCounter) - targets.get(stCounter));
            derivativeTerms.add(derivativeTerm);
        }

        return derivativeTerms;
    }

    /**
     * Neural Networks predict the output value due to the given input
     */
    public ArrayList<Double> predict(ArrayList<Double> inputs, ArrayList<ArrayList<Double>> models) {
        ArrayList<Double> outputs = new ArrayList<>();
        double output;

        for (int stCounter = 0; stCounter < models.size(); stCounter = stCounter + 1) {
            output = 0;
            for (int ndCounter = 0; ndCounter < models.get(stCounter).size(); ndCounter = ndCounter + 1) {
                output = output + inputs.get(ndCounter) * models.get(stCounter).get(ndCounter);
            }
            outputs.add(output);
        }

        return outputs;
    }

    /**
     * User can get last version of models using this method
     * And keep it for later use
     */
    public ArrayList<ArrayList<Double>> getModels() {
        return models;
    }
}
