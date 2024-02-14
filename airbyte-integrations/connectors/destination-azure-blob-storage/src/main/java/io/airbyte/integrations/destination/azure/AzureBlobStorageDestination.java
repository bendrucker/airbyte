/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.azure;

import io.airbyte.cdk.integrations.base.IntegrationRunner;
import io.airbyte.cdk.integrations.destination.azure.BaseAzureBlobStorageDestination;

public class AzureBlobStorageDestination extends BaseAzureBlobStorageDestination {

  public static void main(final String[] args) throws Exception {
    new IntegrationRunner(new AzureBlobStorageDestination()).run(args);
  }

}
