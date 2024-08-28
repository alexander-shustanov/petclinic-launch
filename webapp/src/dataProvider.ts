import { DataProvider } from "react-admin";
import springDataProvider from "./springDataProvider";
import { keycloakHttpClient } from "./keycloakAuthProvider"; 

const baseDataProvider = springDataProvider(
  import.meta.env.VITE_SIMPLE_REST_URL,
  keycloakHttpClient 
);

export interface CustomDataProvider extends DataProvider {

}

export const dataProvider: CustomDataProvider = {
  ...baseDataProvider,
};
