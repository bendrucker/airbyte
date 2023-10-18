#
# Copyright (c) 2023 Airbyte, Inc., all rights reserved.
#


from typing import Any, Dict, Literal, Union

import dpath.util
from airbyte_cdk.sources.file_based.config.abstract_file_based_spec import AbstractFileBasedSpec
from pydantic import BaseModel, Field


class OAuthCredentials(BaseModel):
    class Config:
        title = "Authenticate via Google (OAuth)"

    auth_type: Literal["Client"] = Field("client", const=True)
    client_id: str = Field(
        title="Client ID",
        description="Client ID for the Google Drive API",
        airbyte_secret=True,
    )
    client_secret: str = Field(
        title="Client Secret",
        description="Client Secret for the Google Drive API",
        airbyte_secret=True,
    )
    refresh_token: str = Field(
        title="Refresh Token",
        description="Refresh Token for the Google Drive API",
        airbyte_secret=True,
    )


class ServiceAccountCredentials(BaseModel):
    class Config:
        title = "Service Account Key Authentication"

    auth_type: Literal["Service"] = Field("Service", const=True)
    service_account_info: str = Field(
        title="Service Account Information",
        description='The JSON key of the service account to use for authorization. Read more <a href="https://cloud.google.com/iam/docs/creating-managing-service-account-keys#creating_service_account_keys">here</a>.',
        airbyte_secret=True,
    )


class SourceGoogleDriveSpec(AbstractFileBasedSpec, BaseModel):
    class Config:
        title = "Google Drive Source Spec"

    folder_url: str = Field(description="URL for the folder you want to sync", order=0)

    credentials: Union[OAuthCredentials, ServiceAccountCredentials] = Field(
        title="Authentication", description="Credentials for connecting to the Google Drive API", discriminator="auth_type"
    )

    @classmethod
    def documentation_url(cls) -> str:
        return "https://docs.airbyte.com/integrations/sources/google-drive"

    @staticmethod
    def remove_discriminator(schema: dict) -> None:
        """pydantic adds "discriminator" to the schema for oneOfs, which is not treated right by the platform as we inline all references"""
        dpath.util.delete(schema, "properties/*/discriminator")

    @classmethod
    def schema(cls, *args: Any, **kwargs: Any) -> Dict[str, Any]:
        """
        Generates the mapping comprised of the config fields
        """
        schema = super().schema(*args, **kwargs)

        cls.remove_discriminator(schema)

        schema["advanced_auth"] = {
            "auth_flow_type": "oauth2.0",
            "predicate_key": ["credentials", "auth_type"],
            "predicate_value": "Client",
            "oauth_config_specification": {
                "complete_oauth_output_specification": {
                    "type": "object",
                    "additionalProperties": False,
                    "properties": {"refresh_token": {"type": "string", "path_in_connector_config": ["credentials", "refresh_token"]}},
                },
                "complete_oauth_server_input_specification": {
                    "type": "object",
                    "additionalProperties": False,
                    "properties": {"client_id": {"type": "string"}, "client_secret": {"type": "string"}},
                },
                "complete_oauth_server_output_specification": {
                    "type": "object",
                    "additionalProperties": False,
                    "properties": {
                        "client_id": {"type": "string", "path_in_connector_config": ["credentials", "client_id"]},
                        "client_secret": {"type": "string", "path_in_connector_config": ["credentials", "client_secret"]},
                    },
                },
            },
        }

        return schema
