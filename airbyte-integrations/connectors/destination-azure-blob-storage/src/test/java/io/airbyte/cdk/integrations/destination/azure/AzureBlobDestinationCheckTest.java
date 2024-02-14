/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.commons.io.IOs;
import io.airbyte.commons.json.Jsons;
import io.airbyte.integrations.destination.azure.AzureBlobStorageDestination;
import java.nio.file.Path;

public class AzureBlobDestinationCheckTest extends AzureBaseDestinationCheckTest {

  static private final String secretFilePath = "secrets/config.json";
  static private final JsonNode configFromSecrets = Jsons.deserialize(IOs.readFile(Path.of(secretFilePath)));

  @Override
  protected JsonNode configFromSecrets() {
    return Jsons.clone(configFromSecrets);
  }

  @Override
  protected BaseAzureBlobStorageDestination source() {
    return new AzureBlobStorageDestination();
  }

}
