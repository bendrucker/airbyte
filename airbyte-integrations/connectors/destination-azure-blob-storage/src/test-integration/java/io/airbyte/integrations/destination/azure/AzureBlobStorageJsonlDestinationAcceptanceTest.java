/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.azure;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.cdk.integrations.destination.azure.AzureBaseJsonlDestinationAcceptanceTest;
import io.airbyte.commons.io.IOs;
import io.airbyte.commons.json.Jsons;
import java.nio.file.Path;

public class AzureBlobStorageJsonlDestinationAcceptanceTest extends
    AzureBaseJsonlDestinationAcceptanceTest {

  static private final String SECRETS_CONFIG_JSON = "secrets/config.json";

  @Override
  protected JsonNode getBaseConfigJson() {
    return Jsons.deserialize(IOs.readFile(Path.of(SECRETS_CONFIG_JSON)));
  }

}
