package com.thealiyev;

public class Demo {
    public static void main(String[] args) {
        /**
         * Example shows how to Q-value learning library is being called and all essential arguments are being set
         * Data set initialization and shaping the architecture of Neural Networks
         */
        Perceptron perceptron = new Perceptron();
        perceptron.setDataSet("resources/AdmissionPredictDataSet.xls");
        perceptron.setTheNumberOfOutputs(1);
        perceptron.normalizeDataSet();

        /**
         * Example shows how to define which algorithm will be used for training  and  testing
         * Initializing learning rate(alpha) and random weights
         */
        perceptron.linearRegression();
        perceptron.setAlpha(0.01);
        perceptron.randomInitialization(25, 0);

        /**
         * Using methods to train and test the weights
         */
        perceptron.train(320);
        perceptron.test(80);
    }
}