package com.thealiyev;

import java.util.ArrayList;

public class Perceptron {
    private double learningRate;
    private ArrayList<ArrayList<Double>> parameters = null;
    //private int theNumberOfHiddenLayers; add this future later

    public static void main(String[] args) {
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setParameters(ArrayList<ArrayList<Double>> parameters) {
        this.parameters = parameters;
    }
}
