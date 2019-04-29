package org.lrima.laop.simulation.data;

import org.lrima.laop.utils.CSVUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AlgorithmData extends ArrayList<ArrayList<Double>> {
    Date start = new Date();
    HashMap<String, ArrayList<Double>> values = new HashMap<>();
    Consumer<String> valueAdded;

    public void toCsv(String prefix) {
        //Create a directory with the time of the simulation start in /data
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/d/HH-mm-ss");
        for(String scope : values.keySet()) {
            String directory = "data/" + dateFormat.format(this.start);
            File file = new File(directory, prefix + "-" + scope + ".csv");
            file.getParentFile().mkdirs();

            ArrayList<String> values = this.values.get(scope).stream()
                    .map(d -> d + "")
                    .collect(Collectors.toCollection(ArrayList::new));

            CSVUtils.createCSVFile(file, (writer) ->{
                try {
                    CSVUtils.writeLine(writer, values);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void put(String scope, Double data){
        values.computeIfAbsent(scope, k -> new ArrayList<>());
        this.values.get(scope).add(data);
        if(valueAdded != null)
            valueAdded.accept(scope);
    }

    public HashMap<String, ArrayList<Double>> getData(){
        return values;
    }

    public void setOnAddValue(Consumer<String> stringConsumer){
        this.valueAdded = stringConsumer;
    }
}
