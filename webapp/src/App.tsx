 import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin"; 
import {
  amplicodeDarkTheme,
  amplicodeLightTheme,
} from "./themes/amplicodeTheme/amplicodeTheme";
import { dataProvider } from "./dataProvider";
import Keycloak, { KeycloakInitOptions } from "keycloak-js";
import { useRef, useEffect, useState } from "react";
import {
  keycloakClient,
  authProvider,
} from "./keycloakAuthProvider"; 

 const initOptions: KeycloakInitOptions = {
  onLoad: "login-required",
};
export const App = () => {
   const [keycloak, setKeycloak] = useState<Keycloak>();
  const initializingPromise = useRef<Promise<Keycloak>>();

  useEffect(() => {
    const initKeyCloakClient = async () => {
      await keycloakClient.init(initOptions);
      return keycloakClient;
    };
    if (!initializingPromise.current) {
      initializingPromise.current = initKeyCloakClient();
    }

    initializingPromise.current.then((client) => setKeycloak(client));
  }, [keycloak]);

  // hide the admin until the dataProvider and authProvider are ready
  if (!keycloak) return <p>Loading...</p>;
  return (
    <Admin
      authProvider={authProvider} 
      dataProvider={dataProvider}
      lightTheme={amplicodeLightTheme}
      darkTheme={amplicodeDarkTheme}
    >
    
      <Resource
        name="owners"
        edit={EditGuesser}
        list={ListGuesser}
        show={ShowGuesser}
        recordRepresentation="firstName"
      />
      
      <Resource
        name="pets"
        edit={EditGuesser}
        list={ListGuesser}
        show={ShowGuesser}
        recordRepresentation="name"
      />
      </Admin>
  )
};
