/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure.writer;

import com.azure.storage.blob.specialized.AppendBlobClient;
import io.airbyte.cdk.integrations.destination.azure.AzureBlobStorageDestinationConfig;
import io.airbyte.cdk.integrations.destination.azure.AzureBlobStorageFormat;
import io.airbyte.cdk.integrations.destination.azure.csv.AzureBlobStorageCsvWriter;
import io.airbyte.cdk.integrations.destination.azure.jsonl.AzureBlobStorageJsonlWriter;
import io.airbyte.protocol.models.v0.ConfiguredAirbyteStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductionWriterFactory implements AzureBlobStorageWriterFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ProductionWriterFactory.class);

  @Override
  public AzureBlobStorageWriter create(final AzureBlobStorageDestinationConfig config,
                                       final AppendBlobClient appendBlobClient,
                                       final ConfiguredAirbyteStream configuredStream)
      throws Exception {
    final AzureBlobStorageFormat format = config.getFormatConfig().getFormat();

    if (format == AzureBlobStorageFormat.CSV) {
      LOGGER.debug("Picked up CSV format writer");
      return new AzureBlobStorageCsvWriter(config, appendBlobClient, configuredStream);
    }

    if (format == AzureBlobStorageFormat.JSONL) {
      LOGGER.debug("Picked up JSONL format writer");
      return new AzureBlobStorageJsonlWriter(config, appendBlobClient, configuredStream);
    }

    throw new RuntimeException("Unexpected AzureBlobStorage destination format: " + format);
  }

}
