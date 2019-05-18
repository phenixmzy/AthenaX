package com.uber.athenax.vm.api.tables.simple;

import com.uber.athenax.vm.api.tables.AthenaXTableCatalog;
import com.uber.athenax.vm.api.tables.AthenaXTableCatalogProvider;
import org.apache.flink.util.Preconditions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by KT on 2018/8/19.
 */
public class SimpleAthenaxTableCatalogProvider implements AthenaXTableCatalogProvider {
    private final static String BROKER_LIST = "vmphenix:9092,vmnarsi:9092,vmramon:9092";
    @Override
    public Map<String, AthenaXTableCatalog> getInputCatalog(String cluster) {
        return Collections.singletonMap(
                "input",
                new SimpleKafkaAthenaxTableCatalog(BROKER_LIST, Collections.singletonList("log-data")));

    }

    @Override
    public AthenaXTableCatalog getOutputCatalog(String cluster, List<String> outputs) {
        return new SimpleKafkaAthenaxTableCatalog(BROKER_LIST, Collections.singletonList("test"));
    }
}
