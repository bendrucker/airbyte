/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure.writer;

import com.azure.storage.blob.specialized.AppendBlobClient;
import io.airbyte.cdk.integrations.destination.azure.AzureBlobStorageDestinationConfig;
import io.airbyte.protocol.models.v0.ConfiguredAirbyteStream;

/**
 * Create different {@link AzureBlobStorageWriter} based on
 * {@link AzureBlobStorageDestinationConfig}.
 */
public interface AzureBlobStorageWriterFactory {

  AzureBlobStorageWriter create(AzureBlobStorageDestinationConfig config,
                                AppendBlobClient appendBlobClient,
                                ConfiguredAirbyteStream configuredStream)
      throws Exception;

}
