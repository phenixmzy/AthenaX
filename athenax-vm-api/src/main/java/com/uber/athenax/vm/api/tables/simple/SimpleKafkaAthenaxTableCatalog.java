package com.uber.athenax.vm.api.tables.simple;

import com.uber.athenax.vm.api.tables.AthenaXTableCatalog;

import org.apache.flink.table.api.CatalogNotExistException;
import org.apache.flink.table.api.TableNotExistException;
import org.apache.flink.table.catalog.ExternalCatalog;
import org.apache.flink.table.catalog.ExternalCatalogTable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KT on 2018/8/18.
 */
public class SimpleKafkaAthenaxTableCatalog implements AthenaXTableCatalog{
    private static final long serialVersionUID = -1L;

    private final String broker;
    private final List<String> availableTables;

    public SimpleKafkaAthenaxTableCatalog(String broker, List<String> availableTables) {
        this.broker = broker;
        this.availableTables = availableTables;
    }

    @Override
    public ExternalCatalogTable getTable(String tableName) throws TableNotExistException {
        Map<String, String> sourceTableProp = new HashMap<>();
        sourceTableProp.put("kafka."+"group.id", tableName);
        sourceTableProp.put("kafka."+"bootstrap.servers", broker);
        sourceTableProp.put("kafka."+"auto.offset.reset", "earliest");
        sourceTableProp.put("athenax.kafka.topic.name", tableName);
        return KafkaInputExternalCatalogTable.getKafkaExternalCatalogTable(sourceTableProp);
    }

    @Override
    public List<String> listTables() {
        return this.availableTables;
    }

    @Override
    public ExternalCatalog getSubCatalog(String dbName) throws CatalogNotExistException {
        throw new CatalogNotExistException(dbName);
    }

    @Override
    public List<String> listSubCatalogs() {
        return Collections.emptyList();
    }
}
