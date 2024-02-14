/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.integrations.destination.azure;

public enum AzureBlobStorageFormat {

  CSV("csv"),
  JSONL("jsonl");

  private final String fileExtension;

  AzureBlobStorageFormat(final String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public String getFileExtension() {
    return fileExtension;
  }

}
