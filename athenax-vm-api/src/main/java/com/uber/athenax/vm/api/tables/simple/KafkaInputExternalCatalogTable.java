package com.uber.athenax.vm.api.tables.simple;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.SqlTimeTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.catalog.ExternalCatalogTable;
import org.apache.flink.table.descriptors.ConnectorDescriptor;
import org.apache.flink.table.descriptors.DescriptorProperties;
import scala.Option;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by KT on 2018/8/20.
 */
public class KafkaInputExternalCatalogTable extends ExternalCatalogTable implements Serializable {
    static final TableSchema SCHEMA = new TableSchema(
            new String[] {"id", "proctime"},
            new TypeInformation<?>[] {BasicTypeInfo.INT_TYPE_INFO, SqlTimeTypeInfo.TIMESTAMP});

    KafkaInputExternalCatalogTable(ConnectorDescriptor descriptor) {
        super(descriptor, Option.empty(), Option.empty(), Option.empty(), Option.empty());
    }

    public static KafkaInputExternalCatalogTable getKafkaExternalCatalogTable(Map<String, String> props) {
        ConnectorDescriptor descriptor = new ConnectorDescriptor("kafka+json", 1, false) {
            @Override
            public void addConnectorProperties(DescriptorProperties properties) {
                properties.putTableSchema("athenax.kafka.topic.schema", KafkaInputExternalCatalogTable.SCHEMA);
                properties.putProperties(props);
            }
        };
        return new KafkaInputExternalCatalogTable(descriptor);
    }
}