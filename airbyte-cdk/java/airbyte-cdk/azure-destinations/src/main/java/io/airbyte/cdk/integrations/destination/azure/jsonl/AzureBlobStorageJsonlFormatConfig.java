/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure.jsonl;

import io.airbyte.cdk.integrations.destination.azure.AzureBlobStorageFormat;
import io.airbyte.cdk.integrations.destination.azure.AzureBlobStorageFormatConfig;

public class AzureBlobStorageJsonlFormatConfig implements AzureBlobStorageFormatConfig {

  @Override
  public AzureBlobStorageFormat getFormat() {
    return AzureBlobStorageFormat.JSONL;
  }

}
