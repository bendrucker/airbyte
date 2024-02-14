/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.cdk.integrations.BaseConnector;
import io.airbyte.cdk.integrations.base.AirbyteMessageConsumer;
import io.airbyte.cdk.integrations.base.Destination;
import io.airbyte.cdk.integrations.destination.azure.writer.AzureBlobStorageWriterFactory;
import io.airbyte.cdk.integrations.destination.azure.writer.ProductionWriterFactory;
import io.airbyte.protocol.models.v0.AirbyteConnectionStatus;
import io.airbyte.protocol.models.v0.AirbyteConnectionStatus.Status;
import io.airbyte.protocol.models.v0.AirbyteMessage;
import io.airbyte.protocol.models.v0.ConfiguredAirbyteCatalog;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseAzureBlobStorageDestination extends BaseConnector implements Destination {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseAzureBlobStorageDestination.class);

  @Override
  public AirbyteConnectionStatus check(final JsonNode config) {
    try {
      final AzureBlobStorageConnectionChecker client = new AzureBlobStorageConnectionChecker(
          AzureBlobStorageDestinationConfig.getAzureBlobStorageConfig(config));
      client.attemptWriteAndDelete();
      return new AirbyteConnectionStatus().withStatus(Status.SUCCEEDED);
    } catch (final Exception e) {
      LOGGER.error("Exception attempting to access the azure blob storage bucket: ", e);
      return new AirbyteConnectionStatus()
          .withStatus(Status.FAILED)
          .withMessage(
              "Could not connect to the azure blob storage with the provided configuration. \n" + e
                  .getMessage());
    }
  }

  @Override
  public AirbyteMessageConsumer getConsumer(final JsonNode config,
                                            final ConfiguredAirbyteCatalog configuredCatalog,
                                            final Consumer<AirbyteMessage> outputRecordCollector) {
    final AzureBlobStorageWriterFactory formatterFactory = new ProductionWriterFactory();
    return new AzureBlobStorageConsumer(
        AzureBlobStorageDestinationConfig.getAzureBlobStorageConfig(config), configuredCatalog,
        formatterFactory, outputRecordCollector);
  }

}
