package com.lucas.googleSheets.challenge.model;

import lombok.Data;

import java.util.List;

@Data
public class ConsumerFilterData {


    public int totalOfConsumers;

    public int filteredConsumers;

    public double percentage;

    public List<Consumer> consumerList;

    public ConsumerFilterData(int totalOfConsumers, int filteredConsumers, double percentage, List<Consumer> consumerList) {
        this.totalOfConsumers = totalOfConsumers;
        this.filteredConsumers = filteredConsumers;
        this.percentage = percentage;
        this.consumerList = consumerList;
    }
}
