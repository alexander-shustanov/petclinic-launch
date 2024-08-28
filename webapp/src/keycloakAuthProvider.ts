 import Keycloak, { KeycloakConfig } from "keycloak-js";
import { httpClient, keycloakAuthProvider } from "ra-keycloak";

const keycloakUrl = import.meta.env.VITE_KC_URL as string;
const realmName = import.meta.env.VITE_KC_REALM_NAME as string;
const clientId = import.meta.env.VITE_KC_CLIENT_ID as string;

const keycloakConfig: KeycloakConfig = {
  url: keycloakUrl,
  realm: realmName,
  clientId: clientId,
};

export const keycloakClient = new Keycloak(keycloakConfig);
export const keycloakHttpClient = httpClient(keycloakClient);
export const authProvider = keycloakAuthProvider(keycloakClient);

keycloakClient.onTokenExpired = async () => {
  const updated = await keycloakClient.updateToken(30);

  if (!updated) {
    authProvider.logout({});
  }
}; 