import Keycloak from 'keycloak-js'
import { config } from '../../Constants'

// Setup Keycloak instance as needed
// Pass initialization options as required or leave blank to load from 'keycloak.json'
const keycloakConfig = {
  url: `${config.url.KEYCLOAK_BASE_URL}`,
  realm: 'prototype-render-farm-realm',
  clientId: 'spring-cloud-client',
}

const keycloak = new Keycloak(keycloakConfig)

export default keycloak
