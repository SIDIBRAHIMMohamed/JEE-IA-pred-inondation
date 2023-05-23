package com.esp.irt.backend.services;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.SerializationHelper;

public class ModelService {
    private LinearRegression model;

    public ModelService() {
        try {
            // Load the serialized model from file
            model = (LinearRegression) SerializationHelper.read("src/main/resources/model.model");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public double predict(Instance input) {
        try {
            // Make predictions using the loaded model
            return model.classifyInstance(input);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return Double.NaN;
        }
    }

}
